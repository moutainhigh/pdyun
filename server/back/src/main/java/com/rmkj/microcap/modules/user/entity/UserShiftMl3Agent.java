package com.rmkj.microcap.modules.user.entity;/**
 * Created by Administrator on 2017/5/11.
 */

import javax.validation.constraints.NotNull;

/**
 * @author k
 * @create -05-11-11:47
 **/

public class UserShiftMl3Agent {
    //用户id,可逗号拼接
    @NotNull(message = "用户信息错误")
    private String ids;
    //代理id
    @NotNull(message = "代理商信息错误")
    private String agentId;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
