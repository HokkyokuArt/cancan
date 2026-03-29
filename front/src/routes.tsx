import { createBrowserRouter, Navigate } from "react-router";
import { Role } from "./auth/auth.model";
import Home from "./pages/home/Home";
import Inicio from "./pages/inicio/Inicio";
import Login from "./pages/login/Login";
import Guard from "./security/Guard";
import ProtectedRoute from "./security/ProtectedRoute";

export enum RouterURL {
  HOME = 'home',
  LOGIN = 'login',
  REGISTER = 'register',
  PAINEL = 'painel',
  INICIO = 'inicio'
}

export const router = createBrowserRouter([
  {
    path: RouterURL.PAINEL,
    element: <Guard />,
    children: [
      {
        path: '',
        index: true,
        element:
          <Navigate
            replace={true}
            to={{ pathname: RouterURL.INICIO }}
          />,
      },
      {
        element: <ProtectedRoute allowedRole={Role.ROLE_MEMBER} />,
        children: [
          { path: RouterURL.INICIO, element: <Inicio />, }
        ]
      },
    ]
  },

  {
    path: '',
    index: true,
    element:
      <Navigate
        replace={true}
        to={{ pathname: RouterURL.HOME }}
      />,
  },
  { path: RouterURL.HOME, element: <Home /> },
  { path: RouterURL.LOGIN, element: <Login /> },
  // { path: RouterURL.REGISTER, element: <Register /> },
]);
