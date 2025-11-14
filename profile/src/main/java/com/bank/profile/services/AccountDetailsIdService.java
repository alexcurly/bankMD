package com.bank.profile.services;

import com.bank.profile.DTO.AccountDetailsIdDTO;
import java.util.List;

public interface AccountDetailsIdService {
    AccountDetailsIdDTO getAccountDetailsIdById (Long id);
    List<AccountDetailsIdDTO> getAllAccountDetailsId();
    AccountDetailsIdDTO saveAccountDetailsId(AccountDetailsIdDTO accountDetailsIdDto);
    AccountDetailsIdDTO updateAccountDetailsId(AccountDetailsIdDTO accountDetailsIdDto);
    void deleteAccountDetailsIdById(Long id);
}
