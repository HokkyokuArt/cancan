import { Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { type JSX } from 'react';
import CustomButton from './CustomButton';

export type CustomDialogProps = {
    open: boolean;
    onClose: () => void;
    title: string;
    onConfirm?: () => void;
    content: JSX.Element;
    actions?: (onClose: () => void) => JSX.Element;
};

const CustomDialog = (props: CustomDialogProps) => {
    return (
        <Dialog open={props.open} onClose={props.onClose}>
            <DialogTitle>{props.title}</DialogTitle>
            <DialogContent>
                <form onSubmit={props.onConfirm}>
                    {props.content}
                </form>
            </DialogContent>
            <DialogActions>
                {
                    props.actions?.(props.onClose) ?? <>
                        <CustomButton onClick={props.onClose} color='error'>Cancelar</CustomButton>
                        <CustomButton type="submit">
                            Confirmar
                        </CustomButton>
                    </>
                }
            </DialogActions>
        </Dialog>
    );
};

export default CustomDialog;
