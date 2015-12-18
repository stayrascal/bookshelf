package com.tw.bookshelf.service.impl;

import com.tw.bookshelf.core.exception.BookNotFoundException;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.repository.BookRepository;
import com.tw.bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    public Iterable<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = repository.findBookByIsbn(isbn);
        if (Objects.isNull(book)){
            throw new BookNotFoundException(isbn);
        }
        return book;
    }

    @Override
    public Book create(Book book) {
        return repository.saveBook(book);
    }

    @Override
    public Book save(Book book) {
        if (Objects.isNull(getBookByIsbn(book.getIsbn()))){
            throw new BookNotFoundException(book.getIsbn());
        }
        return repository.saveBook(book);
    }

    @Override
    public Book delete(String isbn) {
        Book book = getBookByIsbn(isbn);
        repository.remove(book);
        return book;
    }
}
