package com.rmkj.microcap.common.modules.weixin.bean;

/**
 * Created by renwp on 2016/12/24.
 */
public class QueryMediasBean {
    private String type;
    private String offset;
    private String count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
