package com.rmkj.microcap.common.bean;


/**
 * 请求结果返回类
 *
 * @param <T>
 */
public class ExecuteResult<T> {
    private int code;
    private String msg;
    private T extra;
    /**
     * 分页查询起始位置
     */
    private Integer start;

    public ExecuteResult(StatusCode code, T result) {
        this.code = code.getValue();
        this.extra = result;
        this.msg = result.toString();
    }

    public ExecuteResult(T result) {
        this.extra = result;
    }

    public ExecuteResult() {
    }

    public ExecuteResult(StatusCode code) {
        this.code = code.getValue();
        this.msg = code.GetDescription();
    }
    public ExecuteResult(StatusCode code, String msg) {
        this.code = code.getValue();
        this.msg = msg;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
        this.extra = extra;
    }
}
