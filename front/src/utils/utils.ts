export const deepReset = <T extends Record<string, any>>(
  state: T,
  initialState: T,
): T => {
  for (let prop in state) {
    delete state[prop as keyof T];
  }
  return Object.assign(state, initialState);
};
