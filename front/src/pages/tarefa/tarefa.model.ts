import type { SuperPayloadResponseDTO } from "../../common/types/abstractEntity";
import type { Filter } from "../../common/types/pageable";
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

export type TarefaFiltroDTO = Filter & {};
