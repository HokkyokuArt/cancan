import type {
  AbstractEntityDTO,
  SuperPayloadResponseDTO,
} from "../types/abstractEntity";
import type { Filter, Page, Pageable } from "../types/pageable";
import type { UUID } from "../types/uuid";
import useRequestService from "./useRequestService";

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
    pageable(filter: FILTRO_DTO, pageable: Pageable) {
      postRequest<Page<LIST_RESPONSE_DTO>>({
        url: props.url + "/pageable",
        then: (res) => {
          console.log("pageable =>", { res });
        },
        body: filter,
        params: pageable.build(),
      });
    },

    find: (id: UUID) => {
      getRequest<RESPONSE_DTO>({
        url: `${props.url}/find/${id}`,
        then: (res) => {
          console.log("find =>", { res });
        },
      });
    },

    create: (body: unknown) => {
      postRequest<RESPONSE_DTO>({
        url: props.url + "/create",
        body,
        then: (res) => {
          console.log("create =>", { res });
        },
      });
    },

    update: (body: unknown) => {
      putRequest<RESPONSE_DTO>({
        url: props.url + "/update",
        body,
        then: (res) => {
          console.log("update =>", { res });
        },
      });
    },

    delete: (id: UUID) => {
      deleteRequest<RESPONSE_DTO>({
        url: `${props.url}/delete/${id}`,
        then: (res) => {
          console.log("delete =>", { res });
        },
      });
    },
  };
};

export default useSuperRequests;
