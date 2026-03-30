import MenuIcon from '@mui/icons-material/Menu';
import { AppBar, Box, Button, IconButton, Toolbar, Typography } from '@mui/material';
import useAuthService from '../auth/useAuthService';

type Props = {
    onToggleMenu: () => void;
};

const TopBar = (props: Props) => {
    const { deslogar } = useAuthService();

    return (
        <AppBar component="nav">
            <Toolbar>
                <IconButton
                    color="inherit"
                    edge="start"
                    onClick={props.onToggleMenu}
                    sx={{ mr: 2 }}
                >
                    <MenuIcon />
                </IconButton>
                <Typography
                    variant="h6"
                    component="div"
                    sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}
                >
                    Can can
                </Typography>
                <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
                    <Button onClick={() => deslogar()} sx={{ color: '#fff' }}>Logout</Button>
                </Box>
            </Toolbar>
        </AppBar>

    );
};

export default TopBar;
