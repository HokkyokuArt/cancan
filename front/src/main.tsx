import { Slide, styled, type SlideProps } from '@mui/material';
import { MaterialDesignContent, SnackbarProvider } from 'notistack';
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import { RouterProvider } from 'react-router';
import './index.css';
import BlockUI from './layout/BlockUI.tsx';
import { store } from './redux/store.ts';
import './reset.css';
import { router } from './routes.tsx';

const StyledMaterialDesignContent = styled(MaterialDesignContent)(() => ({
  fontFamily: '"Poppins", "Roboto", sans-serif',
  letterSpacing: '1px'
}));

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
      <SnackbarProvider
        dense={false}
        maxSnack={7}
        anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        TransitionComponent={(props: SlideProps) => (
          <Slide
            {...props}
            direction='left'
          />
        )}
        Components={{
          default: StyledMaterialDesignContent,
          success: StyledMaterialDesignContent,
          error: StyledMaterialDesignContent,
          warning: StyledMaterialDesignContent,
          info: StyledMaterialDesignContent,
        }}>

        <BlockUI />
        <RouterProvider router={router} />
      </SnackbarProvider>
    </Provider>
  </StrictMode>,
);
