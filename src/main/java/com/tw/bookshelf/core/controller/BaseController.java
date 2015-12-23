package com.tw.bookshelf.core.controller;

import com.tw.bookshelf.core.exception.ResourceException;
import com.tw.bookshelf.core.exception.ResourceIsExistException;
import com.tw.bookshelf.core.exception.ResourceNotFoundException;
import com.tw.bookshelf.core.util.MessageSourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class BaseController {

    private MessageSourceAccessor messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> resourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getBodyMap(exception, locale));
    }

    @ExceptionHandler(ResourceIsExistException.class)
    public ResponseEntity<Map<String, Object>> resourceIsExistException(ResourceIsExistException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(getBodyMap(exception, locale));
    }

    private Map<String, Object> getBodyMap(ResourceException exception, Locale locale) {
        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("status", exception.getStatus());
        body.put("error", exception.getError());
        body.put("message", messageSource.getMessage(exception.getCode(), exception.getArgs(), locale).orElse("No message available"));
        return body;
    }
}
