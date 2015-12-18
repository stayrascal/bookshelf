package com.tw.bookshelf.controller;

import com.tw.bookshelf.core.exception.ResourceNotFoundException;
import com.tw.bookshelf.core.util.MessageSourceAccessor;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class BookShelfController {

    private final Logger logger = LoggerFactory.getLogger(BookShelfController.class);
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/book/get", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @RequestMapping(value = "/book/get/{isbn}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @RequestMapping(value = "/book/delete/{isbn}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> delete(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.delete(isbn));
    }

    @RequestMapping(value = "/book/add", method = RequestMethod.POST)
    public ResponseEntity<Book> add(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(book));
    }

    @RequestMapping(value = "/book/update", method = RequestMethod.PUT)
    public ResponseEntity<Book> update(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.save(book));
    }


    private MessageSourceAccessor messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> resourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", exception.getStatus());
        body.put("error", exception.getError());
        body.put("message", messageSource.getMessage(exception.getCode(), exception.getArgs(), locale).orElse("No message available"));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
