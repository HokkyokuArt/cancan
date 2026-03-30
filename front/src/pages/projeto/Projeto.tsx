import { Button } from '@mui/material';
import { useState } from 'react';
import useSuperRequests from '../../common/services/useSuperRequests';
import type { AbstractEntityDTO } from '../../common/types/abstractEntity';
import { Pageable, Sort } from '../../common/types/pageable';
import { useAppSelector } from '../../redux/store';
import type { ProjetoFiltroDTO, ProjetoPayloadDTO } from './projeto.model';


const Projeto = () => {

    const { find, create, pageable } = useSuperRequests<ProjetoPayloadDTO, AbstractEntityDTO, ProjetoFiltroDTO>({ url: '/projeto' });
    const { id: dono } = useAppSelector(s => s.tokenState);

    const [abc, setAbc] = useState<ProjetoPayloadDTO | null>(null);

    const handleCreate = () => {
        const body: ProjetoPayloadDTO = {
            nome: 'Projeto teste abc',
            sigla: 'PRJ',
            descricao: 'Eu sla oq q ta acontencendo só quero dormir ahhhhhhhhhhhhhh',
            dono: dono!,
            membros: []
        };

        create(body);
    };

    const handleFind = () => {
        find('531655d8-1d05-49a9-8c46-489e67326da6');
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
