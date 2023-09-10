package com.example.miniproject.service;

import com.example.miniproject.dto.UserHistoryResponseDTO;

import java.util.List;

public interface UserHistoryService {
    public List<UserHistoryResponseDTO> viewHistory(int userId);
}
