import type {
  AbstractEntityDTO,
  SuperPayloadResponseDTO,
} from "../types/abstractEntity";
import type { Filter, Page, Pageable } from "../types/pageable";
import type { UUID } from "../types/uuid";
import useRequestService, { type ThenCallBack } from "./useRequestService";

type Props = {
  url: string;
};

const useSuperRequests = <
  RESPONSE_DTO extends SuperPayloadResponseDTO,
  LIST_RESPONSE_DTO extends AbstractEntityDTO,
  FILTRO_DTO extends Filter,
>(
  props: Props,
) => {
  const { getRequest, postRequest, putRequest, deleteRequest } =
    useRequestService();
  return {
    pageable(
      filter: FILTRO_DTO,
      pageable: Pageable,
      then: ThenCallBack<Page<LIST_RESPONSE_DTO>>,
    ) {
      postRequest<Page<LIST_RESPONSE_DTO>>({
        url: props.url + "/pageable",
        then,
        body: filter,
        params: pageable.build(),
      });
    },

    find: (id: UUID, then: ThenCallBack<RESPONSE_DTO>) => {
      getRequest<RESPONSE_DTO>({
        url: `${props.url}/find/${id}`,
        then,
      });
    },

    create: (body: unknown, then: ThenCallBack<RESPONSE_DTO>) => {
      postRequest<RESPONSE_DTO>({
        url: props.url + "/create",
        body,
        then,
      });
    },

    update: (body: unknown, then: ThenCallBack<RESPONSE_DTO>) => {
      putRequest<RESPONSE_DTO>({
        url: props.url + "/update",
        body,
        then,
      });
    },

    delete: (id: UUID, then: ThenCallBack<RESPONSE_DTO>) => {
      deleteRequest<RESPONSE_DTO>({
        url: `${props.url}/delete/${id}`,
        then,
      });
    },
  };
};

export default useSuperRequests;
