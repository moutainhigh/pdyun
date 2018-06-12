package com.rmkj.microcap.common.modules.money.out.weifutong.bean.acquery;

import java.util.ArrayList;
import java.util.List;

public class AcQueryRep {
	private List details = new ArrayList();

	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	public void addDtl(AcNode dtl)
	{
		if(details==null) details=new ArrayList();
		details.add(dtl);
	}
}
