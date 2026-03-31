import type { SuperPayloadResponseDTO } from "../../common/types/abstractEntity";
import type { UUID } from "../../common/types/uuid";

export type TarefaPayloadDTO = SuperPayloadResponseDTO & {
  titulo: string;
  descricao: string;
  status: string;
  prioridade: string;
  responsavel: UUID;
  projeto: UUID;
  prazo: Date;
};

export type TarefaResponseDTO = SuperPayloadResponseDTO & {
  titulo: string;
  codigo: string;
  descricao: string;
  status: string;
  prioridade: string;
  responsavel: UUID;
  projeto: UUID;
  dataCriacao: Date;
  ultimaAtualizacao: Date;
  prazo: Date;
};

export type TarefaFiltroDTO = {
  status?: string;
  prioridade?: string;
  responsavel?: UUID;
  datas?: Date[];
};
