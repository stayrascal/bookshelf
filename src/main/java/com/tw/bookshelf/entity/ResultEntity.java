package com.tw.bookshelf.entity;

import java.util.HashMap;
import java.util.Map;

public class ResultEntity {
    private Integer status;
    private String message;
    private Map<String, Object> params = new HashMap<>();

    public ResultEntity putParams(String key, Object value){
        this.params.put(key, value);
        return this;
    }

    public ResultEntity(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultEntity(Integer status, String message, Map<String, Object> params) {
        this.status = status;
        this.message = message;
        this.params = params;
    }

    public ResultEntity() {
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
