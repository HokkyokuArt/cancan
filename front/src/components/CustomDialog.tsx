import { Box, Dialog, DialogActions, DialogContent, DialogTitle, useMediaQuery, useTheme, type Breakpoint } from '@mui/material';
import { type JSX } from 'react';
import CustomButton from './CustomButton';

export type CustomDialogProps = {
    open: boolean;
    onClose: () => void;
    title: string;
    onConfirm?: () => void;
    content: JSX.Element;
    actions?: (onClose: () => void, isMobile: boolean) => JSX.Element;
    maxWidth?: Breakpoint;
};

const CustomDialog = (props: CustomDialogProps) => {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('md'));
    return (
        <Dialog
            fullScreen={isMobile}
            open={props.open}
            onClose={props.onClose}
            fullWidth
            maxWidth={props.maxWidth}

        >
            <form onSubmit={e => {
                e.preventDefault();
                props.onConfirm?.();
            }}>
                <DialogTitle>{props.title}</DialogTitle>

                <DialogContent sx={{
                    marginBottom: isMobile ? '70px' : 'inherit'
                }}>
                    <Box sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        gap: '20px'
                    }}>
                        {props.content}
                    </Box>
                </DialogContent>

                <DialogActions sx={{ width: 'calc(100% - 42px)', pb: '20px', px: '20px', position: isMobile ? 'fixed' : '', bottom: 0 }}>
                    {
                        props.actions?.(props.onClose, isMobile)
                        ?? <Box sx={{
                            display: 'flex',
                            justifyContent: isMobile ? 'flex-end' : 'space-between',
                            width: '100%',
                            gap: '20px',
                        }}>
                            <CustomButton onClick={props.onClose} color='error'>Cancelar</CustomButton>
                            <CustomButton type="submit">
                                Confirmar
                            </CustomButton>
                        </Box>
                    }
                </DialogActions>
            </form>
        </Dialog>
    );
};

export default CustomDialog;
