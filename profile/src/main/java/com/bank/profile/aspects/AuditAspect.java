package com.bank.profile.aspects;

import com.bank.profile.models.Audit;
import com.bank.profile.repositories.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;

    @AfterReturning(pointcut = "execution(* com.bank.profile.services.*.add*(..))", returning = "result")
    public void auditCreateOperation(Object result) {
        log.info("Аудит операции СОЗДАНИЯ начат.");
        auditOperation(result, "CREATE");
    }

    @AfterReturning(pointcut = "execution(* com.bank.profile.services.*.update*(..))", returning = "result")
    public void auditUpdateOperation(Object result) {
        log.info("Аудит операции ОБНОВЛЕНИЯ начат.");
        auditOperation(result, "UPDATE");
    }

    @AfterReturning(pointcut = "execution(* com.bank.profile.services.*.delete*(..))", returning = "result")
    public void auditDeleteOperation(Object result) {
        log.info("Аудит операции УДАЛЕНИЯ начат.");
        auditOperation(result, "DELETE");
    }

    private void auditOperation(Object result, String operationType) {
        try {
            if (result == null) {
                log.warn("Аудит операции пропущен: результат равен null для операции {}", operationType);
                return;
            }

            log.debug("Начало аудита для операции: {}", operationType);

            String entityJson = objectMapper.writeValueAsString(result);
            log.debug("Сериализованная сущность: {}", entityJson);

            String entityType = result.getClass().getSimpleName();
            log.debug("Тип сущности: {}", entityType);

            String createdBy = "system";
            log.debug("Создано: {}", createdBy);

            Audit Audit = new Audit();
            Audit.setEntityType(entityType);
            Audit.setOperationType(operationType);
            Audit.setCreatedBy(createdBy);
            Audit.setCreatedAt(LocalDateTime.now());
            Audit.setEntityJson(entityJson);

            auditRepository.save(Audit);
            log.info("Аудит запись создана для операции: {} на сущности: {}", operationType, entityType);
        } catch (Exception e) {
            log.error("Ошибка аудита операции для {}: {}", operationType, e.getMessage(), e);
        }
    }
}
