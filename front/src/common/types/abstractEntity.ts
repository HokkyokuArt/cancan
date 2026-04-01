import type { UUID } from "./uuid";

export type AbstractEntityDTO = {
  id: UUID;
  descritivo: string;
};

export type SuperPayloadResponseDTO = {
  id?: UUID;
  version?: number;
};
