import type { GridColDef } from "@mui/x-data-grid";
import type { AbstractEntityDTO } from "../../common/types/abstractEntity";
import type { DataGridRowAction } from "../../components/CustomDataGrid";
import CustomDialog from "../../components/CustomDialog";
import BaseCrud from "../../layout/BaseCrud";
import type { ProjetoFiltroDTO, ProjetoPayloadDTO } from "./projeto.model";

const columns: GridColDef<AbstractEntityDTO>[] = [
    {
        field: 'id',
        headerName: 'UUID',
        flex: 1,
        sortable: true,
    },
    {
        field: 'descritivo',
        headerName: 'Descritivo',
        width: 150,
        sortable: true,
    },
];

const actions: DataGridRowAction<AbstractEntityDTO>[] = [
    {
        id: 'editar',
        label: 'Editar',
        // icon: <AddIcon />,
        onClick: (row) => {
            console.log('Editar', row);
        },
    },
    {
        id: 'excluir',
        label: 'Excluir',
        // icon: <AddIcon />,
        onClick: (row) => {
            console.log('Excluir', row);
        },
        disabled: (row) => true,
    },
];


const Projeto = () => {
    return (
        <BaseCrud<ProjetoPayloadDTO, AbstractEntityDTO, ProjetoFiltroDTO >
            requestPath="/projeto"
            columns={columns}
            actions={actions}
            dialogDetail={props => <CustomDialog
                {...props}
                title={"Novo"}
                content={<>AAAAAAAAAAAA</>}
            />}

            dialogFiltro={props => <CustomDialog
                {...props}
                title={"Filtro"}
                content={<>AAAAAAAAAAAA</>}
            />}
        />
    );
};

export default Projeto;
