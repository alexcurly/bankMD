package com.bank.profile.controllers;

import com.bank.profile.DTO.RegistrationDTO;
import com.bank.profile.services.impl.RegistrationServiceImpl;
import io.micrometer.core.annotation.Timed;
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
 * предоставления информации связанной с Registration.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об Registration.
 **/

@RestController
@RequiredArgsConstructor
@Slf4j
@Timed(value = "controller ClassRegistration")
@RequestMapping(value = "/registration")
public class RegistrationRestController {

    private final RegistrationServiceImpl registrationService;

    /**
     * Метод, отвечающий за создание новой сущности
     */
    @Timed(value = "controller saveRegistration")
    @PostMapping("/save")
    @Operation(summary = "Create new registration.")
    @ApiResponse(responseCode = "201", description = "The entity was created successfully!",
            content = {@Content(schema = @Schema(implementation = RegistrationDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<RegistrationDTO> saveRegistration(@Valid @RequestBody RegistrationDTO registrationDto) {
        log.info("New entity Registration is request to create.");
        final RegistrationDTO dto = registrationService.saveRegistration(registrationDto);
        log.info("The entity Registration was created successfully with ID = {}", dto.getId());
        return ResponseEntity.ok(dto);
    }

    @Timed(value = "controller getAllRegistration")
    @GetMapping("/getall")
    @Operation(summary = "Show all registrations.")
    @ApiResponse(responseCode = "200", description = "All registrations get successfully!",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<RegistrationDTO>> getAllRegistration() {
        log.info("This method is started to show all entities.");
        return new ResponseEntity<>(registrationService.getAllRegistration(), HttpStatus.OK);
    }

    @Timed(value = "controller getRegistrationById")
    @GetMapping("/get/{id}")
    @Operation(summary = "Get one registration by ID.")
    @ApiResponse(responseCode = "200", description = "Registration is got successfully.",
            content = {@Content(schema = @Schema(implementation = RegistrationDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<RegistrationDTO> getRegistrationById(@PathVariable(name = "id") Long id) {
        log.info("This method is started to show one entity by ID = {}.", id);
        return new ResponseEntity<>(registrationService.getRegistrationById(id), HttpStatus.OK);
    }

    @Timed(value = "controller updateRegistration")
    @PutMapping("/update")
    @Operation(summary = "Update registration.")
    @ApiResponse(responseCode = "200", description = "Registration update successfully.",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HttpStatus> updateRegistration(@PathVariable(name = "id") Long id, @Valid @RequestBody RegistrationDTO registrationDto) {
        log.info("Registration will be update");
        registrationService.updateRegistration(registrationDto);
        log.info("Registration updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Timed(value = "controller deleteRegistrationById")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete registration by ID.")
    @ApiResponse(responseCode = "204", description = "Registration delete successfully!")
    public ResponseEntity<RegistrationDTO> deleteRegistrationById(@PathVariable(name = "id") Long id) {
        log.info("Request to delete registration with ID = {}", id);
        registrationService.deleteRegistrationById(id);
        log.info("Registration by ID = {} was deleted successfully!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
