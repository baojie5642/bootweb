package com.baojie.admin.dto;

import java.io.Serializable;
import java.util.List;

public class PageBean implements Serializable {

    private static final long serialVersionUID = 5412400795147948379L;
    // 当前页的当前数据量
    private long total;
    // 当前页记录
    private List rows;

    public PageBean(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public List getRows() {
        return rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
