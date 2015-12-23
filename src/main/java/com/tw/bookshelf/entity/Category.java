package com.tw.bookshelf.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {

    @Id
    private String code;

    private String name;

    private String description;

    public Category(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Category() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
