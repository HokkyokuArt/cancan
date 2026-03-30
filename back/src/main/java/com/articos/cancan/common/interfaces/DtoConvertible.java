package com.articos.cancan.common.interfaces;

public interface DtoConvertible<PAYLOAD_DTO, RESPONSE_DTO, LIST_DTO> {
    void setValues(PAYLOAD_DTO dto);

    RESPONSE_DTO toDTO();

    LIST_DTO toListDTO();

}
