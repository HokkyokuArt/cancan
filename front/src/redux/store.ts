import { configureStore } from "@reduxjs/toolkit";
import {
  useDispatch,
  useSelector,
  type TypedUseSelectorHook,
} from "react-redux";
import { blockUIReducer } from "./features/blockUISlice";
import { projetoReducer } from "./features/projetoSlice";
import { tokenReducer } from "./features/tokenSlice";

export const store = configureStore({
  reducer: {
    blockUIState: blockUIReducer,
    tokenState: tokenReducer,
    projetoState: projetoReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
export const useAppDispatch: () => AppDispatch = useDispatch;
