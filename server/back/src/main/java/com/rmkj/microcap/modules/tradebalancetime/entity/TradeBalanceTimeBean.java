package com.rmkj.microcap.modules.tradebalancetime.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
* Created by Administrator on 2016-10-17.
*/
public class TradeBalanceTimeBean extends DataEntity {
    //开始时间
	private String openTime;
    //结束时间
    private String closeTime;

	public void setOpenTime(String openTime){
		this.openTime=openTime;
	}
	public String getOpenTime(){
		return this.openTime;
	}
	public void setCloseTime(String closeTime){
		this.closeTime=closeTime;
	}
	public String getCloseTime(){
		return this.closeTime;
	}

}
