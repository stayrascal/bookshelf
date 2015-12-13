package com.tw.bookshelf.controller;

import com.google.gson.Gson;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.entity.ResultEntity;
import com.tw.bookshelf.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookShelfController {

    private final Logger logger = LoggerFactory.getLogger(BookShelfController.class);
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/book/get", method = RequestMethod.GET)
    @ResponseBody
    public String getAllBooks(){
        Gson gson = new Gson();
        return gson.toJson(bookService.findAll());
    }

    @RequestMapping(value = "/book/get/{isbn}", method = RequestMethod.GET)
    @ResponseBody
    public ResultEntity getBookByIsbn(@PathVariable String isbn){
        ResultEntity resultEntity = new ResultEntity();
        Book book = bookService.getBookByIsbn(isbn);
        if (book != null){
            resultEntity.setStatus(ResultEntity.Status.SUCCESS);
            resultEntity.setMessage("ok");
            resultEntity.putParams("data", book);
        } else {
            resultEntity.setStatus(ResultEntity.Status.ERROR);
            resultEntity.setMessage("This book is not exist");
        }
        return resultEntity;
    }

    @RequestMapping(value = "/book/delete/{isbn}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResultEntity delete(@PathVariable String isbn){
        ResultEntity resultEntity = new ResultEntity();
        Book book = bookService.getBookByIsbn(isbn);
        if (book != null){
            bookService.delete(isbn);
            resultEntity.setStatus(ResultEntity.Status.SUCCESS);
            resultEntity.setMessage("ok");
        } else {
            resultEntity.setStatus(ResultEntity.Status.ERROR);
            resultEntity.setMessage("This book is not exist");
        }
        return resultEntity;
    }

    @RequestMapping(value = "/book/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity add(@RequestBody Book book){
        ResultEntity resultEntity = new ResultEntity();
        if (bookService.getBookByIsbn(book.getIsbn()) != null){
            resultEntity.setStatus(ResultEntity.Status.ERROR);
            resultEntity.setMessage("This book is exist");
        }else {
            bookService.saveBook(book);
            resultEntity.setStatus(ResultEntity.Status.SUCCESS);
            resultEntity.setMessage("ok");
        }
        return resultEntity;
    }

    @RequestMapping(value = "/book/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity update(@RequestBody Book book){
        ResultEntity resultEntity = new ResultEntity();
        if (bookService.getBookByIsbn(book.getIsbn()) != null){
            bookService.saveBook(book);
            resultEntity.setStatus(ResultEntity.Status.SUCCESS);
            resultEntity.setMessage("ok");
        } else {
            resultEntity.setStatus(ResultEntity.Status.ERROR);
            resultEntity.setMessage("This book is not exist");
        }
        return resultEntity;
    }
}
