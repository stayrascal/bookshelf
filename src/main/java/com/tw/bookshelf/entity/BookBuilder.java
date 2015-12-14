package com.tw.bookshelf.entity;

public class BookBuilder {

    private Book book;

    public BookBuilder(){
        book = new Book();
    }

    public BookBuilder isbn(String isbn){
        book.setIsbn(isbn);
        return this;
    }

    public BookBuilder name(String name){
        book.setName(name);
        return this;
    }

    public BookBuilder author(String author){
        book.setAuthor(author);
        return this;
    }

    public BookBuilder price(Double price){
        book.setPrice(price);
        return this;
    }

    public Book build(){
        return book;
    }
}
