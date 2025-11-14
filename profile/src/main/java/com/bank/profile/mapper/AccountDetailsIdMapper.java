package com.bank.profile.mapper;

import com.bank.profile.DTO.AccountDetailsIdDTO;
import com.bank.profile.models.*;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper (componentModel = "spring")
public interface AccountDetailsIdMapper {
    Account_details_id toEntity(AccountDetailsIdDTO accountDetailsIdDTO);
    AccountDetailsIdDTO toDto(Account_details_id account_details_id);
    List<AccountDetailsIdDTO> toDtoList (List<Account_details_id> account_details_ids);
}
