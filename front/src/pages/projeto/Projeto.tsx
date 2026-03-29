import { Button } from '@mui/material';
import { useState } from 'react';
import useRequestService from '../../common/services/useRequestService';
import type { ProjetoPayloadDTO } from './projeto.model';
import { useAppSelector } from '../../redux/store';


const Projeto = () => {

    const { post, get } = useRequestService();
    const { id: dono } = useAppSelector(s => s.tokenState);

    const [abc, setAbc] = useState<ProjetoPayloadDTO | null>(null);

    const create = () => {
        const body: ProjetoPayloadDTO = {
            nome: 'Projeto teste abc',
            descricao: 'Eu sla oq q ta acontencendo só quero dormir ahhhhhhhhhhhhhh',
            dono: dono!,
            membros: []
        };
        post<ProjetoPayloadDTO>({
            url: '/projeto/create',
            body,
            then: res => {
                console.log('create =>', { res });
                setAbc(res);
            }
        });
    };

    const find = () => {
        get<ProjetoPayloadDTO>({
            url: '/projeto/find/aa52ae33-8b8c-425a-ab6d-1e9066297287',
            then: res => {
                console.log('find =>', { res });
                setAbc(res);
            }
        });
    };

    return (
        <>
            <h1>Projeto</h1>
            <Button onClick={create}>Create</Button>
            <Button onClick={find}>Find</Button>
        </>
    );
};

export default Projeto;
