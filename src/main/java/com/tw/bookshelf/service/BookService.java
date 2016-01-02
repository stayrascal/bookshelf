package com.tw.bookshelf.service;


import com.tw.bookshelf.entity.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Iterable<Book> findAll();

    Book getBookByIsbn(String isbn);

    Book create(Book book);

    Book save(Book book);

    Book delete(String isbn);

    Iterable<Book> findByCategoryName(String name);

    Iterable<Book> finByTitle(String title);

    Iterable<Book> findBooksByFuzzyTitle(String title);

    Iterable<Book> findBooksOrderByPrice();

    List<Book> findBooks(Pageable pageable);

    Long getBookSize();
}
