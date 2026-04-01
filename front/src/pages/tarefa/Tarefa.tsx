import { Button } from '@mui/material';
import useSuperRequests from '../../common/services/useSuperRequests';
import type { AbstractEntityDTO } from '../../common/types/abstractEntity';
import type { TarefaFiltroDTO, TarefaPayloadDTO, TarefaResponseDTO } from './tarefa.model';
import { Pageable, Sort } from '../../common/types/pageable';


const Tarefa = () => {
    // const { find, create, pageable, update } = useSuperRequests<TarefaResponseDTO, AbstractEntityDTO, TarefaFiltroDTO>({ url: '/tarefa' });

    // const handleCreate = () => {
    //     const body: TarefaPayloadDTO = {
    //         titulo: 'Tarefa com titulo',
    //         descricao: 'Descricao teste sla',
    //         status: 'TODO',
    //         prioridade: 'LOW',
    //         responsavel: 'bc25d22b-758f-45c6-95e1-36ab2a5429ff',
    //         projeto: 'a5f5d1ab-9c2b-4fd5-b24a-e0d1b440286e',
    //         prazo: new Date(),
    //     };

    //     create(body);
    // };

    // const handleUpdate = () => {
    //     const body: TarefaPayloadDTO = {
    //         id: "5cd021cf-e1d6-415b-817c-cec25c840eb3",
    //         version: 1,
    //         titulo: 'Tarefa com titulo',
    //         descricao: 'Descricao teste sla',
    //         status: 'TODO',
    //         prioridade: 'LOW',
    //         responsavel: 'bc25d22b-758f-45c6-95e1-36ab2a5429ff',
    //         projeto: 'a5f5d1ab-9c2b-4fd5-b24a-e0d1b440286e',
    //         prazo: new Date(),
    //     };

    //     update(body);
    // };

    // const handleFind = () => {
    //     find("5cd021cf-e1d6-415b-817c-cec25c840eb3");
    // };

    // const handleList = () => {
    //     pageable({}, Pageable.of({ page: 0, size: 10, sort: Sort.ofField('titulo') }));
    // };

    return (
        <>
            <h1>Tarefa</h1>
            {/* <Button onClick={handleCreate}>Create</Button>
            <Button onClick={handleUpdate}>Update</Button>
            <Button onClick={handleFind}>Find</Button>
            <Button onClick={handleList}>List</Button> */}
        </>
    );
};

export default Tarefa;
