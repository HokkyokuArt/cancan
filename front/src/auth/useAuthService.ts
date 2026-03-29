import useCustomNavigate from "../common/hooks/useCustomNavigate";
import useRequestService from "../common/services/useRequestService";
import type { UUID } from "../common/types/uuid";
import { resetToken, setToken } from "../redux/features/tokenSlice";
import { RouterURL } from "../routes";
import { useAppDispatch } from "./../redux/store";
import type {
  LoginPayload,
  RegisterPayload,
  TokenResponse,
} from "./auth.model";

const useAuthService = () => {
  const { post } = useRequestService();
  const dispatch = useAppDispatch();
  const navigate = useCustomNavigate();
  const _path = "/auth";

  return {
    logar(payload: LoginPayload) {
      post<TokenResponse>({
        url: `${_path}/logar`,
        body: payload,
        then: (res) => {
          dispatch(setToken(res));
          navigate([RouterURL.PAINEL, RouterURL.INICIO]);
        },
        catch: (err) => {
          console.log(err.body);
        },
      });
    },

    deslogar() {
      dispatch(resetToken());
    },

    register(payload: RegisterPayload) {
      post<UUID>({
        url: `${_path}/register`,
        body: payload,
        then: (res) => {
          console.log({ res });
        },
        catch: (err) => {
          console.log(err.body);
        },
      });
    },
  };
};

export default useAuthService;
