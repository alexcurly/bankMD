package com.bank.profile.services.impl;

import com.bank.profile.DTO.ActualRegistrationDTO;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.models.Actual_registration;
import com.bank.profile.repositories.ActualRegistrationRepository;
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

class ActualRegistrationServiceImplTest {

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
    private ActualRegistrationRepository actualRegistrationRepository;

    @Mock
    private ActualRegistrationMapper actualRegistrationMapper;

    private ActualRegistrationDTO saveActualRegistrationDtoT;

    @InjectMocks
    private ActualRegistrationServiceImpl actualRegistrationService;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    void testGetActualRegistrationById() {

        Long id = 1L;
        Actual_registration actualRegistration = new Actual_registration();
        actualRegistration.setId(id);
        ActualRegistrationDTO expectedDto = new ActualRegistrationDTO();
        expectedDto.setId(id);

        when(actualRegistrationRepository.findById(anyLong())).thenReturn(Optional.of(actualRegistration));
        when(actualRegistrationMapper.toDto(any(Actual_registration.class))).thenReturn(expectedDto);

        ActualRegistrationDTO actualDto = actualRegistrationService.getActualRegistrationById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllActualRegistration() {

        List<Actual_registration> actualRegistrations = List.of(new Actual_registration(), new Actual_registration());
        List<ActualRegistrationDTO> expectedDtos = List.of(new ActualRegistrationDTO(), new ActualRegistrationDTO());

        when(actualRegistrationRepository.findAll()).thenReturn(actualRegistrations);
        when(actualRegistrationMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<ActualRegistrationDTO> actualDtos = actualRegistrationService.getAllActualRegistration();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveActualRegistration() {

        ActualRegistrationDTO actualRegistrationDto = new ActualRegistrationDTO();
        actualRegistrationDto.setId(ACCOUNT_ID);
        actualRegistrationDto.setCountry(COUNTRY);
        actualRegistrationDto.setRegion(REGION);
        actualRegistrationDto.setCity(CITY);
        actualRegistrationDto.setDistrict(DISTRICT);
        actualRegistrationDto.setLocality(LOCALITY);
        actualRegistrationDto.setStreet(STREET);
        actualRegistrationDto.setHouseNumber(HOUSE_NUMBER);
        actualRegistrationDto.setHouseBlock(HOUSE_BLOCK);
        actualRegistrationDto.setFlatNumber(FLAT_NUMBER);
        actualRegistrationDto.setIndex(INDEX);

        Actual_registration actualRegistration = new Actual_registration();
        actualRegistration.setId(ACCOUNT_ID);;
        actualRegistration.setCountry(COUNTRY);
        actualRegistration.setRegion(REGION);
        actualRegistration.setCity(CITY);
        actualRegistration.setDistrict(DISTRICT);
        actualRegistration.setLocality(LOCALITY);
        actualRegistration.setStreet(STREET);
        actualRegistration.setHouseNumber(HOUSE_NUMBER);
        actualRegistration.setHouseBlock(HOUSE_BLOCK);
        actualRegistration.setFlatNumber(FLAT_NUMBER);
        actualRegistration.setIndex(INDEX);

        when(actualRegistrationMapper.toEntity(any(ActualRegistrationDTO.class))).thenReturn(actualRegistration);
        when(actualRegistrationRepository.save(any(Actual_registration.class))).thenReturn(actualRegistration);
        when(actualRegistrationMapper.toDto(any(Actual_registration.class))).thenReturn(actualRegistrationDto);

        ActualRegistrationDTO savedActualRegistrationDto = actualRegistrationService.saveActualRegistration(actualRegistrationDto);

        assertEquals(actualRegistrationDto, savedActualRegistrationDto);
    }


    @Test
    void testUpdateActualRegistration() {

        ActualRegistrationDTO actualRegistrationDto = new ActualRegistrationDTO();
        actualRegistrationDto.setId(ACCOUNT_ID);
        actualRegistrationDto.setCountry(COUNTRY);
        actualRegistrationDto.setRegion(REGION);
        actualRegistrationDto.setCity(CITY);
        actualRegistrationDto.setDistrict(DISTRICT);
        actualRegistrationDto.setLocality(LOCALITY);
        actualRegistrationDto.setStreet(STREET);
        actualRegistrationDto.setHouseNumber(HOUSE_NUMBER);
        actualRegistrationDto.setHouseBlock(HOUSE_BLOCK);
        actualRegistrationDto.setFlatNumber(FLAT_NUMBER);
        actualRegistrationDto.setIndex(INDEX);

        Actual_registration actualRegistration = new Actual_registration();
        actualRegistration.setId(ACCOUNT_ID);;
        actualRegistration.setCountry(COUNTRY);
        actualRegistration.setRegion(REGION);
        actualRegistration.setCity(CITY);
        actualRegistration.setDistrict(DISTRICT);
        actualRegistration.setLocality(LOCALITY);
        actualRegistration.setStreet(STREET);
        actualRegistration.setHouseNumber(HOUSE_NUMBER);
        actualRegistration.setHouseBlock(HOUSE_BLOCK);
        actualRegistration.setFlatNumber(FLAT_NUMBER);
        actualRegistration.setIndex(INDEX);

        when(actualRegistrationRepository.findById(anyLong())).thenReturn(Optional.of(actualRegistration));
        when(actualRegistrationMapper.toEntity(any(ActualRegistrationDTO.class))).thenReturn(actualRegistration);
        when(actualRegistrationRepository.save(any(Actual_registration.class))).thenReturn(actualRegistration);
        when(actualRegistrationMapper.toDto(any(Actual_registration.class))).thenReturn(actualRegistrationDto);

        ActualRegistrationDTO updatedActualRegistrationDto = actualRegistrationService.updateActualRegistration(actualRegistrationDto);

        assertEquals(actualRegistrationDto, updatedActualRegistrationDto);
    }

    @Test
    void testDeleteActualRegistrationById() {
        Long id = 1L;

        when(actualRegistrationRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> actualRegistrationService.deleteActualRegistrationById(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Actual_registration entity = new Actual_registration();
        ActualRegistrationDTO dto = new ActualRegistrationDTO();

        when(actualRegistrationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(actualRegistrationMapper.toDto(entity)).thenReturn(dto);

        ActualRegistrationDTO result = actualRegistrationService.getActualRegistrationById(id);

        assertEquals(dto, result);
        verify(actualRegistrationRepository).findById(id);
        verify(actualRegistrationMapper).toDto(entity);
    }

    @Test
    void getAllActualRegistrationTest() {

        Actual_registration actualRegistration = new Actual_registration();
        ActualRegistrationDTO actualRegistrationDto = new ActualRegistrationDTO();
        List<Actual_registration> actualRegistrationEntities = List.of(actualRegistration);
        List<ActualRegistrationDTO> actualRegistrationDtos = List.of(actualRegistrationDto);

        when(actualRegistrationRepository.findAll()).thenReturn(actualRegistrationEntities);
        when(actualRegistrationMapper.toDtoList(actualRegistrationEntities)).thenReturn(actualRegistrationDtos);

        List<ActualRegistrationDTO> result = actualRegistrationService.getAllActualRegistration();

        assertEquals(actualRegistrationDtos, result);
        verify(actualRegistrationRepository).findAll();
        verify(actualRegistrationMapper).toDtoList(actualRegistrationEntities);
    }

}

