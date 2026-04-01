import { useEffect, useState } from "react";
import { Role } from "../../auth/auth.model";
import CustomAutocomplete from "../../components/CustomAutocomplete";
import CustomDialog from "../../components/CustomDialog";
import CustomInput from "../../components/CustomInput";
import type { CrudDetailDialogProps } from "../../layout/BaseCrud";
import { useAppSelector } from "../../redux/store";
import type { UsuarioFiltroDTO } from "../usuario/usuario.model";
import type { ProjetoPayloadDTO } from "./projeto.model";

const initialState: ProjetoPayloadDTO = {
    descricao: "",
    dono: "",
    membros: [],
    nome: "",
    sigla: "",
};

const ProjetoDetail = (props: CrudDetailDialogProps<"projeto">) => {
    const state = useAppSelector(s => s.projetoState);
    const [localState, setLocalState] = useState<ProjetoPayloadDTO>(initialState);

    useEffect(() => {
        if (!props.open) {
            setTimeout(() => {
                setLocalState(initialState);
            }, 100);
        } else if (state.entidade) {
            setLocalState(state.entidade);
        }
    }, [props.open]);

    const handleChangeInput = (
        value: ProjetoPayloadDTO[keyof ProjetoPayloadDTO],
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
            />
        </>}
        onConfirm={() => props.onConfirm(localState)}
    />);
};

export default ProjetoDetail;
