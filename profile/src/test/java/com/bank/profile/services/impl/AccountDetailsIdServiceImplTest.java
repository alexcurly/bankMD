package com.bank.profile.services.impl;

import com.bank.profile.DTO.AccountDetailsIdDTO;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.models.Account_details_id;
import com.bank.profile.repositories.AccountDetailsIdRepository;
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

class AccountDetailsIdServiceImplTest {

    @Mock
    private AccountDetailsIdRepository accountDetailsIdRepository;

    @Mock
    private AccountDetailsIdMapper accountDetailsIdMapper;

    private AccountDetailsIdDTO saveAccountDetailsIdDtoT;

    @InjectMocks
    private AccountDetailsIdServiceImpl accountDetailsIdService;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    void testGetAccountDetailsIdById() {

        Long id = 1L;
        Account_details_id accountDetailsId = new Account_details_id();
        accountDetailsId.setId(id);
        AccountDetailsIdDTO expectedDto = new AccountDetailsIdDTO();
        expectedDto.setId(id);

        when(accountDetailsIdRepository.findById(anyLong())).thenReturn(Optional.of(accountDetailsId));
        when(accountDetailsIdMapper.toDto(any(Account_details_id.class))).thenReturn(expectedDto);

        AccountDetailsIdDTO actualDto = accountDetailsIdService.getAccountDetailsIdById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllAccountDetailsId() {

        List<Account_details_id> accountDetailsIds = List.of(new Account_details_id(), new Account_details_id());
        List<AccountDetailsIdDTO> expectedDtos = List.of(new AccountDetailsIdDTO(), new AccountDetailsIdDTO());

        when(accountDetailsIdRepository.findAll()).thenReturn(accountDetailsIds);
        when(accountDetailsIdMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<AccountDetailsIdDTO> actualDtos = accountDetailsIdService.getAllAccountDetailsId();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testDeleteAccountDetailsIdById() {
        Long id = 1L;

        when(accountDetailsIdRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> accountDetailsIdService.deleteAccountDetailsIdById(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Account_details_id entity = new Account_details_id();
        AccountDetailsIdDTO dto = new AccountDetailsIdDTO();

        when(accountDetailsIdRepository.findById(id)).thenReturn(Optional.of(entity));
        when(accountDetailsIdMapper.toDto(entity)).thenReturn(dto);

        AccountDetailsIdDTO result = accountDetailsIdService.getAccountDetailsIdById(id);

        assertEquals(dto, result);
        verify(accountDetailsIdRepository).findById(id);
        verify(accountDetailsIdMapper).toDto(entity);
    }

    @Test
    void getAllAccountDetailsIdTest() {

        Account_details_id accountDetailsId = new Account_details_id();
        AccountDetailsIdDTO accountDetailsIdDto = new AccountDetailsIdDTO();
        List<Account_details_id> accountDetailsIdEntities = List.of(accountDetailsId);
        List<AccountDetailsIdDTO> accountDetailsIdDtos = List.of(accountDetailsIdDto);

        when(accountDetailsIdRepository.findAll()).thenReturn(accountDetailsIdEntities);
        when(accountDetailsIdMapper.toDtoList(accountDetailsIdEntities)).thenReturn(accountDetailsIdDtos);

        List<AccountDetailsIdDTO> result = accountDetailsIdService.getAllAccountDetailsId();

        assertEquals(accountDetailsIdDtos, result);
        verify(accountDetailsIdRepository).findAll();
        verify(accountDetailsIdMapper).toDtoList(accountDetailsIdEntities);
    }

}
