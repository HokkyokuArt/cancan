import type { UUID } from "../../common/types/uuid";

export type ProjetoPayloadDTO = {
  id?: UUID; // tirar daqui
  version?: number; // tirar daqui
  nome: string;
  descricao: string;
  dono: UUID;
  membros: UUID[];
};
