import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import { deepReset } from "../../utils/utils";
import type { TokenResponse } from "./../../auth/auth.model";

const initialState: TokenResponse = {
  token: "",
  role: null,
  id: "",
  nome: "",
};

const tokenSlice = createSlice({
  name: "token",
  initialState,
  reducers: {
    setToken: (state, action: PayloadAction<TokenResponse>) => {
      Object.assign(state, action.payload);
    },
    resetToken: (state) => {
      deepReset(state, initialState);
    },
  },
});

export const { setToken, resetToken } = tokenSlice.actions;
export const tokenReducer = tokenSlice.reducer;
