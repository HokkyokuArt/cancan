import { Box, Drawer, List, ListItem, ListItemButton, ListItemText, useMediaQuery, useTheme } from '@mui/material';
import useCustomNavigate from '../common/hooks/useCustomNavigate';
import { RouterURL } from '../common/types/routerUrl';
import useAuthService from '../auth/useAuthService';


const menuWidth = 240;
const menuItens = [
    {
        id: RouterURL.HOME,
        label: 'HOME',
        routes: [RouterURL.HOME],
    },
    // {
    //     id: RouterURL.REGISTER,
    //     label: 'REGISTER',
    //     routes: [RouterURL.PAINEL, RouterURL.REGISTER],
    // },
    {
        id: RouterURL.INICIO,
        label: 'INICIO',
        routes: [RouterURL.PAINEL, RouterURL.INICIO],
    },
    {
        id: RouterURL.PROJETO,
        label: 'PROJETO',
        routes: [RouterURL.PAINEL, RouterURL.PROJETO],
    },
    {
        id: RouterURL.TAREFA,
        label: 'TAREFA',
        routes: [RouterURL.PAINEL, RouterURL.TAREFA],
    },
];

type Props = {
    open: boolean;
    onClose: () => void;
};

const SideMenu = (props: Props) => {
    const navigate = useCustomNavigate();
    const { deslogar } = useAuthService();

    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('md'));
    return (
        <nav>
            <Drawer
                variant="temporary"
                open={props.open}
                onClose={props.onClose}
                ModalProps={{
                    keepMounted: true,
                }}
                sx={{
                    '& .MuiDrawer-paper': { boxSizing: 'border-box', width: isMobile ? '100%' : menuWidth },
                }}
            >
                <Box onClick={props.onClose} sx={{ textAlign: 'center' }}>
                    <List>
                        {menuItens.map(item => (
                            <ListItem key={item.id} disablePadding>
                                <ListItemButton sx={{ textAlign: 'center' }}>
                                    <ListItemText primary={item.label} onClick={() => navigate(item.routes)} />
                                </ListItemButton>
                            </ListItem>
                        ))}

                        {isMobile && <ListItem disablePadding>
                            <ListItemButton sx={{ textAlign: 'center' }}>
                                <ListItemText primary={'LOGOUT'} onClick={() => deslogar()} />
                            </ListItemButton>
                        </ListItem>}
                    </List>
                </Box>
            </Drawer>
        </nav>
    );
};

export default SideMenu;
