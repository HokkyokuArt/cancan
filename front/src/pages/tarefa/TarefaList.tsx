import HorizontalRuleIcon from '@mui/icons-material/HorizontalRule';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import LocalFireDepartmentIcon from '@mui/icons-material/LocalFireDepartment';
import { Box, Tooltip } from "@mui/material";
import type { GridColDef } from "@mui/x-data-grid";
import type { CrudState } from "../../common/types/crudState";
import { OrderDirection } from "../../common/types/pageable";
import BaseCrud, { CrudAction } from "../../layout/BaseCrud";
import { setTarefaState } from "../../redux/features/tarefaSlice";
import { useAppDispatch } from "../../redux/store";
import type { TarefaListDTO } from "./tarefa.model";
import TarefaDetail from "./TarefaDetail";
import TarefaFiltro from './TarefaFiltro';

const prioridadeIconInfoMap = {
    LOW: <KeyboardArrowDownIcon color="secondary" />,
    MEDIUM: <HorizontalRuleIcon color="primary" />,
    HIGH: <KeyboardArrowUpIcon color='warning' />,
    CRITICAL: <LocalFireDepartmentIcon color='error' />,
};

const columns: GridColDef<TarefaListDTO>[] = [
    {
        field: 'prioridade',
        headerName: 'Prioridade',
        sortable: true,
        headerAlign: 'center',
        width: 150,
        renderCell: (params) =>
            <Tooltip title={params.value} placement="right">
                <Box
                    sx={{
                        display: 'grid',
                        placeItems: 'center',
                        height: '100%'
                    }}
                >
                    {prioridadeIconInfoMap[params.value as keyof typeof prioridadeIconInfoMap]}
                </Box>
            </Tooltip>
    },
    {
        field: 'codigo',
        headerName: 'Código',
        sortable: true,
        width: 200,
    },
    {
        field: 'titulo',
        headerName: 'Título',
        sortable: true,
        flex: 1,
    },
    {
        field: 'status',
        headerName: 'Status',
        sortable: true,
        width: 200,
    },

    {
        field: 'responsavel',
        headerName: 'Responsável',
        sortable: true,
        width: 200,
    },
    {
        field: 'dataCriacao',
        headerName: 'Criação',
        sortable: true,
    },
    {
        field: 'prazo',
        headerName: 'Prazo',
        sortable: true,
    },
];

const Tarefa = () => {

    const dispatch = useAppDispatch();
    const handleSetState = (_: CrudAction, crudState: Partial<CrudState<"tarefa">>) => {
        dispatch(setTarefaState(crudState));
    };

    return (
        <BaseCrud<"tarefa">
            entityName="tarefa"
            onSetCrudState={handleSetState}
            columns={columns}
            initialSort={[
                {
                    field: 'codigo',
                    sort: OrderDirection.DESC,
                }
            ]}
            dialogDetail={props => <TarefaDetail {...props} />}
            dialogFiltro={props => <TarefaFiltro {...props} />}
        />
    );
};

export default Tarefa;
