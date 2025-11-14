package com.bank.profile.controllers;

import com.bank.profile.DTO.PassportDTO;
import com.bank.profile.services.impl.PassportServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassportRestController.class)
class PassportRestControllerTest {

    private static final String BASE_URL = "/passport";
    private static final String AUDIT_BY_ID_URL = BASE_URL + "/get/{id}";
    private static final String ALL_AUDITS_URL = BASE_URL + "/getall";
    private static final long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassportServiceImpl service;

    @Autowired
    private ObjectMapper objectMapper;

    private PassportDTO createTestPassportDto() {
        return new PassportDTO();
    }

    @Test
    void getById() throws Exception {
        PassportDTO dto = createTestPassportDto();
        when(service.getPassportById(ID)).thenReturn(dto);

        mockMvc.perform(get(AUDIT_BY_ID_URL, ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

        verify(service, times(1)).getPassportById(ID);
    }

    @Test
    void getAllAudit() throws Exception {
        PassportDTO dto = createTestPassportDto();
        List<PassportDTO> dtoList = Collections.singletonList(dto);
        when(service.getAllPassport()).thenReturn(dtoList);

        mockMvc.perform(get(ALL_AUDITS_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtoList)));

        verify(service, times(1)).getAllPassport();
    }

}
