import type { UUID } from "../common/types/uuid";

export type LoginPayload = {
  email: string;
  senha: string;
};

export type RegisterPayload = LoginPayload & {
  nome: string;
};

export type TokenResponse = {
  token: string | null;
  role: Role | null;
  id: UUID | null;
};

export enum Role {
  ROLE_ADMIN = "ROLE_ADMIN",
  ROLE_MEMBER = "ROLE_MEMBER",
}
