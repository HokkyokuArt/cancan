import type { JSX } from "react";
import { Navigate, Outlet } from "react-router";
import { useAppSelector } from "../redux/store";

interface Props {
    children?: JSX.Element;
};

const Guard = ({ children }: Props) => {
    const { token } = useAppSelector(s => s.tokenState);
    const isAuthenticated = !!token;
    if (!isAuthenticated) {
        return (
            <Navigate to={'/login'}></Navigate>
        );
    }
    return children ? <>{children}</> : <Outlet />;
};

export default Guard;
