import { setLoading } from "../../redux/features/blockUISlice";
import { useAppDispatch } from "../../redux/store";

const useLoading = () => {
  const dispatch = useAppDispatch();

  return {
    enableLoader: (message?: string) => {
      dispatch(setLoading({ loading: true, message }));
    },
    disabledLoader: () => {
      dispatch(setLoading({ loading: false }));
    },
  };
};

export default useLoading;
