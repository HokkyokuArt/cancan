import {
  AxiosHeaders,
  type AxiosRequestConfig,
  type AxiosResponse,
} from "axios";
import { axiosRequest } from "../../axios.config";
import { useAppSelector } from "../../redux/store";

export type ThenCallBack<T> = (response: T) => void;

export type RequestBaseParams<T> = {
  url: string;
  params?: any;
  headers?: Record<string, unknown>;
  then: ThenCallBack<T>;
  catch?: (erro: any) => void;
  // catch?: (erro: any, defaultErrorBehaviorCb: ()=> void)) => void;
};

export type RequestBodyParams<T> = RequestBaseParams<T> & {
  body?: unknown;
};

export enum RequestType {
  GET = "get",
  POST = "post",
  PUT = "put",
  PATCH = "patch",
  DELETE = "delete",
}

const useRequestService = () => {
  const token = useAppSelector((s) => s.tokenState.token);

  const _makeRequest = <T>(
    params: RequestBodyParams<T>,
    requestType: RequestType,
  ): void => {
    _getRequestByType(params, requestType)
      .then((response) => params.then(response.data))
      .catch((err) => params.catch?.(err));
  };

  const _getRequestByType = <T>(
    params: RequestBodyParams<T>,
    requestType: RequestType,
  ): Promise<AxiosResponse<T>> => {
    const url = `${axiosRequest.defaults.baseURL}${params.url}`;

    switch (requestType) {
      case RequestType.GET:
        return axiosRequest.get(url, _getExtraInfo(params));
      default:
        return axiosRequest[requestType](
          url,
          params.body,
          _getExtraInfo(params),
        );
    }
  };

  const _getExtraInfo = <T>(
    params: RequestBodyParams<T>,
  ): AxiosRequestConfig => {
    const headers = new AxiosHeaders();
    if (!!token) {
      headers.setAuthorization(`Bearer ${token}`);
    }
    return {
      headers,
      params: params.params,
    };
  };

  return {
    getRequest: <T>(param: RequestBaseParams<T>): void => {
      _makeRequest(param, RequestType.GET);
    },

    postRequest: <T>(param: RequestBodyParams<T>): void => {
      _makeRequest(param, RequestType.POST);
    },

    putRequest: <T>(param: RequestBodyParams<T>): void => {
      _makeRequest(param, RequestType.PUT);
    },

    patchRequest: <T>(param: RequestBodyParams<T>): void => {
      _makeRequest(param, RequestType.PATCH);
    },

    deleteRequest: <T>(param: RequestBodyParams<T>): void => {
      _makeRequest(param, RequestType.DELETE);
    },
  };
};

export default useRequestService;
