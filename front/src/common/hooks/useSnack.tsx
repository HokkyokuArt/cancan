import CloseIcon from '@mui/icons-material/Close';
import { type AlertColor, IconButton } from '@mui/material';
import { useSnackbar } from 'notistack';

const SnackbarCloseButton = ({ snackbarKey }: any) => {
  const { closeSnackbar } = useSnackbar();
  return (
    <IconButton onClick={() => closeSnackbar(snackbarKey)} sx={{ minHeight: '50px' }}>
      <CloseIcon sx={{ color: 'white' }} />
    </IconButton>
  );
};

export type CustomSnack = {
  id: string,
  message: string;
  severity?: AlertColor;
  hideDuration?: number;
  closeable?: boolean;
  persist?: boolean;
  preventDuplicate?: boolean;
};

export const useSnack = () => {
  const { closeSnackbar, enqueueSnackbar } = useSnackbar();

  const addSnack = (props: CustomSnack) => {
    enqueueSnackbar({
      action: props.closeable && (snackbarKey => <SnackbarCloseButton snackbarKey={snackbarKey} />),
      message: props.message,
      key: props.id,
      variant: props.severity ? props.severity : 'error',
      persist: props.persist,
      autoHideDuration: props.hideDuration ? props.hideDuration : 5000,
      preventDuplicate: props.preventDuplicate !== undefined ? props.preventDuplicate : true
    });
  };

  return {
    addStack: (snacks: Array<CustomSnack>) => {
      snacks.forEach(s => addSnack({ ...s }));
    },
    addError: (snack: CustomSnack) => {
      addSnack({ ...snack, severity: 'error', });
    },
    addSuccess: (snack: CustomSnack) => {
      addSnack({ ...snack, severity: 'success', });
    },
    addInfo: (snack: CustomSnack) => {
      addSnack({ ...snack, severity: 'info', });
    },
    addWarning: (snack: CustomSnack) => {
      addSnack({ ...snack, severity: 'warning', });
    },
    addSnack: (snack: CustomSnack) => {
      addSnack(snack);
    },
    closeAllSnacks: () => {
      closeSnackbar();
    }
  };
};
