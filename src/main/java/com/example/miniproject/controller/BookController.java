package com.example.miniproject.controller;

import com.example.miniproject.dto.BookDTO;
import com.example.miniproject.dto.BookUpdateDto;
import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.AuthenticationService;
import com.example.miniproject.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {
    private BookService bookService;
    private AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid BookDTO bookDTO) {
        bookService.createBook(bookDTO);
        return ResponseHandler.generateResponse("Book is added", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody @Valid BookUpdateDto bookUpdateDto) {
        bookService.updateBook(id, bookUpdateDto);
        return ResponseHandler.generateResponse("book is updated", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseHandler.generateResponse("Book is deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> get() {
        return ResponseHandler.generateResponse("Book List", HttpStatus.OK, bookService.getAllBooks());
    }
}
