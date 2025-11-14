package com.bank.history.DTO;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Column;

@Data
public class HistoryDTO {
    private Long id;
    private Long transferAuditId;
    private Long profileAuditId;
    private Long accountAuditId;
    private Long antiFraudAuditId;
    private Long publicBankInfoAuditId;
    private Long authorizationAuditId;
}
