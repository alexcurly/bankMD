package com.bank.profile.services;

import com.bank.profile.DTO.RegistrationDTO;
import java.util.List;

public interface RegistrationService {
    List<RegistrationDTO> getAllRegistration();
    RegistrationDTO saveRegistration (RegistrationDTO registrationDTO);
    RegistrationDTO getRegistrationById (Long id);
    RegistrationDTO updateRegistration(RegistrationDTO registrationDto);
    void deleteRegistrationById(Long id);
}
