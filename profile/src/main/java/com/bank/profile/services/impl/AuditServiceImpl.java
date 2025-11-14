package com.bank.profile.services.impl;

import com.bank.profile.DTO.AuditDTO;
import com.bank.profile.exception.EntityNotFoundException;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.models.Audit;
import com.bank.profile.repositories.AuditRepository;
import com.bank.profile.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private static final String AUDIT_NOT_FOUND_MESSAGE = "Audit wasn't found by id ";

    @Override
    public AuditDTO getAuditById(Long id) {
        return auditMapper.toDto(auditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id)));
    }

    @Override
    public List<AuditDTO> getAllAudit() {
        return auditMapper.toDtoList(auditRepository.findAll());
    }

    @Override
    public AuditDTO saveAudit(AuditDTO auditDto) {

        Audit audit = auditMapper.toEntity(auditDto);
        audit.setCreatedAt(LocalDateTime.now());

        log.info("Создание аудита с данными: {}", audit);
        if (auditDto.getEntityType() == null) {
            throw new IllegalArgumentException("EntityType field is required");
        }
        if (auditDto.getOperationType() == null) {
            throw new IllegalArgumentException("OperationType field is required");
        }
        if (auditDto.getCreatedBy() == null) {
            throw new IllegalArgumentException("CreatedBy field is required");
        }
        if (auditDto.getEntityJson() == null) {
            throw new IllegalArgumentException("EntityJson field is required");
        }

        Audit savedAuditEntity = auditRepository.save(audit);
        return auditMapper.toDto(savedAuditEntity);
    }

    @Override
    public AuditDTO updateAudit(AuditDTO auditDto) {

        if (auditDto.getEntityType() == null) {
            throw new IllegalArgumentException("EntityType field is required");
        }
        if (auditDto.getOperationType() == null) {
            throw new IllegalArgumentException("OperationType field is required");
        }
        if (auditDto.getCreatedBy() == null) {
            throw new IllegalArgumentException("CreatedBy field is required");
        }
        if (auditDto.getEntityJson() == null) {
            throw new IllegalArgumentException("EntityJson field is required");
        }
        log.info("Попытка обновить Audit с идентификатором = {}", auditDto.getId());
        Audit audit = auditRepository.findById(auditDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Детали Audit with ID " + auditDto.getId() + " не найдено"));

        auditRepository.save(auditMapper.toEntity(auditDto));
        log.info("Сведения об учетной записи успешно обновлены с идентификатором = {}.", auditDto.getId());
        return auditMapper.toDto(audit);
    }

    @Override
    public void deleteAuditById(Long id) {
        if (!auditRepository.existsById(id)) {
            throw new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id);
        }
        auditRepository.deleteById(id);
    }
}
