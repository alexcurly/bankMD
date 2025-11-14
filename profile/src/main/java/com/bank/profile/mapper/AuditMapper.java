package com.bank.profile.mapper;

import com.bank.profile.DTO.AuditDTO;
import com.bank.profile.models.Audit;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    Audit toEntity(AuditDTO auditDTO);
    AuditDTO toDto(Audit audit);
    List<AuditDTO> toDtoList(List<Audit> audits);
}
