package com.bank.profile.services.impl;

import com.bank.profile.DTO.PassportDTO;
import com.bank.profile.exception.EntityNotFoundException;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.models.Passport;
import com.bank.profile.repositories.PassportRepository;
import com.bank.profile.services.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;

    private static final String AUDIT_NOT_FOUND_MESSAGE = "Passport wasn't found by id ";

    @Override
    public PassportDTO getPassportById(Long id) {
        return passportMapper.toDto(passportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id)));
    }

    @Override
    public List<PassportDTO> getAllPassport() {
        return passportMapper.toDtoList(passportRepository.findAll());
    }

    @Override
    public PassportDTO savePassport(PassportDTO passportDto) {
        Passport passport = passportMapper.toEntity(passportDto);

        log.info("Создание регистрации с данными: {}", passport);
        if (passport.getLastName() == null) {
            throw new IllegalArgumentException("LastName field is required");
        }
        if (passport.getFirstName() == null) {
            throw new IllegalArgumentException("FirstName field is required");
        }
        if (passport.getGender() == null) {
            throw new IllegalArgumentException("Gender field is required");
        }
        if (passport.getBirthPlace() == null) {
            throw new IllegalArgumentException("BirthPlace field is required");
        }
        if (passport.getIssuedBy() == null) {
            throw new IllegalArgumentException("IssuedBy field is required");
        }
        Passport savedPassport = passportRepository.save(passport);
        return passportMapper.toDto(savedPassport);
    }

    @Override
    public PassportDTO updatePassport(PassportDTO passportDto) {
        Passport passport = passportMapper.toEntity(passportDto);

        log.info("Создание регистрации с данными: {}", passport);
        if (passport.getLastName() == null) {
            throw new IllegalArgumentException("LastName field is required");
        }
        if (passport.getFirstName() == null) {
            throw new IllegalArgumentException("FirstName field is required");
        }
        if (passport.getGender() == null) {
            throw new IllegalArgumentException("Gender field is required");
        }
        if (passport.getBirthPlace() == null) {
            throw new IllegalArgumentException("BirthPlace field is required");
        }
        if (passport.getIssuedBy() == null) {
            throw new IllegalArgumentException("IssuedBy field is required");
        }

        passport = passportRepository.findById(passportDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Детали Audit with ID " + passportDto.getId() + " не найдено"));

        passportRepository.save(passportMapper.toEntity(passportDto));
        log.info("Сведения об учетной записи успешно обновлены с идентификатором = {}.", passportDto.getId());
        return passportMapper.toDto(passport);
    }

    @Override
    public void deletePassportById(Long id) {
        if (!passportRepository.existsById(id)) {
            throw new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id);
        }
        passportRepository.deleteById(id);
    }
}
