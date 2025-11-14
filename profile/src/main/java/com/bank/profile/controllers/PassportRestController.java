package com.bank.profile.controllers;

import com.bank.profile.DTO.PassportDTO;
import com.bank.profile.services.impl.PassportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
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

/**
 * Класс-контроллер для обработки ввода и
 * предоставления информации связанной с Passport.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об Passport.
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/passport")
@Slf4j
public class PassportRestController {

    private final PassportServiceImpl passportService;

    @PostMapping("/save")
    @Operation(summary = "Create new passport.")
    @ApiResponse(responseCode = "201", description = "The entity was created successfully!",
            content = {@Content(schema = @Schema(implementation = PassportDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<PassportDTO> savePassport(@Valid @RequestBody PassportDTO passportDto) {
        log.info("New entity Passport is request to create.");
        final PassportDTO dto = passportService.savePassport(passportDto);
        log.info("The entity Passport was created successfully with ID = {}", dto.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getall")
    @Operation(summary = "Show all passports.")
    @ApiResponse(responseCode = "200", description = "All passports get successfully!",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<PassportDTO>> getAllPassport() {
        log.info("This method is started to show all entities.");
        return new ResponseEntity<>(passportService.getAllPassport(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get one passport by ID.")
    @ApiResponse(responseCode = "200", description = "Passport is got successfully.",
            content = {@Content(schema = @Schema(implementation = PassportDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<PassportDTO> getPassportById(@PathVariable(name = "id") Long id) {
        log.info("This method is started to show one entity by ID = {}.", id);
        return new ResponseEntity<>(passportService.getPassportById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update passport.")
    @ApiResponse(responseCode = "200", description = "Passport update successfully.",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HttpStatus> updatePassport(@Valid @RequestBody PassportDTO passportDto) {
        log.info("Passport will be update");
        passportService.updatePassport(passportDto);
        log.info("Passport updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete passport by ID.")
    @ApiResponse(responseCode = "204", description = "Passport delete successfully!")
    public ResponseEntity<PassportDTO> deletePassportById(@PathVariable(name = "id") Long id) {
        log.info("Request to delete passport with ID = {}", id);
        passportService.deletePassportById(id);
        log.info("Passport by ID = {} was deleted successfully!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
