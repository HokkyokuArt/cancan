import { Box, Dialog, DialogActions, DialogContent, DialogTitle, type Breakpoint } from '@mui/material';
import { type JSX } from 'react';
import CustomButton from './CustomButton';

export type CustomDialogProps = {
    open: boolean;
    onClose: () => void;
    title: string;
    onConfirm?: () => void;
    content: JSX.Element;
    actions?: (onClose: () => void) => JSX.Element;
    maxWidth?: Breakpoint;
};

const CustomDialog = (props: CustomDialogProps) => {
    return (
        <Dialog
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

                <DialogContent>
                    <Box sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        gap: '20px'
                    }}>
                        {props.content}
                    </Box>
                </DialogContent>

                <DialogActions sx={{ pb: '20px', px: '20px' }}>
                    {
                        props.actions?.(props.onClose)
                        ?? <Box sx={{
                            display: 'flex',
                            justifyContent: 'space-between',
                            width: '100%'
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
