package com.rmkj.microcap.common.bean;

/**
 * Created by renwp on 2016/12/30.
 */
public class GridPagerExt {

    private Object extObj;

    private GridPager gridPager;

    public GridPagerExt(Object extObj, GridPager gridPager) {
        this.extObj = extObj;
        this.gridPager = gridPager;
    }

    public Object getExtObj() {
        return extObj;
    }

    public void setExtObj(Object extObj) {
        this.extObj = extObj;
    }

    public GridPager getGridPager() {
        return gridPager;
    }

    public void setGridPager(GridPager gridPager) {
        this.gridPager = gridPager;
    }

}
