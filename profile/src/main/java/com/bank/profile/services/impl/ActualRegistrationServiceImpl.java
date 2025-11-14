package com.bank.profile.services.impl;

import com.bank.profile.DTO.ActualRegistrationDTO;
import com.bank.profile.exception.EntityNotFoundException;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.models.Actual_registration;
import com.bank.profile.repositories.ActualRegistrationRepository;
import com.bank.profile.services.ActualRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActualRegistrationServiceImpl implements ActualRegistrationService {

    private final ActualRegistrationRepository actualRegistrationRepository;
    private final ActualRegistrationMapper actualRegistrationMapper;
    private final Logger logger = LoggerFactory.getLogger(ActualRegistrationDTO.class);

    private static final String AUDIT_NOT_FOUND_MESSAGE = "Registration wasn't found by id ";

    @Override
    public ActualRegistrationDTO getActualRegistrationById(Long id) {
        return actualRegistrationMapper.toDto(actualRegistrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id)));
    }

    @Override
    public List<ActualRegistrationDTO> getAllActualRegistration() {
        return actualRegistrationMapper.toDtoList(actualRegistrationRepository.findAll());
    }

    @Override
    public ActualRegistrationDTO saveActualRegistration(ActualRegistrationDTO actualRegistrationDto) {
        Actual_registration actualRegistration = actualRegistrationMapper.toEntity(actualRegistrationDto);

        log.info("Создание регистрации с данными: {}", actualRegistration);
        if (actualRegistration.getCountry() == null) {
            throw new IllegalArgumentException("Country field is required");
        }
        Actual_registration savedActualRegistration = actualRegistrationRepository.save(actualRegistration);
        return actualRegistrationMapper.toDto(savedActualRegistration);
    }

    @Override
    public ActualRegistrationDTO updateActualRegistration(ActualRegistrationDTO actualRegistrationDto) {
        Actual_registration actual_registration = actualRegistrationRepository.findById(actualRegistrationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Детали Audit with ID " + actualRegistrationDto.getId() + " не найдено"));

        actualRegistrationRepository.save(actualRegistrationMapper.toEntity(actualRegistrationDto));
        log.info("Сведения об учетной записи успешно обновлены с идентификатором = {}.", actualRegistrationDto.getId());
        return actualRegistrationMapper.toDto(actual_registration);
    }

    @Override
    public void deleteActualRegistrationById(Long id) {
        if (!actualRegistrationRepository.existsById(id)) {
            throw new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id);
        }
        actualRegistrationRepository.deleteById(id);
    }

}
