import { Role } from "../../auth/auth.model";
import type { Filter } from "../../common/types/pageable";

export type UsuarioFiltroDTO = Filter & {
  roles: Role[];
};
