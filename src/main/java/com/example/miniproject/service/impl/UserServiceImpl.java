package com.example.miniproject.service.impl;

import com.example.miniproject.dto.UserResponseDTO;
import com.example.miniproject.entity.User;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.BorrowedBookRepository;
import com.example.miniproject.repository.UserHistoryRepository;
import com.example.miniproject.repository.UserRepository;
import com.example.miniproject.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserResponseDTO getUserDetails(int userId) {
        User user = getUserById(userId);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public List<String> getBookByUserId(int userId) {
        User user = getUserById(userId);
        return user.getUserHistories().stream().map(userHistory ->
                userHistory.getBook().getTitle()
        ).toList();
    }

    @Override
    public List<String> getBorrowedByBookByUserId(int userId) {
        User user = getUserById(userId);
        return user.getBorrowedBook().stream()
                .map(borrowedBook -> borrowedBook.getBook().getTitle())
                .toList();
    }

    private User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User doesn't found with id: " + userId
                        , HttpStatus.NOT_FOUND));
    }
}
