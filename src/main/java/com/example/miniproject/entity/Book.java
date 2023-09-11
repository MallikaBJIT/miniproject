package com.example.miniproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_table")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    private boolean available = true;
    private boolean isDeleted = false;
    private Double rating = 0.0;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> review;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private BorrowedBook borrowedBook;

    @OneToMany(mappedBy = "book", orphanRemoval = false)
    private List<UserHistory> userHistories = new ArrayList<>();
}
