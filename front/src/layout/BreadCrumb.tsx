import { Box, Breadcrumbs, Typography } from '@mui/material';
import { useLocation } from 'react-router';
import { RouterURL } from '../common/types/routerUrl';

const BreadCrumb = () => {
    const location = useLocation();
    const pathName = location.pathname;

    const getBreadCrumb = () => {
        switch (pathName) {
            case `/${RouterURL.PAINEL}/${RouterURL.INICIO}`:
                return 'Início';
            case `/${RouterURL.PAINEL}/${RouterURL.PROJETO}`:
                return 'Projetos';
            case `/${RouterURL.PAINEL}/${RouterURL.TAREFA}`:
                return 'Tarefas';
        }
    };

    return (
        <Box
            sx={{
                borderBottom: '1px solid',
                borderColor: 'divider',
                pb: 1,
                mb: 2,
            }}
        >
            <Breadcrumbs >
                <Typography sx={{ fontWeight: 'bold', fontSize: '20px' }}>{getBreadCrumb()}</Typography>
            </Breadcrumbs>
        </Box>
    );
};

export default BreadCrumb;
