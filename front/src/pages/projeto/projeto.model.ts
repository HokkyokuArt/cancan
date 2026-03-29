import type { SuperPayloadResponseDTO } from "../../common/types/abstractEntity";
import type { UUID } from "../../common/types/uuid";

export type ProjetoPayloadDTO = SuperPayloadResponseDTO & {
  nome: string;
  descricao: string;
  dono: UUID;
  membros: UUID[];
};

export type ProjetoFiltroDTO = {
  nome: string;
};
