import { Button, Typography } from "@mui/material";
import useCustomNavigate from "../../common/hooks/useCustomNavigate";
import { RouterURL } from "../../routes";

const Home = () => {
    const navigate = useCustomNavigate();
    return (
        <>
            <Typography variant="h1">Home</Typography>
            <Typography variant="h5">Bem vindo!</Typography>
            <Button onClick={() => navigate(RouterURL.LOGIN)}>Login</Button>
        </>
    );
};

export default Home;
