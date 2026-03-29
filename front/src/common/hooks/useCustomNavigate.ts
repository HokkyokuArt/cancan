import { useNavigate, type NavigateOptions } from "react-router";
import type { RouterURL } from "../../routes";

const useCustomNavigate = () => {
  const navigate = useNavigate();

  return (routes: RouterURL[] | RouterURL, options?: NavigateOptions) => {
    navigate(`/${Array.isArray(routes) ? routes.join("/") : routes}`, options);
  };
};

export default useCustomNavigate;
