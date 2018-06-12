package com.rmkj.microcap.common.modules.pay.huanxun.service;/**
 * Created by Administrator on 2018/5/2.
 */

import com.rmkj.microcap.common.modules.pay.NotifyInterface;
import com.rmkj.microcap.common.modules.pay.huanxun.entity.HuanXunMoneyVO;
import com.rmkj.microcap.common.modules.pay.huanxun.utils.ThreeDES;
import com.rmkj.microcap.common.modules.pay.yizhifu.utils.XStreamTool;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.user.dao.UserDao;
import com.rmkj.microcap.modules.user.entity.BankCard;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author k
 * @create -05-02-15:58
 **/
@Service
public class HuanXunPayService {
    private final Logger LOG = Logger.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    @Value("${huanxun_pay_trade_no}")
    private String huanxun_pay_trade_no;
    @Value("${huanxun_pay_mer_no}")
    private String huanxun_pay_mer_no;
    @Value("${huanxun_pay_key}")
    private String huanxun_pay_key;
    @Value("${huanxun_pay_notify_url}")
    private String huanxun_pay_notify_url;

    public String buildParameter(MoneyRecord moneyRecord, HuanXunMoneyVO huanXunMoneyVO){
        BankCard bankCard = userDao.findBankCard(AuthContext.getUserId());
        Map<String, Object> ips = new HashedMap();
        Map<String, Object> gateWayReq = new HashedMap();
        LinkedHashMap<String, Object> head = new LinkedHashMap();
        LinkedHashMap<String, Object> body = new LinkedHashMap();
        try{
            //请求头
            head.put("Version", "v1.0.0");
            head.put("MerCode", huanxun_pay_mer_no);
            head.put("MerName", "");
            head.put("Account", huanxun_pay_trade_no);
            head.put("MsgId", "");
            head.put("ReqDate", DateUtils.getDate("yyyyMMddHHmmss"));
            //订单支付接口表单参数
            body.put("MerBillNo", moneyRecord.getSerialNo());
            body.put("Lang", "GB");
            body.put("Amount ", moneyRecord.getMoney().toString());
            body.put("Date ", DateUtils.getDate("yyyyMMdd"));
            body.put("CurrencyType ", "156");
            body.put("GatewayType ", "01");
            body.put("Merchanturl", huanxun_pay_notify_url); //支付结果成功返回的商户URL
            body.put("FailUrl", huanxun_pay_notify_url); //支付结果失败返回的商户URL
            body.put("Attach", moneyRecord.getSerialNo());
            body.put("OrderEncodeType", "5");
            body.put("RetEncodeType", "17");
            body.put("RetType", "1");
            body.put("ServerUrl", huanxun_pay_notify_url);
            body.put("BillEXP", "24");
            body.put("GoodsName", "PindoCloud");
            body.put("IsCredit", "1");
            body.put("BankCode", huanXunMoneyVO.getBankType());
            body.put("ProductType", "1");
            //客户信息
            body.put("UserRealName", huanXunMoneyVO.getChnName()); //姓名
            body.put("UserId", AuthContext.getUserId());
            String userInfo = huanXunMoneyVO.getMobile().concat("|").concat(huanXunMoneyVO.getIdCard()).concat("|").concat(huanXunMoneyVO.getBankAccount());
            body.put("CardInfo", ThreeDES.encryptDESCBC(userInfo, huanxun_pay_key)); //持卡人信息*/

            String bodyStr = mapToXmlBodyStr(body);
            LOG.info("body:" + bodyStr);
            String signStr = bodyStr.concat(huanxun_pay_mer_no.concat(huanxun_pay_key));
            LOG.info("环迅签名原始Body字符串:".concat(signStr));
            head.put("Signature", DigestUtils.md5Hex(signStr));
            String headStr = mapToXmlHeadStr(head);
            LOG.info("head:" + headStr);
            StringBuffer xmlStr = new StringBuffer();
            xmlStr.append("<Ips>");
            xmlStr.append("<GateWayReq>");
            xmlStr.append(headStr);
            xmlStr.append(bodyStr);
            xmlStr.append("</GateWayReq>");
            xmlStr.append("</Ips>");
            LOG.info("环迅请求支付报文:".concat(xmlStr.toString()));
            //String str = "<Ips><GateWayReq><head><Version>v1.0.0</Version><MerCode>204156</MerCode><MerName></MerName><Account>2041560018</Account><MsgId>msg20180511141342</MsgId><ReqDate>20180511141342</ReqDate><Signature>60ffe596c36b7e8121640c6768fa3d66</Signature></head><body><MerBillNo>20180511141320100</MerBillNo><Lang>GB</Lang><Amount>2.02</Amount><Date>20160818</Date><CurrencyType>156</CurrencyType><GatewayType>01</GatewayType><Merchanturl>http://192.168.12.110:8080/payment-demo/merchant/success.html</Merchanturl><FailUrl><![CDATA[https://www.ips.com.cn/ipay/Default.aspx]]></FailUrl><Attach><![CDATA[this is merchant attach!]]></Attach><OrderEncodeType>5</OrderEncodeType><RetEncodeType>17</RetEncodeType><RetType>1</RetType><ServerUrl><![CDATA[http://192.168.12.110:8080/payment-demo/merchant/s2surl.html]]></ServerUrl><BillEXP>96</BillEXP><GoodsName>三星Galaxy S4</GoodsName><IsCredit>2</IsCredit><BankCode>1100</BankCode><ProductType>1</ProductType></body></GateWayReq></Ips>";
            return xmlStr.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 返回body
     * @param map
     * @return
     */
    public String mapToXmlBodyStr(LinkedHashMap<String, Object> map){
        StringBuffer sb = new StringBuffer();
        sb.append("<body>");
        Set<String> set = map.keySet();
        List<String> keyList = new ArrayList(set);
        for (String key : keyList){
            sb.append("<").append(key).append(">");
            if("Merchanturl".equals(key) || "FailUrl".equals(key) || "Attach".equals(key) || "ServerUrl".equals(key)){
                sb.append("<![CDATA[");
                sb.append(map.get(key));
                sb.append("]]>");
            }else {
                sb.append(map.get(key));
            }
            sb.append("</").append(key).append(">");
        }
        sb.append("</body>");
        return sb.toString();
    }

    /**
     * 返回head
     * @param map
     * @return
     */
    public String mapToXmlHeadStr(LinkedHashMap<String, Object> map){
        StringBuffer sb = new StringBuffer();
        sb.append("<head>");
        Set<String> set = map.keySet();
        List<String> keyList = new ArrayList(set);
        for (String key : keyList){
            sb.append("<").append(key).append(">");
            sb.append(map.get(key));
            sb.append("</").append(key).append(">");
        }
        sb.append("</head>");
        return sb.toString();
    }

    @Autowired
    NotifyInterface notifyInterface;

    public ResponseEntity callback(String xmlStr){
        LOG.info("环迅支付异步通知:".concat(xmlStr));
        //验签
        String sign = getSign(xmlStr);
        String retEncodeType =  getRetEncodeType(xmlStr); //加密方式
        if(retEncodeType.equals("17")){
            //获取body
            String body = subBodyByxmlStr(xmlStr);
            String localSign = DigestUtils.md5Hex(body.concat(huanxun_pay_mer_no).concat(huanxun_pay_key));
            if(localSign.equals(sign)){
                if(getRspCode(xmlStr).equals("000000")){
                    //本地流水号
                    String merBillNo = "MerBillNo";
                    String merBillNoValue = getValueByXmlKey(xmlStr, merBillNo, merBillNo.length() + 2);
                    //环迅流水号
                    String ipsBillNo = "IpsBillNo";
                    String ipsBillNoValue = getValueByXmlKey(xmlStr, ipsBillNo, ipsBillNo.length() + 2);
                    notifyInterface.success(merBillNoValue, ipsBillNoValue);
                    return ResponseEntity.ok("success");
                }
            }else{
                LOG.info("环迅支付验签失败(原-本地):".concat(sign).concat("---").concat(localSign));
            }
        }
        return ResponseEntity.ok("failure");
    }

    /**
     * 根据xml中的key 截取包含的value
     * @param xmlStr
     * @param key
     * @param length
     * @return
     */
    public String getValueByXmlKey(String xmlStr, String key, int length){
        int index = xmlStr.indexOf("<" + key + ">");
        int last = xmlStr.indexOf("</" + key + ">");
        String value = null;
        if(index != -1){
            value = xmlStr.substring(index + length, last);
        }
        return value;
    }

    /**
     * 截取报文信息中的body
     * @param xmlStr
     * @return
     */
    public String subBodyByxmlStr(String xmlStr){
        int index = xmlStr.indexOf("<body>");
        int last = xmlStr.lastIndexOf("</body>");
        String body = null;
        if(index > 0){
            body = xmlStr.substring(index, last + 7);
        }
        return body;
    }

    /**
     * 获取报文中<Signature></Signature>部分
     * @param xml
     * @return
     */
    public String getSign(String xml) {
        int s_index = xml.indexOf("<Signature>");
        int e_index = xml.indexOf("</Signature>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 11, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<RetEncodeType></RetEncodeType>部分
     * @param xml
     * @return
     */
    public String getRetEncodeType(String xml) {
        int s_index = xml.indexOf("<RetEncodeType>");
        int e_index = xml.indexOf("</RetEncodeType>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 15, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<RspCode></RspCode>部分
     * @param xml
     * @return
     */
    public String getRspCode(String xml) {
        int s_index = xml.indexOf("<RspCode>");
        int e_index = xml.indexOf("</RspCode>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 9, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<Status></Status>部分
     * @param xml
     * @return
     */
    public String getStatus(String xml) {
        int s_index = xml.indexOf("<Status>");
        int e_index = xml.indexOf("</Status>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 8, e_index);
        }
        return sign;
    }
}
