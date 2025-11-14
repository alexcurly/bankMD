package com.bank.profile.mapper;

import com.bank.profile.DTO.RegistrationDTO;
import com.bank.profile.models.Registration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    Registration toEntity(RegistrationDTO registrationDTO);
    RegistrationDTO toDto(Registration registration);
    List<RegistrationDTO> toDoList(List<Registration> registrations);
}
