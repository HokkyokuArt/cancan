import { Typography } from "@mui/material";
import { useAppSelector } from "../../redux/store";

const Inicio = () => {
    const token = useAppSelector(s => s.tokenState);
    return (
        <Typography>BEM VINDO DE VOLTA {token.nome.toUpperCase()}! 👋🎉</Typography>
    );
};

export default Inicio;
