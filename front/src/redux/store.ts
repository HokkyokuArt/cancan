import { configureStore } from "@reduxjs/toolkit";
import {
  useDispatch,
  useSelector,
  type TypedUseSelectorHook,
} from "react-redux";
import { blockUIReducer } from "./features/blockUISlice";
import { projetoReducer } from "./features/projetoSlice";
import { tarefaReducer } from "./features/tarefaSlice";
import { tokenReducer } from "./features/tokenSlice";
import { usuarioReducer } from "./features/usuarioSlice";

export const store = configureStore({
  reducer: {
    blockUIState: blockUIReducer,
    tokenState: tokenReducer,
    projetoState: projetoReducer,
    tarefaState: tarefaReducer,
    usuarioState: usuarioReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
export const useAppDispatch: () => AppDispatch = useDispatch;
