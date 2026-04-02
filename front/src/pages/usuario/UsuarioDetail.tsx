import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { IconButton, InputAdornment } from '@mui/material';
import { useEffect, useState } from 'react';
import { Role } from '../../auth/auth.model';
import CustomDialog from '../../components/CustomDialog';
import CustomInput from '../../components/CustomInput';
import EnumAutocomplete from '../../components/EnumAutocomplete';
import type { CrudDetailDialogProps } from '../../layout/BaseCrud';
import { useAppSelector } from '../../redux/store';
import type { UsuarioPayloadDTO } from './usuario.model';

const initialState: UsuarioPayloadDTO = {
  nome: '',
  email: '',
  senha: '',
  role: Role.ROLE_MEMBER
};

const UsuarioDetail = (props: CrudDetailDialogProps<"usuario">) => {
  const state = useAppSelector(s => s.usuarioState);
  const [localState, setLocalState] = useState(initialState);
  const [senhaVisivel, setSenhaVisivel] = useState(false);

  const isNew = !state.entidade;

  useEffect(() => {
    if (!props.open) {
      setTimeout(() => {
        setLocalState(initialState);
      }, 100);
    } else if (!isNew) {
      setLocalState(state.entidade!);
    }
  }, [props.open]);

  const handleChangeInput = (
    value: UsuarioPayloadDTO[keyof UsuarioPayloadDTO],
    key: string,
  ) => {
    setLocalState((prev) => {
      if (!prev) return prev;
      return { ...prev, [key]: value };
    });
  };

  return (<CustomDialog
    {...props}
    content={<>
      <CustomInput
        label="Nome"
        id="nome"
        value={localState?.nome}
        onChange={handleChangeInput}
        slotProps={{
          htmlInput: {
            maxLength: 100
          }
        }}
      />

      <CustomInput
        label="Email"
        id="email"
        type='email'
        value={localState?.email}
        onChange={handleChangeInput}
        slotProps={{
          htmlInput: {
            maxLength: 150
          }
        }}
      />

      <CustomInput
        label="Senha"
        id="senha"
        type={senhaVisivel ? "text" : 'password'}
        value={localState?.senha}
        onChange={handleChangeInput}
        slotProps={{
          htmlInput: {
            maxLength: 30
          },
          input: {
            endAdornment: <InputAdornment position="end">
              <IconButton
                onClick={() => setSenhaVisivel(prev => (!prev))}
              >
                {senhaVisivel ? <VisibilityOff /> : <Visibility />}
              </IconButton>
            </InputAdornment>
          }
        }}
      />

      <EnumAutocomplete
        id='role'
        label='Role'
        value={!state ? Role.ROLE_MEMBER : localState.role}
        enumName='Role'
        onSelect={val =>
          handleChangeInput(val ?? undefined, 'role')
        }
      />
    </>}
    onConfirm={() => props.onConfirm(localState)}
  />);
};

export default UsuarioDetail;
