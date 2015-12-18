package com.tw.bookshelf.service;


import com.tw.bookshelf.entity.Book;

public interface BookService {

    Iterable<Book> findAll();

    Book getBookByIsbn(String isbn);

    Book create(Book book);

    Book save(Book book);

    Book delete(String isbn);
}
