package com.rmkj.microcap.common.modules.version.entity;


import com.rmkj.microcap.common.bean.DataEntity;

/**
 * Created by zhangbowen on 2015-12-25.
 */
public class VersionBean extends DataEntity {
    //名称
    private String title;
    //版本号
    private String version;
    //版本数
    private int code;
    //备注
    private String summary;
    //下载地址
    private String downloadUrl;
    //1.android 2.ios
    private String type;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
