import useCustomNavigate from "../common/hooks/useCustomNavigate";
import useRequestService from "../common/services/useRequestService";
import { RouterURL } from "../common/types/routerUrl";
import type { UUID } from "../common/types/uuid";
import { resetToken, setToken } from "../redux/features/tokenSlice";
import { useAppDispatch } from "./../redux/store";
import type {
  LoginPayload,
  RegisterPayload,
  TokenResponse,
} from "./auth.model";

const useAuthService = () => {
  const { postRequest } = useRequestService();
  const dispatch = useAppDispatch();
  const navigate = useCustomNavigate();
  const _path = "/auth";

  return {
    logar(payload: LoginPayload) {
      postRequest<TokenResponse>({
        url: `${_path}/logar`,
        body: payload,
        then: (res) => {
          dispatch(setToken(res));
          navigate([RouterURL.PAINEL, RouterURL.INICIO]);
        },
        catch: (err) => {
          console.log(err);
        },
      });
    },

    deslogar() {
      dispatch(resetToken());
      navigate([RouterURL.LOGIN]);
    },

    register(payload: RegisterPayload) {
      postRequest<UUID>({
        url: `${_path}/register`,
        body: payload,
        then: (res) => {
          console.log({ res });
        },
        catch: (err) => {
          console.log(err);
        },
      });
    },
  };
};

export default useAuthService;
