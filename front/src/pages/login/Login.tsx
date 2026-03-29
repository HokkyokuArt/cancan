import { Visibility } from "@mui/icons-material";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { Button, IconButton, InputAdornment } from "@mui/material";
import { useState } from "react";
import type { LoginPayload } from "../../auth/auth.model";
import useAuthService from "../../auth/useAuthService";
import BaseLayoutAuthForm from "../../components/BaseLayoutAuthForm";
import CustomInput from "../../components/CustomInput";

const Login = () => {
    const { logar } = useAuthService();
    const [localState, setLocalState] = useState({ showPassword: false });
    const [loginState, setLoginState] = useState<LoginPayload>({ email: 'eu@email', senha: '13241324' });

    const handleClickLogin = () => {
        logar(loginState);
    };

    return <BaseLayoutAuthForm
        title='Login'
        onSubmit={handleClickLogin}
    >
        <CustomInput label="Email" type="email" value={loginState.email} onChange={v => setLoginState(s => ({ ...s, email: v }))} />
        <CustomInput
            label="Senha"
            helperText={'teste'}
            type={localState.showPassword ? "text" : 'password'}
            value={loginState.senha}
            onChange={v => setLoginState(s => ({ ...s, senha: v }))}
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

        <Button type='submit'>Logar</Button>
        {/* <Button onClick={() => navigate(RouterURL.REGISTER)}>Ainda não tem uma conta?<Box paddingX={1}>Criar</Box></Button> */}
    </BaseLayoutAuthForm>;
};

export default Login;
