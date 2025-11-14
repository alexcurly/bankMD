package com.bank.history.mapper;

import com.bank.history.DTO.HistoryDTO;
import com.bank.history.models.History;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface HistoryMapper {
    History toEntity(HistoryDTO profileDTO);
    HistoryDTO toDto(History profile);
    List<HistoryDTO> toDtoList(List<History> profiles);

}

