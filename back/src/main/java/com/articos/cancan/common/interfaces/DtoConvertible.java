package com.articos.cancan.common.interfaces;

public interface DtoConvertible<PAYLOAD_DTO, RESPONSE_DTO, LIST_DTO> {
    RESPONSE_DTO toResponseDTO();

    PAYLOAD_DTO toPayloadDTO();

    LIST_DTO toListDTO();

}
