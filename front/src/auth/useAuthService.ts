import useRequestService from "../common/services/useRequestService";
import type { UUID } from "../common/types/uuid";
import type {
  LoginPayload,
  RegisterPayload,
  TokenResponse,
} from "./auth.model";

const useAuthService = () => {
  const { post } = useRequestService();
  const _path = "/auth";

  return {
    logar(payload: LoginPayload) {
      post<TokenResponse>({
        url: `${_path}/login`,
        body: payload,
        then: (res) => {
          console.log(res);
        },
        catch: (err) => {
          console.log(err.body);
        },
      });
    },

    register(payload: RegisterPayload) {
      post<UUID>({
        url: `${_path}/register`,
        body: payload,
        then: (res) => {
          console.log({res});
        },
        catch: (err) => {
          console.log(err.body);
        },
      });
    },
  };
};

export default useAuthService;
