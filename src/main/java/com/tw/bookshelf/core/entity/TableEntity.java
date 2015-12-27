package com.tw.bookshelf.core.entity;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TableEntity implements Serializable {

    private static final long serialVersionUID = -6723955523317035219L;

    private Integer draw;
    private Integer length = 10;
    private Integer start = 0;
    private Map<String, String> search = new HashMap<String, String>();
    private Map<Integer, TableCoulumEntity> coulumMap = new HashMap<Integer, TableCoulumEntity>();
    private Map<String, String>[] order;

    public Integer getNumPage() {
        Integer numPage = (start == 0) ? 1 : (start / length) + 1;
        return numPage - 1;
    }

    public TableEntity(Integer draw, Integer length, Integer start, Map<String, String> search, Map<Integer, TableCoulumEntity> coulumMap, Map<String, String>[] order) {
        this.draw = draw;
        this.length = length;
        this.start = start;
        this.search = search;
        this.coulumMap = coulumMap;
        this.order = order;
    }

    public TableEntity() {
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Map<String, String> getSearch() {
        return search;
    }

    public void setSearch(Map<String, String> search) {
        this.search = search;
    }

    public Map<Integer, TableCoulumEntity> getCoulumMap() {
        return coulumMap;
    }

    public void setCoulumMap(Map<Integer, TableCoulumEntity> coulumMap) {
        this.coulumMap = coulumMap;
    }

    public Map<String, String>[] getOrder() {
        return order;
    }

    public void setOrder(Map<String, String>[] order) {
        this.order = order;
    }
}
