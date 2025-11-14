package com.bank.profile.services.impl;

import com.bank.profile.DTO.RegistrationDTO;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.models.Registration;
import com.bank.profile.repositories.RegistrationRepository;
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

class RegistrationServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final String COUNTRY = "Russia";
    private static final String REGION = "Russia";
    private static final String CITY = "Russia";
    private static final String DISTRICT = "Russia";
    private static final String LOCALITY = "Russia";
    private static final String STREET = "Russia";
    private static final String HOUSE_NUMBER = "Russia";
    private static final String HOUSE_BLOCK = "Russia";
    private static final String FLAT_NUMBER = "Russia";
    private static final int INDEX = 1;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private RegistrationMapper registrationMapper;

    private RegistrationDTO saveRegistrationDtoT;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    void testGetRegistrationById() {

        Long id = 1L;
        Registration registration = new Registration();
        registration.setId(id);
        RegistrationDTO expectedDto = new RegistrationDTO();
        expectedDto.setId(id);

        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));
        when(registrationMapper.toDto(any(Registration.class))).thenReturn(expectedDto);

        RegistrationDTO actualDto = registrationService.getRegistrationById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllRegistration() {

        List<Registration> registrations = List.of(new Registration(), new Registration());
        List<RegistrationDTO> expectedDtos = List.of(new RegistrationDTO(), new RegistrationDTO());

        when(registrationRepository.findAll()).thenReturn(registrations);
        when(registrationMapper.toDoList(anyList())).thenReturn(expectedDtos);

        List<RegistrationDTO> actualDtos = registrationService.getAllRegistration();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveRegistration() {

        RegistrationDTO registrationDto = new RegistrationDTO();
        registrationDto.setId(ACCOUNT_ID);
        registrationDto.setCountry(COUNTRY);
        registrationDto.setRegion(REGION);
        registrationDto.setCity(CITY);
        registrationDto.setDistrict(DISTRICT);
        registrationDto.setLocality(LOCALITY);
        registrationDto.setStreet(STREET);
        registrationDto.setHouseNumber(HOUSE_NUMBER);
        registrationDto.setHouseBlock(HOUSE_BLOCK);
        registrationDto.setFlatNumber(FLAT_NUMBER);
        registrationDto.setIndex(INDEX);

        Registration registration = new Registration();
        registration.setId(ACCOUNT_ID);
        registration.setCountry(COUNTRY);
        registration.setRegion(REGION);
        registration.setCity(CITY);
        registration.setDistrict(DISTRICT);
        registration.setLocality(LOCALITY);
        registration.setStreet(STREET);
        registration.setHouseNumber(HOUSE_NUMBER);
        registration.setHouseBlock(HOUSE_BLOCK);
        registration.setFlatNumber(FLAT_NUMBER);
        registration.setIndex(INDEX);

        when(registrationMapper.toEntity(any(RegistrationDTO.class))).thenReturn(registration);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        when(registrationMapper.toDto(any(Registration.class))).thenReturn(registrationDto);

        RegistrationDTO savedRegistrationDto = registrationService.saveRegistration(registrationDto);

        assertEquals(registrationDto, savedRegistrationDto);
    }


    @Test
    void testUpdateRegistration() {

        RegistrationDTO registrationDto = new RegistrationDTO();
        registrationDto.setId(ACCOUNT_ID);
        registrationDto.setCountry(COUNTRY);
        registrationDto.setRegion(REGION);
        registrationDto.setCity(CITY);
        registrationDto.setDistrict(DISTRICT);
        registrationDto.setLocality(LOCALITY);
        registrationDto.setStreet(STREET);
        registrationDto.setHouseNumber(HOUSE_NUMBER);
        registrationDto.setHouseBlock(HOUSE_BLOCK);
        registrationDto.setFlatNumber(FLAT_NUMBER);
        registrationDto.setIndex(INDEX);

        Registration registration = new Registration();
        registration.setId(ACCOUNT_ID);
        registration.setCountry(COUNTRY);
        registration.setRegion(REGION);
        registration.setCity(CITY);
        registration.setDistrict(DISTRICT);
        registration.setLocality(LOCALITY);
        registration.setStreet(STREET);
        registration.setHouseNumber(HOUSE_NUMBER);
        registration.setHouseBlock(HOUSE_BLOCK);
        registration.setFlatNumber(FLAT_NUMBER);
        registration.setIndex(INDEX);

        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));
        when(registrationMapper.toEntity(any(RegistrationDTO.class))).thenReturn(registration);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        when(registrationMapper.toDto(any(Registration.class))).thenReturn(registrationDto);

        RegistrationDTO updatedRegistrationDto = registrationService.updateRegistration(registrationDto);

        assertEquals(registrationDto, updatedRegistrationDto);
    }

    @Test
    void testDeleteRegistrationById() {
        Long id = 1L;

        when(registrationRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> registrationService.deleteRegistrationById(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Registration entity = new Registration();
        RegistrationDTO dto = new RegistrationDTO();

        when(registrationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(registrationMapper.toDto(entity)).thenReturn(dto);

        RegistrationDTO result = registrationService.getRegistrationById(id);

        assertEquals(dto, result);
        verify(registrationRepository).findById(id);
        verify(registrationMapper).toDto(entity);
    }

    @Test
    void getAllRegistrationTest() {

        Registration registration = new Registration();
        RegistrationDTO registrationDto = new RegistrationDTO();
        List<Registration> registrationEntities = List.of(registration);
        List<RegistrationDTO> registrationDtos = List.of(registrationDto);

        when(registrationRepository.findAll()).thenReturn(registrationEntities);
        when(registrationMapper.toDoList(registrationEntities)).thenReturn(registrationDtos);

        List<RegistrationDTO> result = registrationService.getAllRegistration();

        assertEquals(registrationDtos, result);
        verify(registrationRepository).findAll();
        verify(registrationMapper).toDoList(registrationEntities);
    }

}