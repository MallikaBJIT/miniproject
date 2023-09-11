package com.example.miniproject.service;

import com.example.miniproject.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    public UserResponseDTO getUserDetails(int userId);

    public List<String> getBookByUserId(int userId);

    public List<String> getBorrowedByBookByUserId(int userId);

}
