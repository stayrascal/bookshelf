package com.tw.bookshelf.service.impl;

import com.tw.bookshelf.core.exception.BookIsExistException;
import com.tw.bookshelf.core.exception.BookNotFoundException;
import com.tw.bookshelf.core.exception.CategoryNotFoundException;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.entity.Category;
import com.tw.bookshelf.repository.BookRepository;
import com.tw.bookshelf.repository.CategoryRepository;
import com.tw.bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = repository.findOne(isbn);
        if (Objects.isNull(book)) {
            throw new BookNotFoundException(isbn);
        }
        return book;
    }

    @Override
    public Book create(Book book) {
        if (Objects.nonNull(repository.findOne(book.getIsbn()))) {
            throw new BookIsExistException(book.getIsbn());
        }
        return repository.save(book);
    }

    @Override
    public Book save(Book book) {
        if (Objects.isNull(getBookByIsbn(book.getIsbn()))) {
            throw new BookNotFoundException(book.getIsbn());
        }
        return repository.save(book);
    }

    @Override
    public Book delete(String isbn) {
        Book book = getBookByIsbn(isbn);
        repository.delete(book);
        return book;
    }

    @Override
    public Iterable<Book> findByCategoryName(final String name) {
        Category category = categoryRepository.findByName(name);
        Optional.ofNullable(category).orElseThrow(() -> new CategoryNotFoundException(name));
        return repository.findByCategoryCode(category.getCode());
    }

    @Override
    public Iterable<Book> finByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    public Iterable<Book> findBooksByFuzzyTitle(String title) {
        return repository.findByTitleContains(title);
    }
}
