import type {
  ProjetoFiltroDTO,
  ProjetoListDTO,
  ProjetoPayloadDTO,
} from "../../pages/projeto/projeto.model";
import type {
  TarefaFiltroDTO,
  TarefaPayloadDTO,
  TarefaResponseDTO,
} from "../../pages/tarefa/tarefa.model";
import type { AbstractEntityDTO } from "./abstractEntity";

export type CrudDtoTypeMap = {
  projeto: {
    PAYLOAD_DTO: ProjetoPayloadDTO;
    RESPONSE_DTO: ProjetoPayloadDTO;
    LIST_RESPONSE_DTO: ProjetoListDTO;
    FILTRO_DTO: ProjetoFiltroDTO;
  };
  tarefa: {
    PAYLOAD_DTO: TarefaPayloadDTO;
    RESPONSE_DTO: TarefaResponseDTO;
    LIST_RESPONSE_DTO: AbstractEntityDTO;
    FILTRO_DTO: TarefaFiltroDTO;
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
};
