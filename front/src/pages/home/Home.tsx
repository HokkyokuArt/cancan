import { Button, Typography } from "@mui/material";
import { useNavigate } from "react-router";
import { RouterURL } from "../../routes";

const Home = () => {

    const navigate = useNavigate();

    return (
        <>
            <Typography variant="h1">Home</Typography>
            <Typography variant="h5">Bem vindo!</Typography>
            <Button onClick={() => navigate(RouterURL.LOGIN)}>Login</Button>
        </>
    );
};

export default Home;
