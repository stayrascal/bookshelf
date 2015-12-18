package com.tw.bookshelf.core.exception;


import org.springframework.http.HttpStatus;

public abstract class ResourceIsExistException extends ResourceException{

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getError() {
        return HttpStatus.FORBIDDEN.getReasonPhrase();
    }
}
