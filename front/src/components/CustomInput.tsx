import { FormControl, TextField, type TextFieldProps } from '@mui/material';

type Props = {
    onChange?: (e: string, id: string) => void;
    label: string;
    formControl?: boolean;
    id: string;
};

const CustomInput = (props: Omit<TextFieldProps, 'onChange'> & Props) => {
    const input = <TextField
        {...props}
        variant={props.multiline ? 'outlined' : 'standard'}
        size="small"
        fullWidth
        onChange={e => {
            props.onChange?.(e.target.value, props.id);
        }}
        helperText={props.error ? props.helperText : null}
    />;

    return props.formControl
        ? <FormControl error={props.error} >{input}</FormControl>
        : input;

};

export default CustomInput;
