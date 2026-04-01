package com.articos.cancan.common.interfaces;

public interface DtoConvertible<PAYLOAD_DTO, RESPONSE_DTO, LIST_DTO> {
    void setValues(PAYLOAD_DTO dto);

    RESPONSE_DTO toResponseDTO();

    PAYLOAD_DTO toPayloadDTO();

    LIST_DTO toListDTO();

}
