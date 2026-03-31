import AddIcon from '@mui/icons-material/Add';
import FilterListIcon from '@mui/icons-material/FilterList';
import { Box } from "@mui/material";
import type { GridColDef, GridPaginationModel, GridSortModel } from '@mui/x-data-grid';
import { useEffect, useState, type JSX } from 'react';
import useSuperRequests from '../common/services/useSuperRequests';
import type { AbstractEntityDTO, SuperPayloadResponseDTO } from '../common/types/abstractEntity';
import { OrderDirection, Pageable, type Filter, type Page } from '../common/types/pageable';
import CustomButton from '../components/CustomButton';
import CustomDataGrid, { type DataGridRowAction } from '../components/CustomDataGrid';
import CustomInput from "../components/CustomInput";

type Props<LIST_RESPONSE_DTO extends AbstractEntityDTO> = {
    requestPath: string,
    columns: GridColDef<LIST_RESPONSE_DTO>[];
    actions: DataGridRowAction<LIST_RESPONSE_DTO>[];
    dialogDetail: (props: { open: boolean, onClose: () => void; }) => JSX.Element;
    dialogFiltro: (props: { open: boolean, onClose: () => void; }) => JSX.Element;
};

const BaseCrud = <
    RESPONSE_DTO extends SuperPayloadResponseDTO,
    LIST_RESPONSE_DTO extends AbstractEntityDTO,
    FILTRO_DTO extends Filter
>(props: Props<LIST_RESPONSE_DTO>) => {

    const { pageable } = useSuperRequests<RESPONSE_DTO, LIST_RESPONSE_DTO, FILTRO_DTO>({
        url: props.requestPath
    });

    const [page, setPage] = useState<Page<LIST_RESPONSE_DTO>>({
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

    const [loading, setLoading] = useState(false);

    const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
        page: 0,
        pageSize: 10,
    });

    const [sortModel, setSortModel] = useState<GridSortModel>([
        {
            field: 'nome',
            sort: OrderDirection.ASC,
        }
    ]);

    const [dialogDetail, setDialogDetail] = useState<{ open: boolean; }>({ open: false });
    const [dialogFiltro, setDialogFiltro] = useState<{ open: boolean; }>({ open: false });

    useEffect(() => {
        list();
    }, [
        paginationModel,
        sortModel
    ]);

    const list = () => {
        setLoading(true);
        pageable(
            {} as FILTRO_DTO,
            Pageable.ofDataGridModel({ paginationModel, sortModel }),
            res => {
                setPage(res);
                setLoading(false);
            }
        );
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

    return (
        <>
            <Box display='flex' gap={'10px'} marginBottom={'20px'}>
                <CustomInput
                    sx={{ maxWidth: '700px' }}
                    label="Buscar"
                    onChange={v => console.log('busca => ', v)}
                />
                <CustomButton
                    color='primary'
                    isIcon
                    onClick={() =>
                        setDialogFiltro(prev => ({ ...prev, open: true }))
                    }
                >
                    <FilterListIcon />
                </CustomButton>
                <CustomButton
                    color='primary'
                    endIcon={<AddIcon />}
                    onClick={() =>
                        setDialogDetail(prev => ({ ...prev, open: true }))
                    }
                >
                    Novo
                </CustomButton>
            </Box>

            <CustomDataGrid<LIST_RESPONSE_DTO>
                height={'calc(100vh - 180px)'}
                page={page}
                columns={props.columns}
                loading={loading}
                paginationModel={paginationModel}
                onPaginationModelChange={handlePaginationModelChange}
                sortModel={sortModel}
                onSortModelChange={handleSortModelChange}
                rowActions={props.actions}
            />

            {props.dialogDetail({
                open: dialogDetail.open,
                onClose: () => {
                    setDialogDetail(prev => ({ ...prev, open: false }));
                }
            })}

            {props.dialogFiltro({
                open: dialogFiltro.open,
                onClose: () => {
                    setDialogFiltro(prev => ({ ...prev, open: false }));
                }
            })}
        </>
    );
};

export default BaseCrud;
