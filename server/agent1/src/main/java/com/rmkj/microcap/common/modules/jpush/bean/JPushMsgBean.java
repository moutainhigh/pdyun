package com.rmkj.microcap.common.modules.jpush.bean;

import java.util.Map;

/**
 * Created by zhangbowen on 2015/9/6.
 */
public class JPushMsgBean {
    private String[] alias;
    private String title;
    private String content;
    //附带参数
    private Map<String, String> params;
    private String[] regIds;
    //发送类别(1.alias 2.regIds)
    private int sendType;
    //消息类别(1.通知 2.消息)
    private int msgType;
    //重试
    private int maxRetryTimes = 3;

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public String[] getRegIds() {
        return regIds;
    }

    public void setRegIds(String[] regIds) {
        this.regIds = regIds;
    }

    public String[] getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
