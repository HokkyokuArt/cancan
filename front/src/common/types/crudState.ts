import type {
  ProjetoFiltroDTO,
  ProjetoListResponseDTO,
  ProjetoPayloadDTO,
} from "../../pages/projeto/projeto.model";
import type {
  TarefaFiltroDTO,
  TarefaListDTO,
  TarefaPayloadDTO,
  TarefaResponseDTO,
} from "../../pages/tarefa/tarefa.model";
import type {
  UsuarioFiltroDTO,
  UsuarioListResponseDTO,
  UsuarioPayloadDTO,
} from "../../pages/usuario/usuario.model";

export type CrudDtoTypeMap = {
  projeto: {
    PAYLOAD_DTO: ProjetoPayloadDTO;
    RESPONSE_DTO: ProjetoPayloadDTO;
    LIST_RESPONSE_DTO: ProjetoListResponseDTO;
    FILTRO_DTO: ProjetoFiltroDTO;
  };
  tarefa: {
    PAYLOAD_DTO: TarefaPayloadDTO;
    RESPONSE_DTO: TarefaResponseDTO;
    LIST_RESPONSE_DTO: TarefaListDTO;
    FILTRO_DTO: TarefaFiltroDTO;
  };
  usuario: {
    PAYLOAD_DTO: UsuarioPayloadDTO;
    RESPONSE_DTO: TarefaResponseDTO;
    LIST_RESPONSE_DTO: UsuarioListResponseDTO;
    FILTRO_DTO: UsuarioFiltroDTO;
  };
};

export type CrudDtoTypeMapKey = keyof CrudDtoTypeMap;

export type GenericPayloadDTO<K extends CrudDtoTypeMapKey> =
  CrudDtoTypeMap[K]["PAYLOAD_DTO"];

export type GenericResponseDTO<K extends CrudDtoTypeMapKey> =
  CrudDtoTypeMap[K]["RESPONSE_DTO"];

export type GenericListResponseDTO<K extends CrudDtoTypeMapKey> =
  CrudDtoTypeMap[K]["LIST_RESPONSE_DTO"];

export type GenericFiltroDTO<K extends CrudDtoTypeMapKey> =
  CrudDtoTypeMap[K]["FILTRO_DTO"];

export type CrudState<K extends CrudDtoTypeMapKey> = {
  entidade: GenericPayloadDTO<K> | null;
  entidadeVisualizar: GenericResponseDTO<K> | null;
  filtro: GenericFiltroDTO<K> | null;
};
