package com.bank.profile.controllers;

import com.bank.profile.DTO.AuditDTO;
import com.bank.profile.services.impl.AuditServiceImpl;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Timed(value = "controller ClassAudit")
@RequestMapping("/audit")
public class AuditRestController {

    private final AuditServiceImpl auditService;

    @Timed(value = "controller saveAudit")
    @PostMapping("/save")
    @Operation(summary = "Create new audit.")
    @ApiResponse(responseCode = "201", description = "The entity was created successfully!",
            content = {@Content(schema = @Schema(implementation = AuditDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<AuditDTO> saveAudit(@Valid @RequestBody AuditDTO auditDto) {
        log.info("Получен запрос на создание новой сущности: AuditEntity");
        AuditDTO createdAuditDto = auditService.saveAudit(auditDto);
        log.info("Сущность \\\"Audit\\\" успешно создана с ID = {}", createdAuditDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuditDto);
    }

    @Timed(value = "controller getAllAudit")
    @GetMapping("/getall")
    @Operation(summary = "Show all audits.")
    @ApiResponse(responseCode = "200", description = "All audits get successfully!",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<AuditDTO>> getAllAudit() {
        log.info("This method is started to show all entities.");
        return new ResponseEntity<>(auditService.getAllAudit(), HttpStatus.OK);
    }

    @Timed(value = "controller getAuditById")
    @GetMapping("/get/{id}")
    @Operation(summary = "Get one audit by ID.")
    @ApiResponse(responseCode = "200", description = "Audit is got successfully.",
            content = {@Content(schema = @Schema(implementation = AuditDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<AuditDTO> getAuditById(@PathVariable(name = "id") Long id) {
        log.info("This method is started to show one entity by ID = {}.", id);
        return new ResponseEntity<>(auditService.getAuditById(id), HttpStatus.OK);
    }

    @Timed(value = "controller updateAudit")
    @PutMapping("/update")
    @Operation(summary = "Update audit.")
    @ApiResponse(responseCode = "200", description = "Audit update successfully.",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HttpStatus> update(@RequestBody AuditDTO auditDto) {
        log.info("Audit will be update");
        auditService.updateAudit(auditDto);
        log.info("Audit updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Timed(value = "controller deleteAuditById")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete audit by ID.")
    @ApiResponse(responseCode = "204", description = "Audit delete successfully!")
    public ResponseEntity<AuditDTO> deleteAuditById(@PathVariable(name = "id") Long id) {
        log.info("Request to delete audit with ID = {}", id);
        auditService.deleteAuditById(id);
        log.info("Audit by ID = {} was deleted successfully!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
