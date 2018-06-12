package com.rmkj.microcap.common.modules.money.out.weifutong;


import com.rmkj.microcap.common.modules.money.out.weifutong.bean.accttrans.AcctTransferReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.acctvalid.ValbSum;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.acctvalid.ValidBReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.acctvalid.VbDetail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.acquery.AcNode;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.acquery.AcQueryRep;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.acquery.AcQueryReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.agrmqx.XQSignReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.agrmsync.SignInfoDetail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.agrmsync.SignInfoSync;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.ahquery.AHQueryRep;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.ahquery.AHQueryReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.ahquery.BalNode;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.cash.CashRep;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.cash.CashReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.AipgReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.AipgRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.InfoReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.XSUtil;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.downloadrsp.DownRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.etdtlquery.EtQReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.etquery.EtNode;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.etquery.EtQueryRep;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.etquery.EtQueryReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.idverify.IdVer;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.loginrsp.LoginRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.notify.Notify;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.payreq.Body;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.payreq.Trans_Detail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.pos.*;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.qtd.QTDReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.qtd.QTDRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.qtd.QTDRspDetail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.qvd.QVDReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.refund.Refund;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.rev.TransRev;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.rev.TransRevRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.rnp.*;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.rtreq.Trans;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.rtrsp.TransRet;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.signquery.NSignReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.signquery.QSignDetail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.signquery.QSignReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.signquery.QSignRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.singleacctvalid.ValidR;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.syncex.SyncReqEx;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.syncex.SyncReqExDetail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.syncex.SyncRspEx;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.syncex.SyncRspExDetail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.synreq.*;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.transfer.TransferReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.transquery.*;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.tunotify.TUNotifyRep;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.tunotify.TUNotifyReq;
import com.thoughtworks.xstream.XStream;

public class XSUtilEx
{
	private static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	public static AipgReq makeNotify(String qsn)
	{
		AipgReq req=new AipgReq();
		Notify notify=new Notify();
		notify.setNOTIFY_SN(qsn);
		req.setINFO(XSUtilEx.makeReq("200003",""+System.currentTimeMillis()));
		req.addTrx(notify);
		return req;
	}
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String status)
	{
		return makeNSignReq(agrno,contractno,acct,status,"1");
	}
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String status,String signtype)
	{
		AipgReq req=new AipgReq();
		QSignDetail qsd=new QSignDetail();
		qsd.setAGREEMENTNO(agrno);
		qsd.setACCT(acct);
		qsd.setCONTRACTNO(contractno);
		qsd.setSTATUS(status);
		//qsd.setSIGNTYPE(signtype);
		NSignReq nsr=new NSignReq();
		nsr.addDtl(qsd);
		req.setINFO(XSUtilEx.makeReq("210003",""+System.currentTimeMillis()));
		req.addTrx(nsr);
		return req;		
	}
	public static AipgReq xmlReq(String xmlMsg)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, true);
		AipgReq req=(AipgReq) xs.fromXML(xmlMsg);
		return req;
	}
	public static AipgRsp xmlRsp(String xmlMsg)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, false);
		AipgRsp rsp=(AipgRsp) xs.fromXML(xmlMsg);
		return rsp;
	}
	public static String reqXml(AipgReq req)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, true);
		String xml=HEAD+xs.toXML(req);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static String rspXml(AipgRsp rsp)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, false);
		String xml=HEAD+xs.toXML(rsp);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static void initXStream(XStream xs,boolean isreq)
	{
		if(isreq) 
			xs.alias("AIPG", AipgReq.class); 
		else 
			xs.alias("AIPG", AipgRsp.class);
	/*	xs.alias("AIPG", FtpXml.class);
		
		xs.alias("IN", InFTP.class);*/
		
		
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("BODY", Body.class) ;
		xs.alias("TRANS_DETAIL", Trans_Detail.class);
		xs.alias("TRANS_DETAIL", Trans_Detail.class);
		xs.alias("IDVER", IdVer.class);

		xs.alias("VALIDBREQ", ValidBReq.class) ;
		xs.alias("VALBSUM", ValbSum.class) ;
		xs.alias("VBDETAIL", VbDetail.class);
		xs.aliasField("VALIDBD", ValidBReq.class, "VALIDBD");
		xs.alias("VALIDR", ValidR.class) ;

		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QVDREQ", QVDReq.class);
		xs.alias("QTRANSRSP", QTransRsp.class);
		xs.alias("QTDETAIL", QTDetail.class);
		xs.alias("DOWNRSP", DownRsp.class);
		xs.alias("NOTIFY", Notify.class);
		xs.alias("SYNC", Sync.class);
		xs.alias("QSIGNRSP", QSignRsp.class);
		xs.alias("QSDETAIL", QSignDetail.class);
		xs.alias("NSIGNREQ", NSignReq.class);
		xs.alias("QSIGNREQ", QSignReq.class);
		xs.alias("TRANS", Trans.class);
		xs.alias("TRANSRET", TransRet.class);
		xs.alias("REVREQ", TransRev.class);
		xs.alias("REVRSP", TransRevRsp.class);
		xs.alias("LOGINRSP", LoginRsp.class);
		xs.alias("BALREQ", BalReq.class);
		xs.alias("BALRET", BalRet.class);
		xs.alias("SVRFREQ", SvrfReq.class);
		xs.alias("SCLOSEREQ", SvrfReq.class);
		xs.alias("SCLOSERSP", SCloseRsp.class);
		xs.alias("PINSIGNREQ", PinVerifyReq.class);
		xs.alias("PINSIGNRSP", PinVerifyRsp.class);

		//退款
		xs.alias("REFUND", Refund.class);
		xs.alias("ACCTTRANSFERREQ", AcctTransferReq.class);
		xs.alias("RNPA", Rnpa.class);
		xs.alias("RNPR", Rnpr.class);
		xs.alias("RNPC", Rnpc.class);
		xs.alias("RNPARET", RnpaRet.class);
		xs.alias("RNP", Rnp.class);

		xs.alias("TRFER", Trfer.class);
		xs.alias("TRFRET", Trfret.class);
		xs.alias("QPTRF", QPTrf.class);
		xs.alias("QPTRFRET", QPTrfret.class);
		xs.alias("QPTRANS", QPTrans.class);
		xs.alias("QPTRANSRET", QPTransRet.class);

		xs.addImplicitCollection(Sync.class, "details");
		xs.addImplicitCollection(QTransRsp.class, "details");
		xs.addImplicitCollection(QSignRsp.class, "details");
		xs.alias("SYNCDETAIL", SyncDetail.class);

		xs.alias("SYNCREQEX", SyncReqEx.class);
		xs.alias("SYNCRSPEX", SyncRspEx.class);
		xs.alias("SYNCREQEXDETAIL", SyncReqExDetail.class);
		xs.alias("SYNCRSPEXDETAIL", SyncRspExDetail.class);
		xs.addImplicitCollection(SyncReqEx.class, "details");
		xs.addImplicitCollection(SyncRspEx.class, "details");
		xs.alias("QTDREQ", QTDReq.class);
		xs.alias("QTDRSP", QTDRsp.class);
		xs.alias("QTDRSPDETAIL", QTDRspDetail.class);
		xs.addImplicitCollection(QTDRsp.class, "details");
		xs.alias("ACQUERYREQ", AcQueryReq.class);
		xs.alias("ACQUERYREP", AcQueryRep.class);
		xs.alias("ACNODE", AcNode.class);
		xs.addImplicitCollection(AcQueryRep.class, "details");
		xs.alias("AHQUERYREQ", AHQueryReq.class);
		xs.alias("AHQUERYREP", AHQueryRep.class);
		xs.alias("BALNODE", BalNode.class);
		xs.addImplicitCollection(AHQueryRep.class, "details");
		xs.alias("TUNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREP", TUNotifyRep.class);
		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);
