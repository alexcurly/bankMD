package com.bank.history.services;

import com.bank.history.DTO.HistoryDTO;
import java.util.List;


public interface HistoryService {
    HistoryDTO saveHistory(HistoryDTO historyDTO);
    HistoryDTO updateHistory(HistoryDTO historyDTO);
    void deleteHistoryById(Long id);
    HistoryDTO getHistoryById(Long id);
    List<HistoryDTO> getAllHistory();

}

