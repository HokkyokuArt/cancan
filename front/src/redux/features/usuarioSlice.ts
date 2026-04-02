import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { CrudState } from "../../common/types/crudState";

const initialState: CrudState<"usuario"> = {
  entidade: null,
  entidadeVisualizar: null,
  filtro: null,
};

const usuarioSlice = createSlice({
  name: "usuario",
  initialState,
  reducers: {
    setUsuarioState: (
      state,
      action: PayloadAction<Partial<CrudState<"usuario">>>,
    ) => {
      Object.assign(state, action.payload);
    },
  },
});

export const { setUsuarioState } = usuarioSlice.actions;
export const usuarioReducer = usuarioSlice.reducer;
