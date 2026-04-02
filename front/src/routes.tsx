import { createBrowserRouter, Navigate } from "react-router";
import { Role } from "./auth/auth.model";
import { RouterURL } from "./common/types/routerUrl";
import BaseLayout from "./layout/BaseLayout";
import Home from "./pages/home/Home";
import Inicio from "./pages/inicio/Inicio";
import Login from "./pages/login/Login";
import ProjetoList from "./pages/projeto/ProjetoList";
import TarefaList from "./pages/tarefa/TarefaList";
import UsuarioList from "./pages/usuario/UsuarioList";
import Guard from "./security/Guard";
import ProtectedRoute from "./security/ProtectedRoute";

export const router = createBrowserRouter([
  {
    path: RouterURL.PAINEL,
    element: <Guard><BaseLayout /></Guard>,
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

      {
        element: <ProtectedRoute allowedRole={Role.ROLE_MEMBER} />,
        children: [
          { path: RouterURL.PROJETO, element: <ProjetoList />, }
        ]
      },

      {
        element: <ProtectedRoute allowedRole={Role.ROLE_MEMBER} />,
        children: [
          { path: RouterURL.TAREFA, element: <TarefaList />, }
        ]
      },

      {
        element: <ProtectedRoute allowedRole={Role.ROLE_ADMIN} />,
        children: [
          { path: RouterURL.USUARIO, element: <UsuarioList /> },
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
]);
