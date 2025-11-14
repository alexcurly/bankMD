package com.bank.profile.services.impl;

import com.bank.profile.DTO.AccountDetailsIdDTO;
import com.bank.profile.exception.EntityNotFoundException;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.models.Account_details_id;
import com.bank.profile.repositories.AccountDetailsIdRepository;
import com.bank.profile.services.AccountDetailsIdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDetailsIdServiceImpl implements AccountDetailsIdService {

    private final AccountDetailsIdRepository accountDetailsIdRepository;
    private final AccountDetailsIdMapper accountDetailsIdMapper;
    private final Logger logger = LoggerFactory.getLogger(AccountDetailsIdDTO.class);

    private static final String AUDIT_NOT_FOUND_MESSAGE = "AccountDetailsId wasn't found by id ";

    @Override
    public AccountDetailsIdDTO getAccountDetailsIdById(Long id) {
        return accountDetailsIdMapper.toDto(accountDetailsIdRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id)));
    }

    @Override
    public List<AccountDetailsIdDTO> getAllAccountDetailsId() {
        return accountDetailsIdMapper.toDtoList(accountDetailsIdRepository.findAll());
    }

    @Override
    public AccountDetailsIdDTO saveAccountDetailsId(AccountDetailsIdDTO accountDetailsIdDto) {
        Account_details_id account_details_id = accountDetailsIdMapper.toEntity(accountDetailsIdDto);

        log.info("Создание регистрации с данными: {}", account_details_id);
        if (account_details_id.getProfileAccountDetailsId() == null) {
            throw new IllegalArgumentException("getProfileAccountDetailsId field is required");
        }
        Account_details_id savedAccountDetailsId = accountDetailsIdRepository.save(account_details_id);
        return accountDetailsIdMapper.toDto(account_details_id);
    }

    @Override
    public AccountDetailsIdDTO updateAccountDetailsId(AccountDetailsIdDTO accountDetailsIdDto) {
        Account_details_id account_details_id = accountDetailsIdMapper.toEntity(accountDetailsIdDto);

        log.info("Создание аудита с данными: {}", account_details_id);
        if (accountDetailsIdDto.getProfileAccountDetailsId() == null) {
            throw new IllegalArgumentException("ProfileAccountDetailsId field is required");
        }

        Account_details_id accountDetailsId = accountDetailsIdRepository.findById(accountDetailsIdDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Детали Audit with ID " + accountDetailsIdDto.getId() + " не найдено"));

        accountDetailsIdRepository.save(accountDetailsIdMapper.toEntity(accountDetailsIdDto));
        log.info("Сведения об учетной записи успешно обновлены с идентификатором = {}.", accountDetailsIdDto.getId());

        return accountDetailsIdMapper.toDto(account_details_id);
    }

    @Override
    public void deleteAccountDetailsIdById(Long id) {
        if (!accountDetailsIdRepository.existsById(id)) {
            throw new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id);
        }
        accountDetailsIdRepository.deleteById(id);
    }

}
