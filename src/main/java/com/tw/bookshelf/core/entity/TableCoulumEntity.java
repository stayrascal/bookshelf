package com.tw.bookshelf.core.entity;


import java.io.Serializable;

public class TableCoulumEntity implements Serializable{

    private static final long serialVersionUID = 5542072297955491187L;

    private String data;
    private String name;
    private Boolean searchable;
    private Boolean orderable;
    private String searchValue;
    private Boolean searchRegex;

    public TableCoulumEntity(String data, String name, Boolean searchable, Boolean orderable, String searchValue, Boolean searchRegex) {
        this.data = data;
        this.name = name;
        this.searchable = searchable;
        this.orderable = orderable;
        this.searchValue = searchValue;
        this.searchRegex = searchRegex;
    }

    public TableCoulumEntity() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getOrderable() {
        return orderable;
    }

    public void setOrderable(Boolean orderable) {
        this.orderable = orderable;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Boolean getSearchRegex() {
        return searchRegex;
    }

    public void setSearchRegex(Boolean searchRegex) {
        this.searchRegex = searchRegex;
    }
}
