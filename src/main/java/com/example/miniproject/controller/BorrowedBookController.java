package com.example.miniproject.controller;

import com.example.miniproject.dto.BorrowedBookDTO;
import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.BorrowedBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BorrowedBookController {
    @Autowired
    private BorrowedBookService borrowedBookService;

    @PostMapping("/{bookId}/user/{userId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable int bookId, @PathVariable int userId
            , @RequestBody @Valid BorrowedBookDTO borrowedBookDTO) {
        return ResponseHandler.generateResponse("Borrowed book", HttpStatus.OK,
                borrowedBookService.borrowBook(bookId, userId, borrowedBookDTO));
    }

    @DeleteMapping("/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable int bookId) {
        borrowedBookService.returnBook(bookId);
        return ResponseHandler.generateResponse("Book is returned successfully", HttpStatus.OK);
    }

}
