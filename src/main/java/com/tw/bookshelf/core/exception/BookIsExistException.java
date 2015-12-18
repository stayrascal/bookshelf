package com.tw.bookshelf.core.exception;

import org.springframework.http.HttpStatus;

public class BookIsExistException extends ResourceIsExistException {

    private final String isbn;

    public BookIsExistException(final String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getError() {
        return String.format("Book %s", HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
