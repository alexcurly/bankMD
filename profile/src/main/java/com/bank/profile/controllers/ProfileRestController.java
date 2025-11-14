package com.bank.profile.controllers;

import com.bank.profile.DTO.ProfileDTO;
import com.bank.profile.services.impl.ProfileServiceImpl;
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
 * предоставления информации связанной с Profile.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об Profile.
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile")
@Slf4j
public class ProfileRestController {

    private final ProfileServiceImpl profileService;

    @PostMapping("/save")
    @Operation(summary = "Create new profile.")
    @ApiResponse(responseCode = "201", description = "The entity was created successfully!",
            content = {@Content(schema = @Schema(implementation = ProfileDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<ProfileDTO> saveProfile(@Valid @RequestBody ProfileDTO profileDto) {
        log.info("New entity Profile is request to create.");
        final ProfileDTO dto = profileService.saveProfile(profileDto);
        log.info("The entity Profile was created successfully with ID = {}", dto.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getall")
    @Operation(summary = "Show all profiles.")
    @ApiResponse(responseCode = "200", description = "All profiles get successfully!",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<ProfileDTO>> getAllProfile() {
        log.info("This method is started to show all entities.");
        return new ResponseEntity<>(profileService.getAllProfile(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get one profile by ID.")
    @ApiResponse(responseCode = "200", description = "Profile is got successfully.",
            content = {@Content(schema = @Schema(implementation = ProfileDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable(name = "id") Long id) {
        log.info("This method is started to show one entity by ID = {}.", id);
        return new ResponseEntity<>(profileService.getProfileById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update profile.")
    @ApiResponse(responseCode = "200", description = "Profile update successfully.",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HttpStatus> updateProfile(@PathVariable(name = "id") Long id, @Valid @RequestBody ProfileDTO profileDto) {
        log.info("Profile will be update");
        profileService.updateProfile(profileDto);
        log.info("Profile updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete profile by ID.")
    @ApiResponse(responseCode = "204", description = "Profile delete successfully!")
    public ResponseEntity<ProfileDTO> deleteProfileById(@PathVariable(name = "id") Long id) {
        log.info("Request to delete profile with ID = {}", id);
        profileService.deleteProfileById(id);
        log.info("Profile by ID = {} was deleted successfully!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
