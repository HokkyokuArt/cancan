import { Button } from '@mui/material';
import { useState } from 'react';
import useSuperRequests from '../../common/services/useSuperRequests';
import { Pageable, Sort } from '../../common/types/pageable';
import { useAppSelector } from '../../redux/store';
import type { ProjetoPayloadDTO } from './projeto.model';
import type { AbstractEntityDTO } from '../../common/types/abstractEntity';


const Projeto = () => {

    const { find, create, pageable } = useSuperRequests<ProjetoPayloadDTO, AbstractEntityDTO>({ url: '/projeto' });
    const { id: dono } = useAppSelector(s => s.tokenState);

    const [abc, setAbc] = useState<ProjetoPayloadDTO | null>(null);

    const handleCreate = () => {
        const body: ProjetoPayloadDTO = {
            nome: 'Projeto teste abc',
            descricao: 'Eu sla oq q ta acontencendo só quero dormir ahhhhhhhhhhhhhh',
            dono: dono!,
            membros: []
        };

        create(body);
    };

    const handleFind = () => {
        find('aa52ae33-8b8c-425a-ab6d-1e9066297287');
    };

    const handleList = () => {
        pageable({ nome: 'abc' }, Pageable.of({ page: 0, size: 10, sort: Sort.ofField('nome') }));
    };

    return (
        <>
            <h1>Projeto</h1>
            <Button onClick={handleCreate}>Create</Button>
            <Button onClick={handleFind}>Find</Button>
            <Button onClick={handleList}>List</Button>
        </>
    );
};

export default Projeto;
