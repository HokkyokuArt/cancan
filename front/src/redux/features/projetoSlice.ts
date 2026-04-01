import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { CrudState } from "../../common/types/crudState";

const initialState: CrudState<"projeto"> = {
  entidade: null,
  entidadeVisualizar: null,
};

const projetoSlice = createSlice({
  name: "projeto",
  initialState,
  reducers: {
    setProjetoState: (
      state,
      action: PayloadAction<Partial<CrudState<"projeto">>>,
    ) => {
      Object.assign(state, action.payload);
    },
  },
});

export const { setProjetoState } = projetoSlice.actions;
export const projetoReducer = projetoSlice.reducer;
