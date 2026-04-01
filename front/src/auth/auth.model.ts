import type { UUID } from "../common/types/uuid";

export type LoginPayload = {
  email: string;
  senha: string;
};

export type RegisterPayload = LoginPayload & {
  nome: string;
};

export type TokenResponse = {
  token: string;
  role: Role | null;
  id: UUID;
  nome: string;
};

export enum Role {
  ROLE_ADMIN = "ROLE_ADMIN",
  ROLE_MEMBER = "ROLE_MEMBER",
}
