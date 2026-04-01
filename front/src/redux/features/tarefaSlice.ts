import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { CrudState } from "../../common/types/crudState";

const initialState: CrudState<"tarefa"> = {
  entidade: null,
  entidadeVisualizar: null,
};

const tarefaSlice = createSlice({
  name: "tarefa",
  initialState,
  reducers: {
    setTarefaState: (
      state,
      action: PayloadAction<Partial<CrudState<"tarefa">>>,
    ) => {
      Object.assign(state, action.payload);
    },
  },
});

export const { setTarefaState } = tarefaSlice.actions;
export const tarefaReducer = tarefaSlice.reducer;
