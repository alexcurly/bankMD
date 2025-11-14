package com.bank.profile.services.impl;

import com.bank.profile.DTO.RegistrationDTO;
import com.bank.profile.exception.EntityNotFoundException;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.models.Registration;
import com.bank.profile.repositories.RegistrationRepository;
import com.bank.profile.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;

    private static final String AUDIT_NOT_FOUND_MESSAGE = "Registration wasn't found by id ";

    @Override
    public RegistrationDTO getRegistrationById(Long id) {
        return registrationMapper.toDto(registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id)));
    }

    @Override
    public List<RegistrationDTO> getAllRegistration() {
        return registrationMapper.toDoList(registrationRepository.findAll());
    }

    @Override
    public RegistrationDTO saveRegistration(RegistrationDTO registrationDto) {
        Registration registration = registrationMapper.toEntity(registrationDto);

        log.info("Создание регистрации с данными: {}", registration);
        if (registrationDto.getCountry() == null) {
            throw new IllegalArgumentException("Country field is required");
        }
        Registration savedRegistration = registrationRepository.save(registration);
        return registrationMapper.toDto(savedRegistration);
    }

    @Override
    public RegistrationDTO updateRegistration(RegistrationDTO registrationDto) {

        if (registrationDto.getCountry() == null) {
            throw new IllegalArgumentException("Country field is required");
        }

        Registration registration = registrationRepository.findById(registrationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Детали Audit with ID " + registrationDto.getId() + " не найдено"));

        registrationRepository.save(registrationMapper.toEntity(registrationDto));
        log.info("Сведения об учетной записи успешно обновлены с идентификатором = {}.", registrationDto.getId());
        return registrationMapper.toDto(registration);
    }

    @Override
    public void deleteRegistrationById(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id);
        }
        registrationRepository.deleteById(id);
    }

}
