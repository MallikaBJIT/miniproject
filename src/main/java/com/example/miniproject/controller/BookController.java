package com.example.miniproject.controller;

import com.example.miniproject.dto.BookDTO;
import com.example.miniproject.dto.BookUpdateDto;
import com.example.miniproject.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BookController {
    private BookService bookService;

    @PostMapping("/books/create")
    public ResponseEntity<?> add(@RequestBody BookDTO bookDTO) {
        bookService.createBook(bookDTO);
        return ResponseEntity.ok("book is added");
    }

    @PutMapping("/books/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody BookUpdateDto bookUpdateDto) {
        bookService.updateBook(id, bookUpdateDto);
        return ResponseEntity.ok("book is updated");
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("book is deleted successfully");
    }

    @PostMapping("/books/all")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
