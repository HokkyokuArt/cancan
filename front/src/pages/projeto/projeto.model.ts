import type { SuperPayloadResponseDTO } from "../../common/types/abstractEntity";
import type { Filter } from "../../common/types/pageable";
import type { UUID } from "../../common/types/uuid";

export type ProjetoPayloadDTO = SuperPayloadResponseDTO & {
  nome: string;
  sigla: string;
  descricao: string;
  dono: UUID;
  membros: UUID[];
};

export type ProjetoFiltroDTO = Filter & {
  nome: string;
};
