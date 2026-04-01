import { useEffect, useState } from 'react';
import useAuthService from '../../auth/useAuthService';
import CustomAutocomplete from '../../components/CustomAutocomplete';
import CustomDialog from '../../components/CustomDialog';
import CustomInput from '../../components/CustomInput';
import EnumAutocomplete from '../../components/EnumAutocomplete';
import type { CrudDetailDialogProps } from '../../layout/BaseCrud';
import { useAppSelector } from '../../redux/store';
import type { ProjetoFiltroDTO } from '../projeto/projeto.model';
import type { UsuarioFiltroDTO } from '../usuario/usuario.model';
import type { TarefaPayloadDTO } from './tarefa.model';

const initialState: TarefaPayloadDTO = {
    titulo: "",
    responsavel: "",
    projeto: "",
    descricao: "",
    status: "TODO",
    prioridade: "",
    prazo: new Date(),
};

const TarefaDetail = (props: CrudDetailDialogProps<"tarefa">) => {

    const state = useAppSelector(s => s.tarefaState);
    const { isAdmin } = useAuthService();
    const [localState, setLocalState] = useState(initialState);

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
        value: TarefaPayloadDTO[keyof TarefaPayloadDTO],
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
                label="Título"
                id="titulo"
                value={localState?.titulo}
                onChange={handleChangeInput}
                slotProps={{
                    htmlInput: {
                        maxLength: 30
                    }
                }}
            />

            <CustomAutocomplete<UsuarioFiltroDTO>
                id="responsavel"
                label="Responsável"
                isAsync
                path="/usuario"
                value={localState.responsavel}
                onSelect={val => {
                    handleChangeInput(val ?? undefined, 'responsavel');
                }}
            />

            <CustomAutocomplete<ProjetoFiltroDTO>
                id="projeto"
                label="Projeto"
                isAsync
                path="/projeto"
                value={localState.projeto}
                onSelect={val => {
                    handleChangeInput(val ?? undefined, 'projeto');
                }}
            />

            <EnumAutocomplete
                id='status'
                label='Status'
                value={!state ? 'TODO' : localState.status}
                enumName='StatusTarefa'
                disabled={isNew}
                onSelect={val =>
                    handleChangeInput(val ?? undefined, 'status')
                }
            />

            <EnumAutocomplete
                id='prioridade'
                label='Prioridade'
                value={!state ? 'TODO' : localState.prioridade}
                enumName='PrioridadeTarefa'
                disabled={!isNew && state.entidade?.prioridade === 'CRITICAL' && !isAdmin()}
                onSelect={val =>
                    handleChangeInput(val ?? undefined, 'prioridade')
                }
            />

            <CustomInput
                label="Descrição"
                id="descricao"
                multiline
                rows={10}
                maxRows={10}
                value={localState?.descricao}
                onChange={handleChangeInput}
                slotProps={{
                    htmlInput: {
                        maxLength: 1000
                    }
                }}
            />
            {/* <CustomInput
                label="Nome"
                id="nome"
                value={localState?.nome}
                onChange={handleChangeInput}
                slotProps={{
                    htmlInput: {
                        maxLength: 30
                    }
                }}
            />

            <CustomInput
                id="sigla"
                label="Sigla"
                value={localState?.sigla}
                onChange={handleChangeInput}
                slotProps={{
                    htmlInput: {
                        maxLength: 3
                    }
                }}
            />

            <CustomInput
                id="descricao"
                label="Descrição"
                value={localState?.descricao}
                onChange={handleChangeInput}
                slotProps={{
                    htmlInput: {
                        maxLength: 100
                    }
                }}
            />

            <CustomAutocomplete<UsuarioFiltroDTO>
                id="dono"
                label="Dono"
                isAsync
                path="/usuario"
                value={localState.dono}
                onSelect={val => {
                    handleChangeInput(val ?? undefined, 'dono');
                }}
                extraCriterias={{
                    roles: [Role.ROLE_ADMIN]
                }}
            />

            <CustomAutocomplete
                id="membros"
                label="Membros"
                isAsync
                path="/usuario"
                multiple
                value={localState.membros}
                onSelect={val => {
                    handleChangeInput(val, 'membros');
                }}
            /> */}
        </>}
        onConfirm={() => props.onConfirm(localState)}
    />);
};

export default TarefaDetail;
