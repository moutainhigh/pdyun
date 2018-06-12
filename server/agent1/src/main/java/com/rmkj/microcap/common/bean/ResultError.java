package com.rmkj.microcap.common.bean;

/**
 * Created by songwei on 2016/7/12.
 */
public class ResultError {
    private int code;
    private Object error;

    public ResultError(int code, Object error) {
        this.code = code;
        this.error = error;
    }

    public ResultError(Object error) {
        this.code = StatusCode.SERVER_ERROR.getValue();
        this.error = error;
    }

    public ResultError() {
    }

    public ResultError(StatusCode statusCode) {
        this.code = statusCode.getValue();
        this.error = statusCode.getDescription();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
