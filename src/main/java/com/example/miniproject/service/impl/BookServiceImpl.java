package com.example.miniproject.service.impl;

import com.example.miniproject.dto.BookDTO;
import com.example.miniproject.dto.BookResponseDTO;
import com.example.miniproject.dto.BookUpdateDto;
import com.example.miniproject.entity.Book;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.BookRepository;
import com.example.miniproject.service.BookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    @Override
    public void createBook(BookDTO bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        bookRepository.save(book);
    }

    @Override
    public void updateBook(int id, BookUpdateDto bookUpdateDto) {
        Book book = getBookById(id);
        book.setTitle(bookUpdateDto.getTitle());
        book.setBody(bookUpdateDto.getBody());
        book.setAvailable(bookUpdateDto.isAvailable());
        bookRepository.save(book);

    }

    @Override
    public void deleteBook(int id) {
        bookRepository.findById(id).orElseThrow(() ->
                new CustomException("Book not found with ID: " + id, HttpStatus.NOT_FOUND));
        bookRepository.deleteById(id);
    }

    private Book getBookById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException("Book not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream().map(book -> modelMapper.map(book, BookResponseDTO.class)).toList();
    }
}
