package com.bank.profile.mapper;

import com.bank.profile.DTO.ActualRegistrationDTO;
import com.bank.profile.models.Actual_registration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ActualRegistrationMapper {
    Actual_registration toEntity(ActualRegistrationDTO actualRegistrationDTO);
    ActualRegistrationDTO toDto(Actual_registration actual_registration);
    List<ActualRegistrationDTO> toDtoList(List<Actual_registration> actualRegistrations);
}
