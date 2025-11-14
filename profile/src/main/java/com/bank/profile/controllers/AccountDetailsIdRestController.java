package com.bank.profile.controllers;

import com.bank.profile.DTO.AccountDetailsIdDTO;
import com.bank.profile.services.impl.AccountDetailsIdServiceImpl;
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

/**
 * Класс-контроллер для обработки ввода и
 * предоставления информации связанной с AccountDetailsId.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об AccountDetailsId.
 **/

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/accountdetailsid")
public class AccountDetailsIdRestController {

    private final AccountDetailsIdServiceImpl accountDetailsIdService;

    /**
     * Метод, отвечающий за получение сущности из БД
     */
//    @Timed(value = "controller saveAccountDetailsId")
    @PostMapping("/save")
    @Operation(summary = "Create new accountDetailsId.")
    @ApiResponse(responseCode = "201", description = "The entity was created successfully!",
            content = {@Content(schema = @Schema(implementation = AccountDetailsIdDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<AccountDetailsIdDTO> saveAccountDetailsId(@Valid @RequestBody AccountDetailsIdDTO accountDetailsIdDto) {
        log.info("New entity AccountDetailsId is request to create.");
        final AccountDetailsIdDTO dto = accountDetailsIdService.saveAccountDetailsId(accountDetailsIdDto);
        log.info("The entity AccountDetailsId was created successfully with ID = {}", dto.getId());
        return ResponseEntity.ok(dto);
    }

    @Timed(value = "controller getAllAccountDetailsId")
    @GetMapping("/getall")
    @Operation(summary = "Show all accountDetailsIds.")
    @ApiResponse(responseCode = "200", description = "All accountDetailsIds get successfully!",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<AccountDetailsIdDTO>> getAllAccountDetailsId() {
        log.info("This method is started to show all entities.");
        return new ResponseEntity<>(accountDetailsIdService.getAllAccountDetailsId(), HttpStatus.OK);
    }

    @Timed(value = "controller get")
    @GetMapping("/get/{id}")
    @Operation(summary = "Get one accountDetailsId by ID.")
    @ApiResponse(responseCode = "200", description = "AccountDetailsId is got successfully.",
            content = {@Content(schema = @Schema(implementation = AccountDetailsIdDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<AccountDetailsIdDTO> getAccountDetailsIdById(@PathVariable(name = "id") Long id) {
        log.info("This method is started to show one entity by ID = {}.", id);
        return new ResponseEntity<>(accountDetailsIdService.getAccountDetailsIdById(id), HttpStatus.OK);
    }

    @Timed(value = "controller updateAccountDetailsId")
    @PutMapping("/update")
    @Operation(summary = "Update accountDetailsId.")
    @ApiResponse(responseCode = "200", description = "AccountDetailsId update successfully.",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HttpStatus> updateAccountDetailsId(@Valid @RequestBody AccountDetailsIdDTO accountDetailsIdDto) {
        log.info("AccountDetailsId will be update");
        accountDetailsIdService.updateAccountDetailsId(accountDetailsIdDto);
        log.info("AccountDetailsId updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Timed(value = "controller deleteAccountDetailsIdById")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete accountDetailsId by ID.")
    @ApiResponse(responseCode = "204", description = "AccountDetailsId delete successfully!")
    public ResponseEntity<AccountDetailsIdDTO> deleteAccountDetailsIdById(@PathVariable(name = "id") Long id) {
        log.info("Request to delete accountDetailsId with ID = {}", id);
        accountDetailsIdService.deleteAccountDetailsIdById(id);
        log.info("AccountDetailsId by ID = {} was deleted successfully!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
