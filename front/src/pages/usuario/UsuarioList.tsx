import type { GridColDef } from '@mui/x-data-grid';
import type { CrudState } from '../../common/types/crudState';
import { OrderDirection } from '../../common/types/pageable';
import type { CrudAction } from '../../layout/BaseCrud';
import BaseCrud from '../../layout/BaseCrud';
import { setUsuarioState } from '../../redux/features/usuarioSlice';
import { useAppDispatch } from '../../redux/store';
import type { UsuarioListResponseDTO } from './usuario.model';
import UsuarioDetail from './UsuarioDetail';


const columns: GridColDef<UsuarioListResponseDTO>[] = [
  {
    field: 'nome',
    headerName: 'Nome',
    sortable: true,
    flex: 1,
  },
  {
    field: 'email',
    headerName: 'Email',
    sortable: true,
    flex: 1,
  },
  {
    field: 'role',
    headerName: 'Role',
    sortable: true,
    width: 200,
  },
];

const UsuarioList = () => {
  const dispatch = useAppDispatch();
  const handleSetState = (_: CrudAction, crudState: Partial<CrudState<"usuario">>) => {
    dispatch(setUsuarioState(crudState));
  };

  return (
    <BaseCrud<"usuario">
      entityName="usuario"
      onSetCrudState={handleSetState}
      columns={columns}
      chipsConfig={{}}
      initialSort={[
        {
          field: 'nome',
          sort: OrderDirection.DESC,
        }
      ]}
      dialogDetail={props => <UsuarioDetail {...props} />}
    />
  );
};

export default UsuarioList;
