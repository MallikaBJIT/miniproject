package com.example.miniproject.controller;

import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable int userId) {
        return ResponseHandler.generateResponse("User details", HttpStatus.OK,
                userService.getUserDetails(userId));
    }

    @GetMapping("/{userId}/books")
    public ResponseEntity<?> getBookByUserId(@PathVariable int userId) {
        return ResponseHandler.generateResponse("User details", HttpStatus.OK,
                userService.getBookByUserId(userId));
    }

    @GetMapping("/{userId}/borrowed-books")
    public ResponseEntity<?> getBorrowedByBookByUserId(@PathVariable int userId) {
        return ResponseHandler.generateResponse("User details", HttpStatus.OK,
                userService.getBorrowedByBookByUserId(userId));
    }


}
