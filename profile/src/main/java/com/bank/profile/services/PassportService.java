package com.bank.profile.services;

import com.bank.profile.DTO.PassportDTO;
import java.util.List;

public interface PassportService {
    PassportDTO savePassport(PassportDTO passportDTO);
    PassportDTO getPassportById (Long id);
    List<PassportDTO> getAllPassport();
    PassportDTO updatePassport(PassportDTO passportDto);
    void deletePassportById(Long id);
}
