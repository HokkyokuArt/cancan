import { Button } from '@mui/material';
import useSuperRequests from '../../common/services/useSuperRequests';
import type { AbstractEntityDTO } from '../../common/types/abstractEntity';
import type { TarefaFiltroDTO, TarefaPayloadDTO, TarefaResponseDTO } from './tarefa.model';
import { Pageable, Sort } from '../../common/types/pageable';


const Tarefa = () => {
    const { find, create, pageable } = useSuperRequests<TarefaResponseDTO, AbstractEntityDTO, TarefaFiltroDTO>({ url: '/tarefa' });

    const handleCreate = () => {
        const body: TarefaPayloadDTO = {
            titulo: 'Tarefa com titulo',
            descricao: 'Descricao teste sla',
            status: 'TODO',
            prioridade: 'LOW',
            responsavel: 'c8382357-8434-4e5c-a27c-53033900c632',
            projeto: 'a9e1441c-a144-4c4a-8a57-f384dcd10471',
            prazo: new Date(),
        };

        create(body);
    };

    const handleFind = () => {
        find('10735cc8-b8ab-4e89-a706-54785f247e67');
    };

    const handleList = () => {
        pageable({}, Pageable.of({ page: 0, size: 10, sort: Sort.ofField('titulo') }));
    };

    return (
        <>
            <h1>Tarefa</h1>
            <Button onClick={handleCreate}>Create</Button>
            <Button onClick={handleFind}>Find</Button>
            <Button onClick={handleList}>List</Button>
        </>
    );
};

export default Tarefa;
