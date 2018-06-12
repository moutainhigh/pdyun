package com.rmkj.microcap.common.bean;


import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 请求结果返回类
 *
 */
public class ExecuteResult<T> {
    private int error_code;
    private Map<String,Object> data;

    public ExecuteResult(StatusCode code, Map data) {
        System.out.println("code="+code+","+(code == StatusCode.OK ));
        this.error_code = code.getValue();
        if(code != StatusCode.OK && StringUtils.isEmpty(data.get("error"))){
            data.put("error",code.getDescription());
        }
        this.data = data;
    }

    public ExecuteResult(int code, Map data) {
        this.error_code = code;
        this.data = data;
    }

    public ExecuteResult() {
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
