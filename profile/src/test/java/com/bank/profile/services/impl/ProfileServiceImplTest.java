package com.bank.profile.services.impl;

import com.bank.profile.DTO.ProfileDTO;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.models.Profile;
import com.bank.profile.repositories.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfileServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final Long PHONE_NUMBER = 89819519604L;
    private static final String EMAIL = "alex@gmail.com";
    private static final String NAME_ON_CARD = "Alex";
    private static final int INN = 111;
    private static final int SNILS = 222;


    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    private ProfileDTO saveProfileDtoT;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    void testGetProfileById() {

        Long id = 1L;
        Profile profile = new Profile();
        profile.setId(id);
        ProfileDTO expectedDto = new ProfileDTO();
        expectedDto.setId(id);

        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile));
        when(profileMapper.toDto(any(Profile.class))).thenReturn(expectedDto);

        ProfileDTO actualDto = profileService.getProfileById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllProfile() {

        List<Profile> profiles = List.of(new Profile(), new Profile());
        List<ProfileDTO> expectedDtos = List.of(new ProfileDTO(), new ProfileDTO());

        when(profileRepository.findAll()).thenReturn(profiles);
        when(profileMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<ProfileDTO> actualDtos = profileService.getAllProfile();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveProfile() {

        ProfileDTO profileDto = new ProfileDTO();
        profileDto.setId(ACCOUNT_ID);
        profileDto.setPhoneNumber(PHONE_NUMBER);
        profileDto.setEmail(EMAIL);
        profileDto.setNameOnCard(NAME_ON_CARD);
        profileDto.setInn(INN);
        profileDto.setSnils(SNILS);

        Profile profile = new Profile();
        profile.setId(ACCOUNT_ID);
        profile.setPhoneNumber(PHONE_NUMBER);
        profile.setEmail(EMAIL);
        profile.setNameOnCard(NAME_ON_CARD);
        profile.setInn(INN);
        profile.setSnils(SNILS);

        when(profileMapper.toEntity(any(ProfileDTO.class))).thenReturn(profile);
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(profileMapper.toDto(any(Profile.class))).thenReturn(profileDto);

        ProfileDTO savedProfileDto = profileService.saveProfile(profileDto);

        assertEquals(profileDto, savedProfileDto);
    }

    @Test
    void testUpdateProfile() {

        ProfileDTO profileDto = new ProfileDTO();
        profileDto.setId(ACCOUNT_ID);
        profileDto.setPhoneNumber(PHONE_NUMBER);
        profileDto.setEmail(EMAIL);
        profileDto.setNameOnCard(NAME_ON_CARD);
        profileDto.setInn(INN);
        profileDto.setSnils(SNILS);

        Profile profile = new Profile();
        profile.setId(ACCOUNT_ID);
        profile.setPhoneNumber(PHONE_NUMBER);
        profile.setEmail(EMAIL);
        profile.setNameOnCard(NAME_ON_CARD);
        profile.setInn(INN);
        profile.setSnils(SNILS);

        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile));
        when(profileMapper.toEntity(any(ProfileDTO.class))).thenReturn(profile);
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(profileMapper.toDto(any(Profile.class))).thenReturn(profileDto);

        ProfileDTO updatedProfileDto = profileService.updateProfile(profileDto);

        assertEquals(profileDto, updatedProfileDto);
    }

    @Test
    void testDeleteProfileById() {
        Long id = 1L;

        when(profileRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> profileService.deleteProfileById(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Profile entity = new Profile();
        ProfileDTO dto = new ProfileDTO();

        when(profileRepository.findById(id)).thenReturn(Optional.of(entity));
        when(profileMapper.toDto(entity)).thenReturn(dto);

        ProfileDTO result = profileService.getProfileById(id);

        assertEquals(dto, result);
        verify(profileRepository).findById(id);
        verify(profileMapper).toDto(entity);
    }

    @Test
    void getAllProfileTest() {

        Profile profile = new Profile();
        ProfileDTO profileDto = new ProfileDTO();
        List<Profile> profileEntities = List.of(profile);
        List<ProfileDTO> profileDtos = List.of(profileDto);

        when(profileRepository.findAll()).thenReturn(profileEntities);
        when(profileMapper.toDtoList(profileEntities)).thenReturn(profileDtos);

        List<ProfileDTO> result = profileService.getAllProfile();

        assertEquals(profileDtos, result);
        verify(profileRepository).findAll();
        verify(profileMapper).toDtoList(profileEntities);
    }

}