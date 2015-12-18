package com.tw.bookshelf.core.exception;


import org.springframework.http.HttpStatus;

public class BookNotFoundException extends ResourceNotFoundException {

    private final String isbn;

    public BookNotFoundException(final String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getError() {
        return "Book " + HttpStatus.NOT_FOUND.getReasonPhrase();
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{isbn};
    }
}
