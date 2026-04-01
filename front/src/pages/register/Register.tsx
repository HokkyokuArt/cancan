import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { Button, IconButton, InputAdornment } from "@mui/material";
import { useState } from "react";
import type { RegisterPayload } from "../../auth/auth.model";
import useAuthService from "../../auth/useAuthService";
import CustomInput from "../../components/CustomInput";
import BaseLayoutAuthForm from '../../layout/BaseLayoutAuthForm';

const Register = () => {

    const { register } = useAuthService();

    const [localState, setLocalState] = useState({ showPassword: false });

    const [registerState, setRegisterState] = useState<RegisterPayload>(
        {
            nome: '',
            email: '',
            senha: '',
        }
    );

    const handleClickRegister = () => {
        register(registerState);
    };

    return <BaseLayoutAuthForm
        title='Registrar'
        onSubmit={handleClickRegister}
    >
        <CustomInput id='nome' label="Nome" onChange={v => setRegisterState(s => ({ ...s, nome: v }))} />
        <CustomInput id='email' label="Email" type="email" onChange={v => setRegisterState(s => ({ ...s, email: v }))} />
        <CustomInput
            id='senha'
            label="Senha"
            helperText={'teste'}
            type={localState.showPassword ? "text" : 'password'}
            onChange={v => setRegisterState(s => ({ ...s, senha: v }))}
            slotProps={{
                input: {
                    endAdornment: <InputAdornment position="end">
                        <IconButton
                            onClick={() => setLocalState(prev => ({ ...prev, showPassword: !prev.showPassword }))}
                        >
                            {localState.showPassword ? <VisibilityOff /> : <Visibility />}
                        </IconButton>
                    </InputAdornment>
                }
            }}
        />

        <Button type='submit'>Registrar</Button>
    </BaseLayoutAuthForm>;
};

export default Register;
