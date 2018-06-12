package com.rmkj.microcap.test.weifutong;

import com.rmkj.microcap.common.modules.money.out.weifutong.XmlTools;

/**
 * 张广海
 * 签名和验签demo
 * 2012-11-09
 */
public class SendTest {

	/**
	 * @param args
	 * @throws Exception com.allinpay.demoSignTest.java
	 */
	public static void main(String[] args) throws Exception {
	    String url = "https://113.108.182.3/aipg/ProcessServlet";
		String message = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><AIPG><INFO><TRX_CODE>211006</TRX_CODE><VERSION>03</VERSION><DATA_TYPE>2</DATA_TYPE><LEVEL>5</LEVEL><USER_NAME>20060400000044502</USER_NAME><USER_PASS>`12qwe</USER_PASS><REQ_SN>00010020170321000000009</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><RNPA><MERCHANT_ID>200604000000445</MERCHANT_ID><ACCOUNT_TYPE>00</ACCOUNT_TYPE><ACCOUNT_NO>6214851211107193</ACCOUNT_NO><ACCOUNT_NAME>程统雄</ACCOUNT_NAME><ACCOUNT_PROP>0</ACCOUNT_PROP><ID_TYPE>0</ID_TYPE><ID>362330198802063076</ID><TEL>18321982804</TEL></RNPA></AIPG>";
		System.out.println(message);
		message = signMsg(message);
		System.out.println(XmlTools.send(url, message));

	}
	public static String signMsg(String xml) throws Exception{
		xml=XmlTools.signMsg(xml, "config\\20060400000044502.p12", "111111", false);
		return xml;
	}
	
	

}
