import type { JSX } from "react";
import { Navigate, Outlet } from "react-router";
import { Role } from "../auth/auth.model";
import { useAppSelector } from "../redux/store";

interface Props {
    children?: JSX.Element;
    allowedRole: Role;
};

const ProtectedRoute = ({ children, allowedRole }: Props) => {
    const role = useAppSelector(s => s.tokenState.role);
    const isAdmin = role === Role.ROLE_ADMIN;
    const allowedRoleIsOnlyAdmin = allowedRole == Role.ROLE_ADMIN;
    if (!isAdmin && allowedRoleIsOnlyAdmin) {
        return (
            <Navigate to={'/login'}></Navigate>
        );
    }
    return children ? <>{children}</> : <Outlet />;
};

export default ProtectedRoute;
