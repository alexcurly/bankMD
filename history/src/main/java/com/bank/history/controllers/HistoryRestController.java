package com.bank.history.controllers;

import com.bank.history.DTO.HistoryDTO;
import com.bank.history.services.impl.HistoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Класс-контроллер для обработки ввода и
 * предоставления информации связанной с History.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об History.
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/history")
@Slf4j
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class HistoryRestController {

    private final HistoryServiceImpl historyService;

    @GetMapping("/test")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("CORS test successful!");
    }

    @PostMapping("/save")
    @Operation(summary = "Create new history.")
    @ApiResponse(responseCode = "201", description = "The entity was created successfully!",
            content = {@Content(schema = @Schema(implementation = HistoryDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HistoryDTO> saveHistory(@Valid @RequestBody HistoryDTO historyDto) {
        log.info("New entity History is request to create.");
        final HistoryDTO dto = historyService.saveHistory(historyDto);
        log.info("The entity History was created successfully with ID = {}", dto.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getall")
    @Operation(summary = "Show all histories.")
    @ApiResponse(responseCode = "200", description = "All histories get successfully!",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<HistoryDTO>> getAllHistory() {
        log.info("This method is started to show all entities.");
        return new ResponseEntity<>(historyService.getAllHistory(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get one history by ID.")
    @ApiResponse(responseCode = "200", description = "History is got successfully.",
            content = {@Content(schema = @Schema(implementation = HistoryDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HistoryDTO> getHistoryById(@PathVariable(name = "id") Long id) {
        log.info("This method is started to show one entity by ID = {}.", id);
        return new ResponseEntity<>(historyService.getHistoryById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update history.")
    @ApiResponse(responseCode = "200", description = "History update successfully.",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HttpStatus> updateHistory(@Valid @RequestBody HistoryDTO historyDto) {
        log.info("History will be update");
        historyService.updateHistory(historyDto);
        log.info("History updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete history by ID.")
    @ApiResponse(responseCode = "204", description = "History delete successfully!")
    public ResponseEntity<HistoryDTO> deleteHistoryById(@PathVariable(name = "id") Long id) {
        log.info("Request to delete history with ID = {}", id);
        historyService.deleteHistoryById(id);
        log.info("History by ID = {} was deleted successfully!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
