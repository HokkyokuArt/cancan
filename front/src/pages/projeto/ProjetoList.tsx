import type { GridColDef } from "@mui/x-data-grid";
import type { CrudState } from "../../common/types/crudState";
import { OrderDirection } from "../../common/types/pageable";
import CustomDialog from "../../components/CustomDialog";
import BaseCrud, { CrudAction } from "../../layout/BaseCrud";
import { setProjetoState } from "../../redux/features/projetoSlice";
import { useAppDispatch } from "../../redux/store";
import ProjetoDetail from "./ProjetoDetail";
import type { ProjetoListResponseDTO } from "./projeto.model";

const columns: GridColDef<ProjetoListResponseDTO>[] = [
    {
        field: 'sigla',
        headerName: 'Sigla',
        width: 150,
        sortable: true,
    },
    {
        field: 'nome',
        headerName: 'Nome',
        flex: 1,
        sortable: true,
    },
    {
        field: 'dono',
        headerName: 'Dono',
        width: 150,
        sortable: true,
    },
];

const Projeto = () => {
    const dispatch = useAppDispatch();
    const handleSetState = (_: CrudAction, crudState: Partial<CrudState<"projeto">>) => {
        dispatch(setProjetoState(crudState));
    };

    return (
        <BaseCrud<"projeto">
            entityName="projeto"
            onSetCrudState={handleSetState}
            columns={columns}
            initialSort={[
                {
                    field: 'nome',
                    sort: OrderDirection.ASC,
                }
            ]}
            dialogDetail={props => <ProjetoDetail {...props} />}
            dialogFiltro={props => <CustomDialog
                {...props}
                title={"Filtro"}
                content={<>AAAAAAAAAAAA</>}
            />}
        />
    );
};

export default Projeto;
