package com.example.miniproject.controller;

import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.AuthenticationService;
import com.example.miniproject.service.ReservedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class ReservedBookController {
    @Autowired
    private ReservedBookService reservedBookService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{bookId}/user/{userId}/reserve")
    public ResponseEntity<?> reserveReservation(@PathVariable int bookId, @PathVariable int userId) {
        reservedBookService.bookReserve(bookId, userId);
        return ResponseHandler.generateResponse("Book is reserved", HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}/user/{userId}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable int bookId, @PathVariable int userId) {
        reservedBookService.cancelReservation(bookId, userId);
        return ResponseHandler.generateResponse("Reservation is cancel", HttpStatus.OK);
    }
}
