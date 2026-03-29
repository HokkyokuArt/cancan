export type LoginPayload = {
  email: string;
  senha: string;
};

export type RegisterPayload = LoginPayload & {
  nome: string;
};

export type TokenResponse = {
  token: string;
};
