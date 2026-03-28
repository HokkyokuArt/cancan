import type { JSX } from "react";
import { Navigate, Outlet } from "react-router";

interface Props {
    children?: JSX.Element;
};

const Guard = ({ children }: Props) => {
    const isAuthenticated = true;
    if (!isAuthenticated) {
        return (
            <Navigate to={'/login'}></Navigate>
        );
    }
    return children ? <>{children}</> : <Outlet />;
};

export default Guard;
