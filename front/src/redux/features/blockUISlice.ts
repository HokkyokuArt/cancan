import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

type BlockUIProps = {
  loading: boolean;
  message?: string;
};
const initialState: BlockUIProps = {
  loading: false,
};

const blockUISlice = createSlice({
  name: "blockUI",
  initialState,
  reducers: {
    setLoading: (state, action: PayloadAction<BlockUIProps>) => {
      state.loading = action.payload.loading;
      state.message = action.payload.message;
    },
  },
});

export const { setLoading } = blockUISlice.actions;
export const blockUIReducer = blockUISlice.reducer;
