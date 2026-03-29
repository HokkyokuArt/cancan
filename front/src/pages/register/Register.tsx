import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { Box, Button, IconButton, InputAdornment } from "@mui/material";
import { useState } from "react";
import type { RegisterPayload } from "../../auth/auth.model";
import useAuthService from "../../auth/useAuthService";
import useCustomNavigate from '../../common/hooks/useCustomNavigate';
import BaseLayoutAuthForm from '../../components/BaseLayoutAuthForm';
import CustomInput from "../../components/CustomInput";
import { RouterURL } from "../../routes";

const Register = () => {

    const { register } = useAuthService();
    const navigate = useCustomNavigate();

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
        <CustomInput label="Nome" onChange={v => setRegisterState(s => ({ ...s, nome: v }))} />
        <CustomInput label="Email" type="email" onChange={v => setRegisterState(s => ({ ...s, email: v }))} />
        <CustomInput
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
        <Button onClick={() => navigate(RouterURL.LOGIN)}>Já tem conta? <Box paddingX={1}>Entrar</Box></Button>
    </BaseLayoutAuthForm>;
};

export default Register;
