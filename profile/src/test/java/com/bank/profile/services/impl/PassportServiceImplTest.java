package com.bank.profile.services.impl;

import com.bank.profile.DTO.PassportDTO;
import com.bank.profile.exception.EntityNotFoundException;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.models.Passport;
import com.bank.profile.repositories.PassportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PassportServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final int SERIES = 1;
    private static final Long NUMBER = 2L;
    private static final String LAST_NAME = "Russia";
    private static final String FIRST_NAME = "Russia";
    private static final String MIDDLE_NAME = "Russia";
    private static final String GENDER = "M";
    private static final String BIRTH_PLACE = "Russia";
    private static final String ISSUED_BY = "Russia";
    private static final int DIVISION_CODE = 3;

    @Mock
    private PassportRepository passportRepository;

    @Mock
    private PassportMapper passportMapper;

    private PassportDTO savePassportDtoT;

    @InjectMocks
    private PassportServiceImpl passportService;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    void testGetPassportById() {

        Long id = 1L;
        Passport passport = new Passport();
        passport.setId(id);
        PassportDTO expectedDto = new PassportDTO();
        expectedDto.setId(id);

        when(passportRepository.findById(anyLong())).thenReturn(Optional.of(passport));
        when(passportMapper.toDto(any(Passport.class))).thenReturn(expectedDto);

        PassportDTO actualDto = passportService.getPassportById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllPassport() {

        List<Passport> passports = List.of(new Passport(), new Passport());
        List<PassportDTO> expectedDtos = List.of(new PassportDTO(), new PassportDTO());

        when(passportRepository.findAll()).thenReturn(passports);
        when(passportMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<PassportDTO> actualDtos = passportService.getAllPassport();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSavePassport() {

        PassportDTO passportDto = new PassportDTO();
        passportDto.setId(ACCOUNT_ID);
        passportDto.setSeries(SERIES);
        passportDto.setNumber(NUMBER);
        passportDto.setLastName(LAST_NAME);
        passportDto.setFirstName(FIRST_NAME);
        passportDto.setMiddleName(MIDDLE_NAME);
        passportDto.setGender(GENDER);
        passportDto.setBirthPlace(BIRTH_PLACE);
        passportDto.setIssuedBy(ISSUED_BY);
        passportDto.setDivisionCode(DIVISION_CODE);

        Passport passport = new Passport();
        passport.setId(ACCOUNT_ID);
        passport.setSeries(SERIES);
        passport.setNumber(NUMBER);
        passport.setLastName(LAST_NAME);
        passport.setFirstName(FIRST_NAME);
        passport.setMiddleName(MIDDLE_NAME);
        passport.setGender(GENDER);
        passport.setBirthPlace(BIRTH_PLACE);
        passport.setIssuedBy(ISSUED_BY);
        passport.setDivisionCode(DIVISION_CODE);

        when(passportMapper.toEntity(any(PassportDTO.class))).thenReturn(passport);
        when(passportRepository.save(any(Passport.class))).thenReturn(passport);
        when(passportMapper.toDto(any(Passport.class))).thenReturn(passportDto);

        PassportDTO savedPassportDto = passportService.savePassport(passportDto);

        assertEquals(passportDto, savedPassportDto);
    }


    @Test
    void testUpdatePassport() {

        PassportDTO passportDto = new PassportDTO();
        passportDto.setId(ACCOUNT_ID);
        passportDto.setSeries(SERIES);
        passportDto.setNumber(NUMBER);
        passportDto.setLastName(LAST_NAME);
        passportDto.setFirstName(FIRST_NAME);
        passportDto.setMiddleName(MIDDLE_NAME);
        passportDto.setGender(GENDER);
        passportDto.setBirthPlace(BIRTH_PLACE);
        passportDto.setIssuedBy(ISSUED_BY);
        passportDto.setDivisionCode(DIVISION_CODE);

        Passport passport = new Passport();
        passport.setId(ACCOUNT_ID);
        passport.setSeries(SERIES);
        passport.setNumber(NUMBER);
        passport.setLastName(LAST_NAME);
        passport.setFirstName(FIRST_NAME);
        passport.setMiddleName(MIDDLE_NAME);
        passport.setGender(GENDER);
        passport.setBirthPlace(BIRTH_PLACE);
        passport.setIssuedBy(ISSUED_BY);
        passport.setDivisionCode(DIVISION_CODE);

        when(passportRepository.findById(anyLong())).thenReturn(Optional.of(passport));
        when(passportMapper.toEntity(any(PassportDTO.class))).thenReturn(passport);
        when(passportRepository.save(any(Passport.class))).thenReturn(passport);
        when(passportMapper.toDto(any(Passport.class))).thenReturn(passportDto);

        PassportDTO updatedPassportDto = passportService.updatePassport(passportDto);

        assertEquals(passportDto, updatedPassportDto);
    }

    @Test
    void testDeletePassportById() {
        Long id = 1L;

        when(passportRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> passportService.deletePassportById(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Passport entity = new Passport();
        PassportDTO dto = new PassportDTO();

        when(passportRepository.findById(id)).thenReturn(Optional.of(entity));
        when(passportMapper.toDto(entity)).thenReturn(dto);

        PassportDTO result = passportService.getPassportById(id);

        assertEquals(dto, result);
        verify(passportRepository).findById(id);
        verify(passportMapper).toDto(entity);
    }

    @Test
    void getAllPassportTest() {

        Passport passport = new Passport();
        PassportDTO passportDto = new PassportDTO();
        List<Passport> passportEntities = List.of(passport);
        List<PassportDTO> passportDtos = List.of(passportDto);

        when(passportRepository.findAll()).thenReturn(passportEntities);
        when(passportMapper.toDtoList(passportEntities)).thenReturn(passportDtos);

        List<PassportDTO> result = passportService.getAllPassport();

        assertEquals(passportDtos, result);
        verify(passportRepository).findAll();
        verify(passportMapper).toDtoList(passportEntities);
    }

    @Test
    public void testGetPassportById_ExistingId() throws EntityNotFoundException {
        // Arrange
        Long id = 1L;
        Passport passport = new Passport();
        passport.setId(id);
        PassportDTO expectedDto = new PassportDTO(); // Предполагается, что у вас есть конструктор или метод для заполнения DTO
        when(passportRepository.findById(id)).thenReturn(java.util.Optional.of(passport));
        when(passportMapper.toDto(passport)).thenReturn(expectedDto);

        // Act
        PassportDTO resultDto = passportService.getPassportById(id);

        // Assert
        assertEquals(expectedDto, resultDto);
    }

    @Test
    public void testGetPassportById_NonExistingId() {
        // Arrange
        Long id = 1L;
        when(passportRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> passportService.getPassportById(id));
    }

}
