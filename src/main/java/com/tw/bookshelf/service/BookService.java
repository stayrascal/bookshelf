package com.tw.bookshelf.service;


import com.tw.bookshelf.entity.Book;

import java.util.Iterator;

public interface BookService {
    Iterable<Book> findAll();


    Book getBookByIsbn(String isbn);

    Book saveBook(Book book);

    void delete(String isbn);
}
