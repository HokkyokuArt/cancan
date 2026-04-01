import { configureStore } from "@reduxjs/toolkit";
import {
  useDispatch,
  useSelector,
  type TypedUseSelectorHook,
} from "react-redux";
import { tokenReducer } from "./features/tokenSlice";
import { projetoReducer } from "./features/projetoSlice";

export const store = configureStore({
  reducer: {
    tokenState: tokenReducer,
    projetoState: projetoReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
export const useAppDispatch: () => AppDispatch = useDispatch;
