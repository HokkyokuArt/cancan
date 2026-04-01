import {
  AxiosError,
  AxiosHeaders,
  type AxiosRequestConfig,
  type AxiosResponse,
} from "axios";
import { axiosRequest } from "../../axios.config";
import { useAppSelector } from "../../redux/store";
import useLoading from "../hooks/useLoading";
import { useSnack } from "../hooks/useSnack";
import { ProblemaCode } from "../types/problemaCode";

export type ThenCallBack<T> = (response: T) => void;

export type RequestBaseParams<T, ExtraError> = {
  url: string;
  params?: any;
  headers?: Record<string, unknown>;
  then: ThenCallBack<T>;
  catch?: (erro: ProblemaDetalhe<ExtraError>) => void;
  // catch?: (erro: any, defaultErrorBehaviorCb: ()=> void)) => void;
};

export type RequestBodyParams<T, ExtraError> = RequestBaseParams<
  T,
  ExtraError
> & {
  body?: unknown;
};

export enum RequestType {
  GET = "get",
  POST = "post",
  PUT = "put",
  PATCH = "patch",
  DELETE = "delete",
}

export type ProblemaDetalhe<ExtraError> = {
  timestamp: Date;
  status: number;
  code: ProblemaCode;
  error: string;
  message: string;
  path: string;
  extra?: ExtraError;
};

const useRequestService = () => {
  const token = useAppSelector((s) => s.tokenState.token);
  const { enableLoader, disabledLoader } = useLoading();
  const { addError } = useSnack();

  const _makeRequest = <T, ExtraError>(
    params: RequestBodyParams<T, ExtraError>,
    requestType: RequestType,
  ): void => {
    enableLoader();
    _getRequestByType(params, requestType)
      .then((response) => {
        params.then(response.data);
      })
      .catch(({ response }: AxiosError<ProblemaDetalhe<ExtraError>>) => {
        const data = response!.data;
        addError({ id: "error", message: data.message });
        params.catch?.(data);
      })
      .finally(() => disabledLoader());
  };

  const _getRequestByType = <T, ExtraError>(
    params: RequestBodyParams<T, ExtraError>,
    requestType: RequestType,
  ): Promise<AxiosResponse<T>> => {
    const url = `${axiosRequest.defaults.baseURL}${params.url}`;

    switch (requestType) {
      case RequestType.GET:
      case RequestType.DELETE:
        return axiosRequest[requestType](url, _getExtraInfo(params));
      default:
        return axiosRequest[requestType](
          url,
          params.body,
          _getExtraInfo(params),
        );
    }
  };

  const _getExtraInfo = <T, ExtraError>(
    params: RequestBodyParams<T, ExtraError>,
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
    getRequest: <T, ExtraError = unknown>(
      param: RequestBaseParams<T, ExtraError>,
    ): void => {
      _makeRequest<T, ExtraError>(param, RequestType.GET);
    },

    postRequest: <T, ExtraError = any>(
      param: RequestBodyParams<T, ExtraError>,
    ): void => {
      _makeRequest<T, ExtraError>(param, RequestType.POST);
    },

    putRequest: <T, ExtraError = any>(
      param: RequestBodyParams<T, ExtraError>,
    ): void => {
      _makeRequest<T, ExtraError>(param, RequestType.PUT);
    },

    patchRequest: <T, ExtraError = any>(
      param: RequestBodyParams<T, ExtraError>,
    ): void => {
      _makeRequest<T, ExtraError>(param, RequestType.PATCH);
    },

    deleteRequest: <T, ExtraError = any>(
      param: RequestBaseParams<T, ExtraError>,
    ): void => {
      _makeRequest<T, ExtraError>(param, RequestType.DELETE);
    },
  };
};

export default useRequestService;
