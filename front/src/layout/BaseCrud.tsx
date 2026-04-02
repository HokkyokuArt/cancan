import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import FilterListIcon from '@mui/icons-material/FilterList';
import { Box, Chip, Tooltip, Typography } from "@mui/material";
import type { GridColDef, GridPaginationModel, GridSortModel } from '@mui/x-data-grid';
import { useEffect, useState, type JSX } from 'react';
import useSuperRequests from '../common/services/useSuperRequests';
import type { CrudDtoTypeMapKey, CrudState, GenericFiltroDTO, GenericListResponseDTO, GenericPayloadDTO } from '../common/types/crudState';
import { Pageable, type Page } from '../common/types/pageable';
import type { UUID } from '../common/types/uuid';
import CustomButton from '../components/CustomButton';
import CustomDataGrid, { type DataGridRowAction } from '../components/CustomDataGrid';
import CustomDialog from '../components/CustomDialog';
import CustomInput from "../components/CustomInput";
import { useAppSelector } from '../redux/store';

export enum CrudAction {
    VISUALIZAR = "visualizar",
    CRIAR = "criar",
    EDITAR = "editar",
    LIMPAR = "limpar",
    FILTRAR = 'filtrar',
}
export type CrudDetailDialogProps<K extends CrudDtoTypeMapKey> = {
    title: string,
    open: boolean,
    onClose: () => void;
    onConfirm: (entidade: GenericPayloadDTO<K>) => void;
};

export type CrudFiltroProps<K extends CrudDtoTypeMapKey> = {
    open: boolean,
    onClose: () => void;
    onConfirm: (filtro: GenericFiltroDTO<K>) => void;
    onClear: () => void;
};

export type ChipsConfig<K extends CrudDtoTypeMapKey> = Partial<Record<keyof GenericFiltroDTO<K>, { label: string; convertValue?: (val: any) => string; }>>;

type Props<
    K extends CrudDtoTypeMapKey
> = {
    entityName: string,
    columns: GridColDef<GenericListResponseDTO<K>>[];
    actions?: DataGridRowAction<GenericListResponseDTO<K>>[];
    initialSort?: GridSortModel;
    chipsConfig: ChipsConfig<K>;
    onSetCrudState: (action: CrudAction, crudState: Partial<CrudState<K>>) => void;
    dialogDetail: (props: CrudDetailDialogProps<K>) => JSX.Element;
    dialogFiltro?: (props: CrudFiltroProps<K>) => JSX.Element;
};

const BaseCrud = <
    K extends CrudDtoTypeMapKey
