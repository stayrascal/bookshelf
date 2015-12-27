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

    /*public Map<Integer, TableCoulumEntity> parseColum(HttpServletRequest request){
        Map<String, Object> columnParams = WebUtils.getParametersStartingWith(request, "columns");
        Map<Integer, Map<String, String>> groupParams = new HashMap<Integer, Map<String, String>>();
        Map<Integer, TableCoulumEntity> columEntityMap = new HashMap<Integer, TableCoulumEntity>();
        Set<Integer> paramsIndexSet = new HashSet<Integer>();

        for (Map.Entry<String, Object> entry : columnParams.entrySet()){
            Integer paramIndex = getColumnPAramIndex(entry.getKey());
            if (Objects.isNull(paramIndex)){
                continue;
            }
            paramsIndexSet.add(paramIndex);
        }

        for (Integer paramsIndex : paramsIndexSet){
            Map<String, String> groupMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : columnParams.entrySet()){
                Integer paramIndex = getColumnParamIndex(entry.getKey());
                if (Objects.isNull(paramIndex)){
                    continue;
                }
                if (paramsIndex.equals(paramIndex)){
                    groupMap.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            groupParams.put(paramsIndex, groupMap);
        }

        for (Map.Entry<Integer, Map<String, String>> entry : groupParams.entrySet()){
            Map<String, String> paramMap = entry.getValue();
            TableCoulumEntity tableCoulumEntity = new TableCoulumEntity();
            tableCoulumEntity.setName(paramMap.get());
        }

    }*/
}
