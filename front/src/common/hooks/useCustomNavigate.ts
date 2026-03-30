import { useNavigate, type NavigateOptions } from "react-router";
import type { RouterURL } from "../types/routerUrl";

const useCustomNavigate = () => {
  const navigate = useNavigate();

  return (routes: RouterURL[] | RouterURL, options?: NavigateOptions) => {
    navigate(`/${Array.isArray(routes) ? routes.join("/") : routes}`, options);
  };
};

export default useCustomNavigate;
