import type {
  AbstractEntityDTO,
  SuperPayloadResponseDTO,
} from "../../common/types/abstractEntity";
import type { Filter } from "../../common/types/pageable";
import type { UUID } from "../../common/types/uuid";

export type TarefaPayloadDTO = SuperPayloadResponseDTO & {
  titulo: string;
  descricao: string;
  status: string;
  prioridade: string;
  responsavel: UUID;
  projeto: UUID;
  prazo: Date | null;
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

export type TarefaListDTO = AbstractEntityDTO & {
  titulo: string;
  codigo: string;
  status: string;
  prioridade: string;
  responsavel: string;
  prazo: string;
  dataCriacao: string;
};

export type TarefaFiltroDTO = Filter & {
  status?: string | null;
  prioridade?: string | null;
  responsavel?: UUID | null;
  criacaoInicio?: Date | null;
  criacaoFim?: Date | null;
  prazoInicio?: Date | null;
  prazoFim?: Date | null;
};
