package com.tw.bookshelf.core.exception;


import org.springframework.http.HttpStatus;

public abstract class ResourceIsExistException extends ResourceException{

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getError() {
        return HttpStatus.CONFLICT.getReasonPhrase();
    }
}
