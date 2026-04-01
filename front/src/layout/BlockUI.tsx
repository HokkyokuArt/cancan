import { Backdrop, Box, CircularProgress, Typography } from "@mui/material";
import { useAppSelector } from "../redux/store";

const BlockUI = () => {
    const { loading, message } = useAppSelector(s => s.blockUIState);
    return (
        <Backdrop
            sx={{
                zIndex: (theme) => theme.zIndex.drawer + 1,
                background: 'rgba(0,0,0,.8)'
            }}
            open={loading}
        >
            <Box style={{
                flexDirection: 'column',
                display: 'flex'
            }}>
                <CircularProgress sx={{ alignSelf: 'center' }} />
                {!!message &&
                    <Typography
                        className="blockUI"
                        color="primary"
                        sx={{
                            textAlign: 'center',
                            paddingTop: '20px',
                            fontSize: '20px'
                        }}>
                        {message}
                    </Typography>
                }
            </Box>
        </Backdrop>
    );
};

export default BlockUI;
