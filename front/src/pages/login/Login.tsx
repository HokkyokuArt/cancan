import { Button, Typography } from "@mui/material";
import { useNavigate } from "react-router";

const Login = () => {

    const navigate = useNavigate();
    return (
        <>
            <Typography variant="h1">Login</Typography>
            <Button onClick={() => { navigate('/home'); }}>Logar</Button>
        </>
    );
};

export default Login;
