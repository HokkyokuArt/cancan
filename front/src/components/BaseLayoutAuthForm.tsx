import type { JSX } from '@emotion/react/jsx-runtime';
import { Box, Typography } from '@mui/material';

type Props = {
    title: string;
    children: JSX.Element | JSX.Element[];
    onSubmit: () => void;
};

const BaseLayoutAuthForm = (props: Props) => {
    return (
        <Box sx={{
            width: '100%',
            minHeight: '100vh',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
        }} >
            <form style={{
                width: '400px',
                // background: 'red',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                padding: '50px',
                margin: 0,
                borderRadius: '25px',
                gap: '20px'
            }}
                onSubmit={e => {
                    e.preventDefault();
                    props.onSubmit();
                }}
            >
                <Typography variant="h1" fontSize={'32px'}>{props.title}</Typography>
                {props.children}
            </form>
        </Box>
    );
};

export default BaseLayoutAuthForm;
