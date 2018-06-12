package com.rmkj.microcap.modules.weiXinMenu.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by renwp on 2016/12/24.
 */
public class MediaBean {
    private String name;
    private String update_time;
    private String media_id;
    private String url;

    private JSONObject content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getContent() {
        return content;
    }

    public void setContent(JSONObject content) {
        this.content = content;
    }
}
