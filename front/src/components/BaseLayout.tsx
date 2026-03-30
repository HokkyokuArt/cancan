import { Button } from '@mui/material';
import { Outlet } from 'react-router';
import useCustomNavigate from '../common/hooks/useCustomNavigate';
import { RouterURL } from '../routes';
import useAuthService from '../auth/useAuthService';


const BaseLayout = () => {
    const navigate = useCustomNavigate();
    const { deslogar } = useAuthService();
    return (
        <>
            <h1>CAN CAN</h1>
            <Button onClick={() => navigate(RouterURL.HOME)} >HOME</Button>
            <Button onClick={() => deslogar()} >LOGOUT</Button>
            <Button onClick={() => navigate(RouterURL.REGISTER)} >REGISTER</Button>
            <Button onClick={() => navigate([RouterURL.PAINEL, RouterURL.INICIO])} >INICIO</Button>
            <Button onClick={() => navigate([RouterURL.PAINEL, RouterURL.PROJETO])} >PROJETO</Button>
            <Button onClick={() => navigate([RouterURL.PAINEL, RouterURL.TAREFA])} >TAREFA</Button>

            <Outlet />
        </>
    );
};

export default BaseLayout;
