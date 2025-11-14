package com.bank.profile.controllers;

import com.bank.profile.DTO.ActualRegistrationDTO;
import com.bank.profile.services.ActualRegistrationService;
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
 * предоставления информации связанной с ActualRegistration.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об ActualRegistration.
 **/

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/actual_registration")
public class ActualRegistrationRestController {

    private final ActualRegistrationService actualRegistrationService;

    /**
     * Метод, отвечающий за получение сущности из БД
     */
    @Timed(value = "controller save")
    @PostMapping("/save")
    @Operation(summary = "Create new saveActualRegistration.")
    @ApiResponse(responseCode = "201", description = "The entity was created successfully!",
            content = {@Content(schema = @Schema(implementation = ActualRegistrationDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<ActualRegistrationDTO> saveActualRegistration(@Valid @RequestBody ActualRegistrationDTO actualRegistrationDto) {
        log.info("New entity ActualRegistration is request to create.");
        final ActualRegistrationDTO dto = actualRegistrationService.saveActualRegistration(actualRegistrationDto);
        log.info("The entity ActualRegistration was created successfully with ID = {}", dto.getId());
        return ResponseEntity.ok(dto);
    }

    @Timed(value = "controller getAllActualRegistration")
    @GetMapping("/getall")
    @Operation(summary = "Show all actualRegistrations.")
    @ApiResponse(responseCode = "200", description = "All actualRegistrations get successfully!",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<ActualRegistrationDTO>> getAllActualRegistration() {
        log.info("This method is started to show all entities.");
        return new ResponseEntity<>(actualRegistrationService.getAllActualRegistration(), HttpStatus.OK);
    }

    @Timed(value = "controller getActualRegistrationById")
    @GetMapping("/get/{id}")
    @Operation(summary = "Get one actualRegistration by ID.")
    @ApiResponse(responseCode = "200", description = "ActualRegistration is got successfully.",
            content = {@Content(schema = @Schema(implementation = ActualRegistrationDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<ActualRegistrationDTO> getActualRegistrationById(@PathVariable(name = "id") Long id) {
        log.info("This method is started to show one entity by ID = {}.", id);
        return new ResponseEntity<>(actualRegistrationService.getActualRegistrationById(id), HttpStatus.OK);
    }

    @Timed(value = "controller updateActualRegistration")
    @PutMapping("/update")
    @Operation(summary = "Update actualRegistration.")
    @ApiResponse(responseCode = "200", description = "ActualRegistration update successfully.",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HttpStatus> updateActualRegistration(@Valid @RequestBody ActualRegistrationDTO actualRegistrationDto) {
        log.info("ActualRegistration will be update");
        actualRegistrationService.updateActualRegistration(actualRegistrationDto);
        log.info("ActualRegistration updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Timed(value = "controller deleteActualRegistrationById")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete actualRegistration by ID.")
    @ApiResponse(responseCode = "204", description = "ActualRegistration delete successfully!")
    public ResponseEntity<ActualRegistrationDTO> deleteActualRegistrationById(@PathVariable(name = "id") Long id) {
        log.info("Request to delete actualRegistration with ID = {}", id);
        actualRegistrationService.deleteActualRegistrationById(id);
        log.info("ActualRegistration by ID = {} was deleted successfully!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
