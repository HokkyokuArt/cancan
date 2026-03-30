import { Box, Drawer, List, ListItem, ListItemButton, ListItemText } from '@mui/material';
import useCustomNavigate from '../common/hooks/useCustomNavigate';
import { RouterURL } from '../common/types/routerUrl';


const menuWidth = 240;
const menuItens = [
    {
        id: RouterURL.HOME,
        label: 'HOME',
        routes: [RouterURL.HOME],
    },
    {
        id: RouterURL.REGISTER,
        label: 'REGISTER',
        routes: [RouterURL.REGISTER],
    },
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
                    '& .MuiDrawer-paper': { boxSizing: 'border-box', width: menuWidth },
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
                    </List>
                </Box>
            </Drawer>
        </nav>
    );
};

export default SideMenu;
