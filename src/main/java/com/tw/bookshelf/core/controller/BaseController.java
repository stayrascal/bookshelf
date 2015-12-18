package com.tw.bookshelf.core.controller;

import com.tw.bookshelf.core.exception.ResourceException;
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

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<Map<String, Object>> resourceException(ResourceException exception, Locale locale) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", exception.getStatus());
        body.put("error", exception.getError());
        body.put("message", messageSource.getMessage(exception.getCode(), exception.getArgs(), locale).orElse("No message available"));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
