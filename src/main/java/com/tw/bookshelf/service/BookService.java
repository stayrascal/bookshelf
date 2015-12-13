package com.tw.bookshelf.service;


import com.tw.bookshelf.entity.Book;

import java.util.Iterator;

public interface BookService {
    Iterable<Book> findAll();


    Book getBookByIsbn(String isbn);

    void saveBook(Book book);

    void delete(String isbn);
}
