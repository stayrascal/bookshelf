package com.tw.bookshelf.core.controller;


import com.tw.bookshelf.core.util.MessageSourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@RestController
public class ExceptionController implements ErrorController {

    private String errorPath;
    private ErrorAttributes errorAttributes;
    private MessageSource messageSource;
    private MessageSourceAccessor messageSourceAccessor;

    @Value("${error.path:/error}")
    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }

    @Autowired
    public void setErrorAttributes(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    @RequestMapping(value = "${error.path:/error}")
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request, Locale locale) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> body = errorAttributes.getErrorAttributes(requestAttributes, true);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (Objects.nonNull(body.get("status"))) {
            try {
                status = HttpStatus.valueOf((Integer) body.get("status"));
            } catch (Exception ignore) {
            }
        }

        String message = messageSource.getMessage("error." + status, null, null, locale);
        if (Objects.nonNull(message)) {
            body.put("message", message);
        }

        /*messageSourceAccessor.getMessage("error." + status, locale).ifPresent(msg->{
            body.put("message", msg);
        });*/
        return ResponseEntity.status(status).body(body);
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }
}
