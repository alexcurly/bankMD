package com.bank.profile.controllers;

import com.bank.profile.DTO.AuditDTO;
import com.bank.profile.services.impl.AuditServiceImpl;
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

@WebMvcTest(AuditRestController.class)
class AuditRestControllerTest {

    private static final String BASE_URL = "/audit";
    private static final String AUDIT_BY_ID_URL = BASE_URL + "/get/{id}";
    private static final String ALL_AUDITS_URL = BASE_URL + "/getall";
    private static final long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditServiceImpl service;

    @Autowired
    private ObjectMapper objectMapper;

    private AuditDTO createTestAuditDto() {
        return new AuditDTO();
    }

    @Test
    void getById() throws Exception {
        AuditDTO dto = createTestAuditDto();
        when(service.getAuditById(ID)).thenReturn(dto);

        mockMvc.perform(get(AUDIT_BY_ID_URL, ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

        verify(service, times(1)).getAuditById(ID);
    }

    @Test
    void getAllAudit() throws Exception {
        AuditDTO dto = createTestAuditDto();
        List<AuditDTO> dtoList = Collections.singletonList(dto);
        when(service.getAllAudit()).thenReturn(dtoList);

        mockMvc.perform(get(ALL_AUDITS_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtoList)));

        verify(service, times(1)).getAllAudit();
    }

}