//		xs.alias("TUQNOTIFYREQ", NoticeReq.class);
//		xs.alias("TUNOTIFYREP", NoticeRep.class);
		xs.alias("ETQUERYREQ", EtQueryReq.class);
		xs.alias("ETQUERYREP", EtQueryRep.class);
		xs.alias("ETNODE", EtNode.class);
		xs.alias("ETQREQ", EtQReq.class);
		xs.addImplicitCollection(EtQueryRep.class, "details");
		xs.alias("SIGNINFODETAIL", SignInfoDetail.class);
		xs.alias("SIGNINFOSYNC", SignInfoSync.class);
		xs.alias("SCLOSEREQ", SCloseReq.class);
		xs.addImplicitCollection(SignInfoSync.class, "details");
		xs.alias("XQSIGNREQ", XQSignReq.class);

		/**
		 * 转账
		 */
		xs.alias("TRANSFERREQ",TransferReq.class);
	}
	public static InfoReq makeReq(String trxcod, String sn)
	{
		InfoReq ir=new InfoReq();
		ir.setTRX_CODE(trxcod);
		ir.setDATA_TYPE("2");
		ir.setVERSION("03");
		ir.setSIGNED_MSG("");
		ir.setREQ_SN(sn);
		ir.setLEVEL(null);
		ir.setUSER_NAME(null);
		ir.setUSER_PASS(null);
		return ir;
	}
	public static InfoReq makeReq(String trxcod, String sn,String user,String pass,int level)
	{
		InfoReq ir=new InfoReq();
		ir.setTRX_CODE(trxcod);
		ir.setDATA_TYPE("2");
		ir.setVERSION("03");
		ir.setSIGNED_MSG("");
		ir.setREQ_SN(sn);
		ir.setLEVEL(""+level);
		ir.setUSER_NAME(user);
		ir.setUSER_PASS(pass);
		return ir;
	}
	
	public static Object parseXml(String xml)
	{
		XStream xs=new XStreamIg();
		XSUtil.initXStream(xs,true);
		return xs.fromXML(xml);
	}
}
