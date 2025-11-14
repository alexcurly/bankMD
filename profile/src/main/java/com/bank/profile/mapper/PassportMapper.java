package com.bank.profile.mapper;

import com.bank.profile.DTO.PassportDTO;
import com.bank.profile.models.Passport;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    Passport toEntity(PassportDTO passportDTO);
    PassportDTO toDto(Passport passport);
    List<PassportDTO> toDtoList (List<Passport> passports);
}
