package com.bank.profile.DTO;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AuditDTO implements Serializable {
    private Long id;
    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    private String newEntityJson;
    private String entityJson;
}
