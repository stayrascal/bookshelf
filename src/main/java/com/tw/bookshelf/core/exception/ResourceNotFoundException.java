package com.tw.bookshelf.core.exception;


import org.springframework.http.HttpStatus;

public abstract class ResourceNotFoundException extends ResourceException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getError() {
        return HttpStatus.NOT_FOUND.getReasonPhrase();
    }

}
