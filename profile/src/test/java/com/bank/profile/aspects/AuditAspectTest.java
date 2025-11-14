package com.bank.profile.aspects;

import com.bank.profile.models.Audit;
import com.bank.profile.repositories.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

    class AuditAspectTest {

        @Mock
        private AuditRepository auditRepo;

        @Mock
        private ObjectMapper objectMapper;

        @InjectMocks
        private AuditAspect auditAspect;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void auditCreate() throws Exception {
            Object result = new Object();
            String json = "{\"key\":\"value\"}";

            when(objectMapper.writeValueAsString(result)).thenReturn(json);

            auditAspect.auditCreateOperation(result);

            verify(auditRepo).save(argThat(audit ->
                    "CREATE".equals(audit.getOperationType()) &&
                            "Object".equals(audit.getEntityType()) &&
                            LocalDateTime.now().isAfter(audit.getCreatedAt().minusSeconds(1)) &&
                            LocalDateTime.now().isBefore(audit.getCreatedAt().plusSeconds(1)) &&
                            json.equals(audit.getEntityJson())
            ));
        }

        @Test
        void auditUpdate() throws Exception {
            Object result = new Object();
            String json = "{\"key\":\"value\"}";

            when(objectMapper.writeValueAsString(result)).thenReturn(json);

            auditAspect.auditUpdateOperation(result);

            verify(auditRepo).save(argThat(audit ->
                    "UPDATE".equals(audit.getOperationType()) &&
                            "Object".equals(audit.getEntityType()) &&
                            LocalDateTime.now().isAfter(audit.getCreatedAt().minusSeconds(1)) &&
                            LocalDateTime.now().isBefore(audit.getCreatedAt().plusSeconds(1)) &&
                            json.equals(audit.getEntityJson())
            ));
        }

        @Test
        void auditDelete() throws Exception {
            Object result = new Object();
            String json = "{\"key\":\"value\"}";

            when(objectMapper.writeValueAsString(result)).thenReturn(json);

            auditAspect.auditDeleteOperation(result);

            verify(auditRepo).save(argThat(audit ->
                    "DELETE".equals(audit.getOperationType()) &&
                            "Object".equals(audit.getEntityType()) &&
                            LocalDateTime.now().isAfter(audit.getCreatedAt().minusSeconds(1)) &&
                            LocalDateTime.now().isBefore(audit.getCreatedAt().plusSeconds(1)) &&
                            json.equals(audit.getEntityJson())
            ));
        }

        @Test
        void auditIgnoresNullResult() {
            auditAspect.auditCreateOperation(null);
            verify(auditRepo, never()).save(any(Audit.class));
        }
    }
