import { createBrowserRouter, Navigate } from "react-router";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Guard from "./security/Guard";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Guard />,
    children: [
      {
        path: '',
        index: true,
        element:
          <Navigate
            replace={true}
            to={{ pathname: '/home' }}
          />,
      },

      { path: 'home', element: <Home /> },
    ]
  },
  { path: 'login', element: <Login /> },
]);
