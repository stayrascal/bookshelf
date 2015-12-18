package com.tw.bookshelf.service.impl;

import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.repository.BookRepository;
import com.tw.bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    public Iterable<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return repository.findOne(isbn);
    }

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public void delete(String isbn) {
        repository.delete(isbn);
    }
}
