import type { AbstractEntityDTO } from "../types/abstractEntity";
import type {
  CrudDtoTypeMapKey,
  GenericFiltroDTO,
  GenericListResponseDTO,
  GenericPayloadDTO,
  GenericResponseDTO,
} from "../types/crudState";
import type { Filter, Page, Pageable } from "../types/pageable";
import type { UUID } from "../types/uuid";
import useRequestService, { type ThenCallBack } from "./useRequestService";

type Props = {
  url: string;
};

const useSuperRequests = <K extends CrudDtoTypeMapKey>(props: Props) => {
  const { getRequest, postRequest, putRequest, deleteRequest } =
    useRequestService();
  return {
    pageable(
      filter: GenericFiltroDTO<K>,
      pageable: Pageable,
      then: ThenCallBack<Page<GenericListResponseDTO<K>>>,
    ) {
      postRequest<Page<GenericListResponseDTO<K>>>({
        url: props.url + "/pageable",
        then,
        body: filter,
        params: pageable.build(),
      });
    },

    autocomplete(
      filter: Filter,
      pageable: Pageable,
      then: ThenCallBack<Page<AbstractEntityDTO>>,
    ) {
      postRequest<Page<AbstractEntityDTO>>({
        url: props.url + "/autocomplete",
        then,
        body: filter,
        params: pageable.build(),
      });
    },

    find: (id: UUID, then: ThenCallBack<GenericResponseDTO<K>>) => {
      getRequest<GenericResponseDTO<K>>({
        url: `${props.url}/find/${id}`,
        then,
      });
    },

    findToEdit: (id: UUID, then: ThenCallBack<GenericPayloadDTO<K>>) => {
      getRequest<GenericPayloadDTO<K>>({
        url: `${props.url}/find-to-edit/${id}`,
        then,
      });
    },

    findAbstractEntity: (
      ids: UUID[],
      then: ThenCallBack<AbstractEntityDTO[]>,
    ) => {
      if (!ids.length) {
        then([]);
        return;
      }
      getRequest<AbstractEntityDTO[]>({
        url: `${props.url}/find-abstract-entity`,
        then,
        params: {
          ids,
        },
      });
    },

    create: (
      body: GenericPayloadDTO<K>,
      then: ThenCallBack<GenericResponseDTO<K>>,
    ) => {
      postRequest<GenericResponseDTO<K>>({
        url: props.url + "/create",
        body,
        then,
      });
    },

    update: (
      body: GenericPayloadDTO<K>,
      then: ThenCallBack<GenericResponseDTO<K>>,
    ) => {
      putRequest<GenericResponseDTO<K>>({
        url: props.url + "/update",
        body,
        then,
      });
    },

    delete: (id: UUID, then: ThenCallBack<GenericResponseDTO<K>>) => {
      deleteRequest<GenericResponseDTO<K>>({
        url: `${props.url}/delete/${id}`,
        then,
      });
    },
  };
};

export default useSuperRequests;
