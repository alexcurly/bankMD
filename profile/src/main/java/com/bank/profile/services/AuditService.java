package com.bank.profile.services;

import com.bank.profile.DTO.AuditDTO;
import java.util.List;

public interface AuditService {
    AuditDTO getAuditById (Long id);
    List<AuditDTO> getAllAudit();
    AuditDTO saveAudit (AuditDTO auditDTO);
    AuditDTO updateAudit(AuditDTO auditDTO);
    void deleteAuditById(Long id);
}
