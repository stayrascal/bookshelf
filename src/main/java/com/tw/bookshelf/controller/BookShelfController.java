package com.tw.bookshelf.controller;

import com.tw.bookshelf.core.controller.BaseController;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookShelfController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(BookShelfController.class);
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/book/get", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Book>> getAllBooks() {
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

    @RequestMapping(value = "/book/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Book>> getBooksByFuzzyName(@PathVariable String title) {
        return ResponseEntity.ok().body(bookService.findBooksByFuzzyTitle(title));
    }

    @RequestMapping(value = "/book/category/{name}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Book>> getBooksByCategoryCode(@PathVariable String name) {
        return ResponseEntity.ok().body(bookService.findByCategoryName(name));
    }

    @RequestMapping(value = "/book/order/price", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Book>> getBooksOrderByPriceDesc(){
        return ResponseEntity.ok().body(bookService.findBooksOrderByPrice());
    }

    @RequestMapping(value = "/book/get/{start}/{rows}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Book>> getBooks(@PathVariable int start, @PathVariable int rows){
        Pageable pageable = new PageRequest(start, rows);
        return ResponseEntity.ok().body(bookService.findBooks(pageable));
    }

    @RequestMapping(value = "/book/page", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> getBooks(@RequestBody PageRequest pageable){
        logger.debug("\n\n\n--------getBooks--------\n\n\n");
        return ResponseEntity.ok().body(bookService.findBooks(pageable));
    }

    @RequestMapping(value = "/book/page1", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Book>> getBookByPage(PageRequest pageRequest) {
        return ResponseEntity.ok().body(bookService.findBooks(pageRequest));
    }

    @RequestMapping(value = "/book/count", method = RequestMethod.GET)
    public ResponseEntity<Long> getBooksSize(){
        return  ResponseEntity.ok().body(bookService.getBookSize());
    }

}
