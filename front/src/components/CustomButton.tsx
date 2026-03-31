import { Box, Button, IconButton, type ButtonProps } from "@mui/material";
import type { JSX } from 'react';

type Props = Omit<ButtonProps, 'variant'> & {
    isIcon?: boolean;
    children?: JSX.Element | string;
};

const CustomButton = (props: Props) => {
    return (


        <Box sx={{
            display: 'grid',
            placeItems: 'center',
            minWidth: 'fit-content'
        }}>

            {props.isIcon &&
                <IconButton
                    {...props}
                    sx={{
                        padding: '7px !important',
                        aspectRatio: '1/1',
                        border: '1px solid'
                    }}>
                    {props.children}
                </IconButton>
            }

            {
                !props.isIcon &&
                <Button
                    {...props}
                    sx={{
                        ...props.sx,
                        fontSize: 14,
                        borderRadius: '50px',
                        padding: '7px 14px',
                    }}
                    variant="outlined"
                >
                    {props.children}
                </Button>
            }
        </Box >
    );
};

export default CustomButton;
