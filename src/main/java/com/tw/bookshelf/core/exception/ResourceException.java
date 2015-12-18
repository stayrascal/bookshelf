package com.tw.bookshelf.core.exception;


import org.springframework.http.HttpStatus;

public abstract class ResourceException extends RuntimeException {

    public abstract HttpStatus getStatus();

    public abstract String getError();

    public abstract Object[] getArgs();

    public String getCode() {
        return String.format("error.%s", getClass().getSimpleName());
    }
}
