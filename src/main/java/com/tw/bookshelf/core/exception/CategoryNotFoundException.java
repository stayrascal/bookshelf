package com.tw.bookshelf.core.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends ResourceNotFoundException {

    private final String name;

    public CategoryNotFoundException(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getError() {
        return String.format("Category %s", HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{name};
    }
}
