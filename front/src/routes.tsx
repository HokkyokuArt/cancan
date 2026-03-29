import { createBrowserRouter, Navigate } from "react-router";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";
import Guard from "./security/Guard";

export enum RouterURL {
  HOME = '/home',
  LOGIN = '/login',
  REGISTER = '/register'
}

export const router = createBrowserRouter([
  {
    path: "/painel",
    element: <Guard />,
    children: [
      {
        path: '',
        index: true,
        element:
          <Navigate
            replace={true}
            to={{ pathname: '' }}
          />,
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
  { path: RouterURL.REGISTER, element: <Register /> },
]);
