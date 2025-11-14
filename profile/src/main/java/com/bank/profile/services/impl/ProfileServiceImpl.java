package com.bank.profile.services.impl;

import com.bank.profile.DTO.ProfileDTO;
import com.bank.profile.exception.EntityNotFoundException;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.models.Profile;
import com.bank.profile.repositories.ProfileRepository;
import com.bank.profile.services.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    private static final String AUDIT_NOT_FOUND_MESSAGE = "Profile wasn't found by id ";

    @Override
    public ProfileDTO getProfileById(Long id) {
        return profileMapper.toDto(profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id)));
    }

    @Override
    public List<ProfileDTO> getAllProfile() {
        return profileMapper.toDtoList(profileRepository.findAll());
    }

    @Override
    public ProfileDTO saveProfile(ProfileDTO profileDto) {
        Profile profile = profileMapper.toEntity(profileDto);
        log.info("Создание регистрации с данными: {}", profile);
        if (profileDto.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Country field is required");
        }
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDto) {

        if (profileDto.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Country field is required");
        }

        Profile profile = profileRepository.findById(profileDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Детали Audit with ID " + profileDto.getId() + " не найдено"));

        profileRepository.save(profileMapper.toEntity(profileDto));
        log.info("Сведения об учетной записи успешно обновлены с идентификатором = {}.", profileDto.getId());
        return profileMapper.toDto(profile);
    }

    @Override
    public void deleteProfileById(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id);
        }
        profileRepository.deleteById(id);
    }
}
