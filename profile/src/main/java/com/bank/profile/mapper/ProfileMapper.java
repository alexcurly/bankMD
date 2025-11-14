package com.bank.profile.mapper;

import com.bank.profile.DTO.ProfileDTO;
import com.bank.profile.models.Profile;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toEntity(ProfileDTO profileDTO);
    ProfileDTO toDto(Profile profile);
    List<ProfileDTO> toDtoList(List<Profile> profiles);
}
