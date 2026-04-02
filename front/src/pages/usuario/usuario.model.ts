import { Role } from "../../auth/auth.model";
import type {
  AbstractEntityDTO,
  SuperPayloadResponseDTO,
} from "../../common/types/abstractEntity";
import type { Filter } from "../../common/types/pageable";

export type UsuarioFiltroDTO = Filter & {
  roles: Role[];
};

export type UsuarioPayloadDTO = SuperPayloadResponseDTO & {
  nome: string;
  email: string;
  senha: string;
  role: Role;
};

export type UsuarioResponseDTO = SuperPayloadResponseDTO & {
  nome: string;
  email: string;
  role: Role;
};

export type UsuarioListResponseDTO = AbstractEntityDTO & {
  nome: string;
  email: string;
  role: string;
};
