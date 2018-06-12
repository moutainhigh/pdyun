package com.rmkj.microcap.common.modules.sys.bean;

import com.rmkj.microcap.common.bean.DataEntity;

/**
* Created by Administrator on 2016-11-15.
*/
public class Ml3AgentRoleBean extends DataEntity {
    //用户编号
    private String agentId;
    //角色编号
    private String roleId;
	public Ml3AgentRoleBean(String agentId, String roleId) {
		this.agentId = agentId;
		this.roleId = roleId;
	}

	public Ml3AgentRoleBean() {
	}
	public void setAgentId(String agentId){
		this.agentId=agentId;
	}
	public String getAgentId(){
		return this.agentId;
	}
	public void setRoleId(String roleId){
		this.roleId=roleId;
	}
	public String getRoleId(){
		return this.roleId;
	}

}
