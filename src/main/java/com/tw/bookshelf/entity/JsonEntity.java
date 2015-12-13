package com.tw.bookshelf.entity;

import java.util.HashMap;
import java.util.Map;

public class JsonEntity {
    private Integer status;
    private String message;
    private Map<String, Object> params = new HashMap<String, Object>();

    public JsonEntity(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public JsonEntity putParam(String key, Object value){
        this.params.put(key, value);
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public static class Status{
        public static final Integer SUCCESS = 200;
        public static final Integer ERROR = 500;
    }
}
