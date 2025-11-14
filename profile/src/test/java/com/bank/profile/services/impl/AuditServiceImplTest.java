package com.bank.profile.services.impl;

import com.bank.profile.DTO.AuditDTO;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.models.Audit;
import com.bank.profile.repositories.AuditRepository;
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

class AuditServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final String ENTITY_TYPE = "Russia";
    private static final String OPERATION_TYPE = "Russia";
    private static final String CREATE_BY = "Russia";
    private static final String MODIFIED_BY = "Russia";
    private static final String NEW_ENTITY_JSON = "Russia";
    private static final String ENTITY_JSON = "Russia";

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AuditMapper auditMapper;

    private AuditDTO saveAuditDtoT;

    @InjectMocks
    private AuditServiceImpl auditService;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    void testGetAuditById() {

        Long id = 1L;
        Audit audit = new Audit();
        audit.setId(id);
        AuditDTO expectedDto = new AuditDTO();
        expectedDto.setId(id);

        when(auditRepository.findById(anyLong())).thenReturn(Optional.of(audit));
        when(auditMapper.toDto(any(Audit.class))).thenReturn(expectedDto);

        AuditDTO actualDto = auditService.getAuditById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllAudit() {

        List<Audit> audits = List.of(new Audit(), new Audit());
        List<AuditDTO> expectedDtos = List.of(new AuditDTO(), new AuditDTO());

        when(auditRepository.findAll()).thenReturn(audits);
        when(auditMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<AuditDTO> actualDtos = auditService.getAllAudit();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveAudit() {

        AuditDTO auditDto = new AuditDTO();
        auditDto.setId(ACCOUNT_ID);
        auditDto.setEntityType(ENTITY_TYPE);
        auditDto.setOperationType(OPERATION_TYPE);
        auditDto.setCreatedBy(CREATE_BY);
        auditDto.setModifiedBy(MODIFIED_BY);
        auditDto.setNewEntityJson(NEW_ENTITY_JSON);
        auditDto.setEntityJson(ENTITY_JSON);

        Audit audit = new Audit();
        audit.setId(ACCOUNT_ID);
        audit.setEntityType(ENTITY_TYPE);
        audit.setOperationType(OPERATION_TYPE);
        audit.setCreatedBy(CREATE_BY);
        audit.setModifiedBy(MODIFIED_BY);
        audit.setNewEntityJson(NEW_ENTITY_JSON);
        audit.setEntityJson(ENTITY_JSON);

        when(auditMapper.toEntity(any(AuditDTO.class))).thenReturn(audit);
        when(auditRepository.save(any(Audit.class))).thenReturn(audit);
        when(auditMapper.toDto(any(Audit.class))).thenReturn(auditDto);

        AuditDTO savedAuditDto = auditService.saveAudit(auditDto);

        assertEquals(auditDto, savedAuditDto);
    }


    @Test
    void testUpdateAudit() {

        AuditDTO auditDto = new AuditDTO();
        auditDto.setId(ACCOUNT_ID);
        auditDto.setEntityType(ENTITY_TYPE);
        auditDto.setOperationType(OPERATION_TYPE);
        auditDto.setCreatedBy(CREATE_BY);
        auditDto.setModifiedBy(MODIFIED_BY);
        auditDto.setNewEntityJson(NEW_ENTITY_JSON);
        auditDto.setEntityJson(ENTITY_JSON);

        Audit audit = new Audit();
        audit.setId(ACCOUNT_ID);
        audit.setEntityType(ENTITY_TYPE);
        audit.setOperationType(OPERATION_TYPE);
        audit.setCreatedBy(CREATE_BY);
        audit.setModifiedBy(MODIFIED_BY);
        audit.setNewEntityJson(NEW_ENTITY_JSON);
        audit.setEntityJson(ENTITY_JSON);

        when(auditRepository.findById(anyLong())).thenReturn(Optional.of(audit));
        when(auditMapper.toEntity(any(AuditDTO.class))).thenReturn(audit);
        when(auditRepository.save(any(Audit.class))).thenReturn(audit);
        when(auditMapper.toDto(any(Audit.class))).thenReturn(auditDto);

        AuditDTO updatedAuditDto = auditService.updateAudit(auditDto);

        assertEquals(auditDto, updatedAuditDto);
    }

    @Test
    void testDeleteAuditById() {
        Long id = 1L;

        when(auditRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> auditService.deleteAuditById(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Audit entity = new Audit();
        AuditDTO dto = new AuditDTO();

        when(auditRepository.findById(id)).thenReturn(Optional.of(entity));
        when(auditMapper.toDto(entity)).thenReturn(dto);

        AuditDTO result = auditService.getAuditById(id);

        assertEquals(dto, result);
        verify(auditRepository).findById(id);
        verify(auditMapper).toDto(entity);
    }

    @Test
    void getAllAuditTest() {

        Audit audit = new Audit();
        AuditDTO auditDto = new AuditDTO();
        List<Audit> auditEntities = List.of(audit);
        List<AuditDTO> auditDtos = List.of(auditDto);

        when(auditRepository.findAll()).thenReturn(auditEntities);
        when(auditMapper.toDtoList(auditEntities)).thenReturn(auditDtos);

        List<AuditDTO> result = auditService.getAllAudit();

        assertEquals(auditDtos, result);
        verify(auditRepository).findAll();
        verify(auditMapper).toDtoList(auditEntities);
    }

}
