package com.tw.bookshelf.core.entity;


import java.util.List;

public class DataTableEntity {
    private Integer draw;
    private List<Object> data;
    private Long recordsFiltered;
    private Long recordsTotal;

    public DataTableEntity(Integer draw, List<Object> data, Long recordsFiltered, Long recordsTotal) {
        this.draw = draw;
        this.data = data;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
    }

    public DataTableEntity() {
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }
}
