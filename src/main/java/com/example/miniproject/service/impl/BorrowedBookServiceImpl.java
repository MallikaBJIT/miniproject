package com.example.miniproject.service.impl;

import com.example.miniproject.dto.BorrowedBookDTO;
import com.example.miniproject.dto.BorrowedBookResponseDTO;
import com.example.miniproject.entity.Book;
import com.example.miniproject.entity.BorrowedBook;
import com.example.miniproject.entity.User;
import com.example.miniproject.entity.UserHistory;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.BookRepository;
import com.example.miniproject.repository.BorrowedBookRepository;
import com.example.miniproject.repository.UserHistoryRepository;
import com.example.miniproject.repository.UserRepository;
import com.example.miniproject.service.BorrowedBookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {
    private BorrowedBookRepository borrowedBookRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private UserHistoryRepository userHistoryRepository;
    private ModelMapper modelMapper;

    @Override
    public BorrowedBookResponseDTO borrowBook(int bookId, int userId, BorrowedBookDTO borrowedBookDTO) {
        Book book = getBook(bookId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Invalid User", HttpStatus.NOT_FOUND));
        if (!book.isAvailable()) {
            throw new CustomException("Book is not available for borrowed", HttpStatus.CONFLICT);
        }

        book.setAvailable(false);
        BorrowedBook borrowedBook = modelMapper.map(borrowedBookDTO, BorrowedBook.class);
        borrowedBook.setBook(book);
        borrowedBook.setUser(user);

        borrowedBookRepository.save(borrowedBook);
        bookRepository.save(book);

        //update user history table
        UserHistory userHistory = new UserHistory();
        userHistory.setBorrowedDate(borrowedBook.getBorrowedDate());
        userHistory.setDueDate(borrowedBook.getDueDate());
        userHistory.setUser(borrowedBook.getUser());
        userHistory.setBook(borrowedBook.getBook());
        userHistoryRepository.save(userHistory);

        return new BorrowedBookResponseDTO(
                book.getTitle(), user.getFirstName(), borrowedBookDTO.getBorrowedDate(), borrowedBookDTO.getDueDate()
        );
    }

    @Override
    public void returnBook(int bookId) {
        Book book = getBook(bookId);

        if (book.isAvailable()) {
            throw new CustomException("Book is available and not able to return", HttpStatus.BAD_REQUEST);
        }

        book.setAvailable(true);
        bookRepository.save(book);

        borrowedBookRepository.deleteByBookId(bookId);

        //update user history table
        UserHistory userHistory = userHistoryRepository.findLastByBookId(bookId);
        userHistory.setReturnDate(new Date());
        userHistoryRepository.save(userHistory);
    }

    private Book getBook(int bookId) {
        return bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new CustomException("Book doesn't exist", HttpStatus.NOT_FOUND));
    }
}
