import { FormControl, TextField, type TextFieldProps } from '@mui/material';

type Props = {
    onChange?: (e: string) => void;
    label: string;
    formControl?: boolean;
};

const CustomInput = (props: Omit<TextFieldProps, 'onChange'> & Props) => {
    const input = <TextField
        {...props}
        variant='standard'
        size="small"
        fullWidth
        onChange={e => {
            props.onChange?.(e.target.value);
        }}
        helperText={props.error ? props.helperText : null}
    />;

    return props.formControl
        ? <FormControl error={props.error} >{input}</FormControl>
        : input;

};

export default CustomInput;
