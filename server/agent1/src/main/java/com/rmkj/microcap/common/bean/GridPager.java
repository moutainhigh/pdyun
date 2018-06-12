package com.rmkj.microcap.common.bean;

import java.util.List;


/**
 * 分页实体
 */
public class GridPager implements java.io.Serializable {

    private int total = 0;
    private List rows;
    private List footer;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public List getFooter() {
        return footer;
    }

    public void setFooter(List footer) {
        this.footer = footer;
    }

}
