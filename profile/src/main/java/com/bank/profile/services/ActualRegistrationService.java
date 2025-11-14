package com.bank.profile.services;

import com.bank.profile.DTO.ActualRegistrationDTO;
import java.util.List;

public interface ActualRegistrationService {
    ActualRegistrationDTO getActualRegistrationById(Long id);
    List<ActualRegistrationDTO> getAllActualRegistration();
    ActualRegistrationDTO saveActualRegistration(ActualRegistrationDTO actualRegistrationDTO);
    ActualRegistrationDTO updateActualRegistration(ActualRegistrationDTO actualRegistrationDto);
    void deleteActualRegistrationById(Long id);
}
