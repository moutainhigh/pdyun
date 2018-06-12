package com.rmkj.microcap.common.modules.money.out.weifutong.bean.pay;

import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.AipgReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.AipgRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.XSUtil;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.XStreamEx;
import com.thoughtworks.xstream.XStream;

public class PGPayUtil
{
	private static XStream xsreq=buildXS(true);
	private static XStream xsrsp=buildXS(false);
	public static XStream buildXS(boolean isreq)
	{
		XStream xs=new XStreamEx();
		if(isreq)
		{
			xs.alias("AIPG",AipgReq.class);
			xs.addImplicitCollection(AipgReq.class,"trxData");
			xs.alias("BODY", PGPayReq.class);
			xs.aliasField("TRANS_DETAILS", PGPayReq.class, "details");
			xs.alias("TRANS_DETAIL", PGPayReqRecord.class);
			
		}
		else
		{
			xs.alias("AIPG", AipgRsp.class);
			xs.addImplicitCollection(AipgRsp.class,"trxData");
			xs.alias("BODY",PGPayRsp.class);
			xs.aliasField("RET_DETAILS", PGPayRsp.class, "details");
			xs.alias("RET_DETAIL",PGPayRspRecord.class);
		}
		return xs;
	}
	public static AipgReq parseReq(String xml)
	{
		return (AipgReq) xsreq.fromXML(xml);
	}
	public static AipgRsp parseRsp(String xml)
	{
		return (AipgRsp) xsrsp.fromXML(xml);
	}
	public static String toXml(Object o)
	{
		boolean isreq=(o instanceof AipgReq);
		if(isreq) return XSUtil.toXml(xsreq, o);
		else return XSUtil.toXml(xsrsp, o);
	}	
}
