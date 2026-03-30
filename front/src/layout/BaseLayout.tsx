import { Toolbar } from '@mui/material';
import Box from '@mui/material/Box';
import { useState } from 'react';
import { Outlet } from 'react-router';
import SideMenu from './SideMenu';
import TopBar from './TopBar';

const BaseLayout = () => {
    const [openMenu, setOpenMenu] = useState(false);

    const handleDrawerToggle = () => {
        setOpenMenu((prevState) => !prevState);
    };

    return (
        <>
            <Box sx={{ display: 'flex' }}>
                <TopBar onToggleMenu={handleDrawerToggle} />
                <SideMenu open={openMenu} onClose={handleDrawerToggle} />
                <Box component="main" sx={{ p: 3 }}>
                    <Toolbar />
                    <Outlet />
                </Box>
            </Box>

        </>
    );
};

export default BaseLayout;