>(props: Props<K>) => {
    const {
        columns,
        dialogDetail,
        dialogFiltro,
        entityName,
        onSetCrudState: setCrudState,
        chipsConfig,
        initialSort,
        actions } = props;

    const crudState = useAppSelector(s => s[`${entityName}State` as keyof typeof s]) as CrudState<K>;
    const { pageable, findToEdit, create, update, delete: deleteEntity } = useSuperRequests<K>({
        url: '/' + entityName
    });

    const [page, setPage] = useState<Page<GenericListResponseDTO<K>>>({
        content: [],
        totalElements: 0,
        totalPages: 0,
        size: 10,
        number: 0,
        first: true,
        last: true,
        numberOfElements: 0,
        empty: true,
    });

    const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
        page: 0,
        pageSize: 10,
    });

    const [sortModel, setSortModel] = useState<GridSortModel>(initialSort ?? []);

    const [dialogDetailState, setDialogDetailState] = useState<{ open: boolean; }>({ open: false });
    const [dialogFiltroState, setDialogFiltroState] = useState<{ open: boolean; }>({ open: false });
    const [dialogExclusaoState, setDialogExclusaoState] = useState<{ open: boolean; row: GenericListResponseDTO<K> | null; }>({ open: false, row: null });

    useEffect(() => {
        handleList();
    }, [
        paginationModel,
        sortModel
    ]);

    const clearCrudState = (key: keyof CrudState<K>) => {
        setTimeout(() => {
            setCrudState(CrudAction.LIMPAR, { [key]: null });
        },);
    };

    const handleBuscaGeral = (value: string) => {
        pageable(
            { busca: value } as GenericFiltroDTO<K>,
            Pageable.ofDataGridModel({ paginationModel, sortModel }),
            res => {
                setPage(res);
            }
        );
    };

    const handleList = (filter: GenericFiltroDTO<K> = {}) => {
        pageable(
            filter,
            Pageable.ofDataGridModel({ paginationModel, sortModel }),
            res => {
                setPage(res);
            }
        );
    };

    const handleFindToEdit = (id: UUID) => {
        findToEdit(id, res => {
            setCrudState(CrudAction.EDITAR, { entidade: res });
            setDialogDetailState(prev => ({ ...prev, open: true }));
        });
    };

    const handleCreate = (entidade: GenericPayloadDTO<K>) => {
        create(entidade, () => {
            setDialogDetailState(prev => ({ ...prev, open: false }));
            handleList();
            clearCrudState('entidade');
        });
    };

    const handleUpdate = (entidade: GenericPayloadDTO<K>) => {
        update(entidade, () => {
            setDialogDetailState(prev => ({ ...prev, open: false }));
            handleList();
            clearCrudState('entidade');
        });
    };

    const handleDelete = () => {
        const id = dialogExclusaoState.row?.id;
        if (!id) return;
        deleteEntity(id, () => {
            setDialogExclusaoState(prev => ({ ...prev, open: false, row: null }));
            handleList();
        });
    };

    const handlePaginationModelChange = (model: GridPaginationModel) => {
        setPaginationModel(model);
    };

    const handleSortModelChange = (model: GridSortModel) => {
        setSortModel(prev => {
            if (!model.length) return prev;
            return model;
        });
    };

    const baseActions: DataGridRowAction<GenericListResponseDTO<K>>[] = [
        // {
        //     id: 'visualizar',
        //     label: 'Visualizar',
        //     icon: <VisibilityIcon />,
        //     onClick: (row) => {
        //         setDialogDetailState(prev => ({ ...prev, open: true }));
        //     },
        // },
        {
            id: 'editar',
            label: 'Editar',
            icon: <EditIcon />,
            onClick: (row) => {
                handleFindToEdit(row.id);
            },
        },
        {
            id: 'excluir',
            label: 'Excluir',
            icon: <DeleteIcon />,
            onClick: (row) => {
                setDialogExclusaoState(prev => ({ ...prev, open: true, row }));
            },
        },
    ];

    const getChips = () => {
        const filtros = crudState.filtro;
        if (!!filtros)
            // TODO: arrumar chips com values que são id de entidade
            return <Box marginBottom={'10px'} display='flex' gap={'3px'}>
                {Object.entries(filtros)
                    .filter(([key, value]) => (Array.isArray(value) ? value?.length : !!value) && key !== 'buscar')
                    .map(([key, value]) => {
                        const configChip = chipsConfig[key as keyof GenericFiltroDTO<K>];
                        return <Chip key={key} label={`${configChip?.label}: ${configChip?.convertValue?.(value) ?? value}`} />;
                    })}
            </Box>;
    };

    return (
        <>
            <Box display='flex' gap={'10px'} marginBottom={'10px'} maxWidth={'calc(100% - 45px)'}>
                <CustomInput
                    id='busca'
                    label="Buscar"
                    sx={{
                        maxWidth: '700px',
                    }}
                    onChange={v => handleBuscaGeral(v)}
                />

                {!!dialogFiltro &&
                    <Tooltip title="Filtros">
                        <CustomButton
                            color='primary'
                            isIcon
                            onClick={() =>
                                setDialogFiltroState(prev => ({ ...prev, open: true }))
                            }
                        >
                            <FilterListIcon />
                        </CustomButton>
                    </Tooltip>
                }

                <CustomButton
                    color='primary'
                    endIcon={<AddIcon />}
                    onClick={() =>
                        setDialogDetailState(prev => ({ ...prev, open: true }))
                    }
                >
                    Novo
                </CustomButton>
            </Box >

            {getChips()}

            <CustomDataGrid<GenericListResponseDTO<K>>
                height={'calc(100vh - 275px)'}
                page={page}
                columns={columns}
                paginationModel={paginationModel}
                onPaginationModelChange={handlePaginationModelChange}
                sortModel={sortModel}
                onSortModelChange={handleSortModelChange}
                rowActions={[...baseActions, ...(actions ?? [])]}
            />

            {
                dialogDetail({
                    title: !crudState.entidade ? 'Novo' : 'Editar',
                    open: dialogDetailState.open,
                    onClose: () => {
                        setDialogDetailState(prev => ({ ...prev, open: false }));
                        clearCrudState('entidade');
                    },
                    onConfirm: entidade => {
                        if (!crudState.entidade) {
                            handleCreate(entidade);
                        } else {
                            handleUpdate(entidade);
                        }
                    }
                })
            }

            {
                dialogFiltro?.({
                    open: dialogFiltroState.open,
                    onClose: () => {
                        setDialogFiltroState(prev => ({ ...prev, open: false }));
                    },
                    onConfirm: filtro => {
                        setCrudState(CrudAction.FILTRAR, { filtro });
                        handleList(filtro);
                        setDialogFiltroState(prev => ({ ...prev, open: false }));
                    },
                    onClear: () => {
                        clearCrudState('filtro');
                        handleList();
                    }
                })
            }

            <CustomDialog
                open={dialogExclusaoState.open}
                onClose={() => {
                    setDialogExclusaoState(prev => ({ ...prev, open: false, row: null }));
                }}
                title={'Atenção'}
                content={<Typography>Deseja realmente excluir o registro?</Typography>}
                onConfirm={handleDelete}
            />
        </>
    );
};

export default BaseCrud;
