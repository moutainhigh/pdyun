package com.rmkj.microcap.common.modules.money.out.weifutong.bean.pay;

import java.util.List;

@SuppressWarnings("rawtypes")
public class PGPayReq
{
	public PGPayReqSum getTRANS_SUM()
	{
		return TRANS_SUM;
	}
	public void setTRANS_SUM(PGPayReqSum tRANS_SUM)
	{
		TRANS_SUM = tRANS_SUM;
	}
	public List getDetails()
	{
		return details;
	}
	public void setDetails(List details)
	{
		this.details = details;
	}
	private PGPayReqSum TRANS_SUM;
	private List details;

}
