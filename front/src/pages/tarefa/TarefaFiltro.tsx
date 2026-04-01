import FilterListOffIcon from '@mui/icons-material/FilterListOff';
import { Box, Tooltip } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';
import { useEffect, useState } from 'react';
import CustomAutocomplete from '../../components/CustomAutocomplete';
import CustomButton from '../../components/CustomButton';
import CustomDialog from '../../components/CustomDialog';
import EnumAutocomplete from '../../components/EnumAutocomplete';
import type { CrudFiltroProps } from '../../layout/BaseCrud';
import { useAppSelector } from '../../redux/store';
import type { UsuarioFiltroDTO } from '../usuario/usuario.model';
import type { TarefaFiltroDTO } from './tarefa.model';

const initialState: TarefaFiltroDTO = {
  status: null,
  prioridade: null,
  responsavel: null,
  criacaoInicio: null,
  criacaoFim: null,
  prazoInicio: null,
  prazoFim: null,
};

const TarefaFiltro = (props: CrudFiltroProps<'tarefa'>) => {
  const state = useAppSelector(s => s.tarefaState);
  const [localState, setLocalState] = useState(initialState);
  const hasFiltro = !!state.filtro;

  useEffect(() => {
    if (!props.open) {
      setTimeout(() => {
        setLocalState(initialState);
      }, 100);
    } else if (hasFiltro) {
      setLocalState(state.filtro!);
    }
  }, [props.open]);

  const handleChangeInput = (
    value: TarefaFiltroDTO[keyof TarefaFiltroDTO],
    key: string,
  ) => {
    setLocalState((prev) => {
      if (!prev) return prev;
      return { ...prev, [key]: value };
    });
  };

  return (
    <CustomDialog
      {...props}
      title={"Filtro"}
      content={<>
        <EnumAutocomplete
          id='status'
          label='Status'
          value={localState.status ?? ''}
          enumName='StatusTarefa'
          onSelect={val =>
            handleChangeInput(!val ? null : val, 'status')
          }
        />

        <EnumAutocomplete
          id='prioridade'
          label='Prioridade'
          value={localState.prioridade ?? ''}
          enumName='PrioridadeTarefa'
          onSelect={val =>
            handleChangeInput(!val ? null : val, 'prioridade')
          }
        />

        <CustomAutocomplete<UsuarioFiltroDTO>
          id="responsavel"
          label="Responsável"
          isAsync
          path="/usuario"
          value={localState.responsavel ?? ''}
          onSelect={val => {
            handleChangeInput(!val ? null : val, 'responsavel');
          }}
        />

        <Box sx={{
          display: 'flex',
          gap: '10px'
        }}>
          <DatePicker
            sx={{ width: '100%' }}
            label="Criação início"
            value={localState.criacaoInicio ? dayjs(localState.criacaoInicio) : null}
            onChange={(newValue) => {
              handleChangeInput(newValue ? newValue.toDate() : null, 'criacaoInicio');
            }}
            format="DD/MM/YYYY"
            slotProps={{
              textField: {
                variant: 'standard',
              },
            }}
          />

          <DatePicker
            sx={{ width: '100%' }}
            label="Criação fim"
            value={localState.criacaoFim ? dayjs(localState.criacaoFim) : null}
            onChange={(newValue) => {
              handleChangeInput(newValue ? newValue.toDate() : null, 'criacaoFim');
            }}
            format="DD/MM/YYYY"
            slotProps={{
              textField: {
                variant: 'standard',
              },
            }}
          />
        </Box>

        <Box sx={{
          display: 'flex',
          gap: '10px'
        }}>
          <DatePicker
            sx={{ width: '100%' }}
            label="Prazo início"
            value={localState.prazoInicio ? dayjs(localState.prazoInicio) : null}
            onChange={(newValue) => {
              handleChangeInput(newValue ? newValue.toDate() : null, 'prazoInicio');
            }}
            format="DD/MM/YYYY"
            slotProps={{
              textField: {
                variant: 'standard',
              },
            }}
          />

          <DatePicker
            sx={{ width: '100%' }}
            label="Prazo fim"
            value={localState.prazoFim ? dayjs(localState.prazoFim) : null}
            onChange={(newValue) => {
              handleChangeInput(newValue ? newValue.toDate() : null, 'prazoFim');
            }}
            format="DD/MM/YYYY"
            slotProps={{
              textField: {
                variant: 'standard',
              },
            }}
          />
        </Box>
      </>}
      actions={(onClose, isMobile) => <Box sx={{
        display: 'flex',
        justifyContent: isMobile ? 'flex-end' : 'space-between',
        width: '100%',
        gap: '20px',
      }}>
        <CustomButton onClick={onClose} color='error'>Cancelar</CustomButton>

        <Tooltip title="Limpar filtros">
          <CustomButton
            color='primary'
            isIcon
            onClick={() => {
              setLocalState(initialState);
              props.onClear();
            }}
          >
            <FilterListOffIcon />
          </CustomButton>
        </Tooltip>

        <CustomButton type="submit">
          Confirmar
        </CustomButton>
      </Box>}
      onConfirm={() => {
        props.onConfirm(localState);
      }}
    />
  );
};

export default TarefaFiltro;
