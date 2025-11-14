package com.bank.history.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import com.bank.history.DTO.HistoryDTO;
import com.bank.history.exception.EntityNotFoundException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.models.History;
import com.bank.history.repositories.HistoryRepository;
import com.bank.history.services.HistoryService;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    private static final String AUDIT_NOT_FOUND_MESSAGE = "History wasn't found by id ";

    @Override
    public HistoryDTO getHistoryById(Long id) {
        return historyMapper.toDto(historyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id)));
    }

    @Override
    public List<HistoryDTO> getAllHistory() {
        return historyMapper.toDtoList(historyRepository.findAll());
    }

    @Override
    public HistoryDTO saveHistory(HistoryDTO historyDto) {
        log.info("Attempting to save history: {}", historyDto);

        if (historyDto.getTransferAuditId() == null) {
            log.error("Transfer Audit ID is null");
            throw new IllegalArgumentException("Transfer Audit ID field is required");
        }

        History history = historyMapper.toEntity(historyDto);
        log.info("Mapped to entity: {}", history);

        try {
            History savedHistory = historyRepository.save(history);
            log.info("Successfully saved history with ID: {}", savedHistory.getId());
            return historyMapper.toDto(savedHistory);
        } catch (Exception e) {
            log.error("Error saving history: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public HistoryDTO updateHistory(HistoryDTO historyDto) {

        if (historyDto.getTransferAuditId() == null) {
            throw new IllegalArgumentException("Transfer Audit ID field is required");
        }

        History history = historyRepository.findById(historyDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Детали Audit with ID " + historyDto.getId() + " не найдено"));

        historyRepository.save(historyMapper.toEntity(historyDto));
        log.info("Сведения об учетной записи успешно обновлены с идентификатором = {}.", historyDto.getId());
        return historyMapper.toDto(history);
    }

    @Override
    public void deleteHistoryById(Long id) {
        if (!historyRepository.existsById(id)) {
            throw new EntityNotFoundException(AUDIT_NOT_FOUND_MESSAGE + id);
        }
        historyRepository.deleteById(id);
    }
}
