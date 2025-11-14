package com.bank.profile.services;

import com.bank.profile.DTO.ProfileDTO;
import java.util.List;

public interface ProfileService {
    ProfileDTO saveProfile(ProfileDTO profileDTO);
    ProfileDTO updateProfile(ProfileDTO profileDto);
    void deleteProfileById(Long id);
    ProfileDTO getProfileById(Long id);
    List<ProfileDTO> getAllProfile();
}
