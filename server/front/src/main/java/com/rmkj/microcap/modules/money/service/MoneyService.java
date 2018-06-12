package com.rmkj.microcap.modules.money.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.capinfo.crypt.Md5;
import com.capinfo.crypt.RSA_MD5;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.cache.CacheFacade;
import com.rmkj.microcap.common.modules.pay.huanxun.entity.HuanXunMoneyVO;
import com.rmkj.microcap.common.modules.pay.huanxun.service.HuanXunPayService;
import com.rmkj.microcap.common.modules.pay.mingfu.bean.MingFuQuickBean;
import com.rmkj.microcap.common.modules.pay.mingfu.service.MingFuPayService;
import com.rmkj.microcap.common.modules.pay.ronghe.service.RongHePayService;
import com.rmkj.microcap.common.modules.pay.rongya.service.RongYaPayService;
import com.rmkj.microcap.common.modules.pay.weifutong.WeiFuTongPayService;
import com.rmkj.microcap.common.modules.pay.weifutong.bean.PayResultReqBean;
import com.rmkj.microcap.common.modules.pay.weifutong.bean.PrePayRespBean;
import com.rmkj.microcap.common.modules.pay.yizhifu.bean.PayOrderResBean;
import com.rmkj.microcap.common.modules.pay.yizhifu.bean.PayRequestParamBean;
import com.rmkj.microcap.common.modules.pay.yizhifu.bean.UnionPayOrderReq;
import com.rmkj.microcap.common.modules.pay.yizhifu.bean.UnionPayRequestParam;
import com.rmkj.microcap.common.modules.pay.yizhifu.service.PayOrderService;
import com.rmkj.microcap.common.modules.pay.yizhifu.service.UnionPayOrderService;
import com.rmkj.microcap.common.modules.pay.zhinengcloud.entity.ZhiNengCloudPayBean;
import com.rmkj.microcap.common.modules.pay.zhinengcloud.service.ZhiNengCloudPayService;
import com.rmkj.microcap.common.modules.pay.zhongnan.entity.ZhongNanQuickPayBean;
import com.rmkj.microcap.common.modules.pay.zhongnan.service.ZhongNanPayService;
import com.rmkj.microcap.common.modules.weixin.bean.pay.NotifyReqBean;
import com.rmkj.microcap.common.modules.weixin.bean.pay.UnifiedOrderRespBean;
import com.rmkj.microcap.common.modules.weixin.face.NotifyForPayService;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinPayService;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.index.dao.ParameterSetDao;
import com.rmkj.microcap.modules.index.entity.ParameterSet;
import com.rmkj.microcap.modules.money.MoneyInHelper;
import com.rmkj.microcap.modules.money.bean.MoneyOutBean;
import com.rmkj.microcap.modules.money.bean.PrePayBean;
import com.rmkj.microcap.modules.money.dao.MoneyDao;
import com.rmkj.microcap.modules.money.entity.MoneyChange;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.money.entity.ReturnMoneyOut;
import com.rmkj.microcap.modules.user.dao.UserDao;
import com.rmkj.microcap.modules.user.dao.UserMessageDao;
import com.rmkj.microcap.modules.user.entity.BankCard;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.entity.UserMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by renwp on 2016/10/19.
 */
@Service
@Transactional
public class MoneyService implements NotifyForPayService {

    private static final Logger Log = Logger.getLogger(NotifyForPayService.class);

    @Autowired
    private MoneyDao moneyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMessageDao userMessageDao;

    @Autowired
    private ParameterSetDao parameterSetDao;

    @Autowired
    private WeiXinPayService weiXinPayService;

    @Autowired
    private WeiFuTongPayService weiFuTongPayService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private UnionPayOrderService unionPayOrderService;

    @Autowired
    private ZhongNanPayService zhongNanPayService;

    @Autowired
    private ZhiNengCloudPayService zhiNengCloudPayService;

    @Autowired
    private RongYaPayService rongYaPayService;

    @Autowired
    private RongHePayService rongHePayService;

    @Autowired
    private MingFuPayService mingFuPayService;

    @Autowired
    private HuanXunPayService huanXunPayService;

    private final double FEE = 2.00;

    public ResponseEntity out(MoneyOutBean moneyOutBean) {
        String userId = AuthContext.getUserId();

        User userById = userDao.findUserById(userId);

        if(userById == null || !userById.getTradePassword().equals(Utils.md5(moneyOutBean.getPassword()))){
            return new ResponseErrorEntity(StatusCode.PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
        }

        if(null == userDao.findBankCard(userId)){
            return new ResponseErrorEntity(StatusCode.NOT_BANKCARD,HttpStatus.NOT_FOUND);
        }
        //检查工作日提现
        Date date = new Date();
        Calendar calendar_start = Calendar.getInstance();
        calendar_start.setTime(date);
        calendar_start.set(Calendar.HOUR_OF_DAY,9);
        calendar_start.set(Calendar.MINUTE,00);
        Calendar calendar_end = Calendar.getInstance();
        calendar_end.setTime(date);
        calendar_end.set(Calendar.HOUR_OF_DAY,17);
        calendar_end.set(Calendar.MINUTE,00);

        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(date);

            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                return new ResponseErrorEntity(StatusCode.BEYOND_WITHDRAW_TIME,HttpStatus.BAD_REQUEST);
            }
            if(calendar.compareTo(calendar_start) == -1 || calendar.compareTo(calendar_end) == 1){
                return new ResponseErrorEntity(StatusCode.BEYOND_WITHDRAW_TIME, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //验证最低提现100元
        if(new BigDecimal(moneyOutBean.getMoney()).compareTo(new BigDecimal("100")) == -1){
            return new ResponseErrorEntity(StatusCode.WITHDRAW_MONEY_MIN,HttpStatus.BAD_REQUEST);
        }

        //验证提现金额，提现次数，是否超过规定
        ParameterSet parameterSet = parameterSetDao.findParameterSet();
        List<MoneyRecord> moneyRecordList = moneyDao.findUserMoneyRecord(userId);

        BigDecimal bigDecimal = new BigDecimal(0);
        Double moneyRecordMax = 0D;
        for (MoneyRecord moneyRecord : moneyRecordList){
            moneyRecordMax += moneyRecord.getMoney().doubleValue();
        }
        moneyRecordMax += new BigDecimal(moneyOutBean.getMoney()).doubleValue();
        if(null != parameterSet){
            if (new BigDecimal(moneyRecordMax).compareTo(parameterSet.getCashMoneyRation()) == 1){//提现额度大于规定额度时
                return new ResponseErrorEntity(StatusCode.CASH_MONEY_RATION_MAX, HttpStatus.BAD_REQUEST);
            }
            if(moneyRecordList.size() >= parameterSet.getCashMoneyCount()){
                return new ResponseErrorEntity(StatusCode.CASH_MONEY_COUNT_MAX, HttpStatus.BAD_REQUEST);
            }
        }


        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setSerialNo(serialNo());
        moneyRecord.setCreateTime(new Date());

        moneyRecord.setType(Constants.Money.MONEY_RECORD_TYPE_OUT);
        //线上出金money
        //moneyRecord.setMoney(Utils.toBigDecimal(moneyOutBean.getMoney()));
        //线下出金
        moneyRecord.setMoney(Utils.toBigDecimal(moneyOutBean.getMoney()).subtract(new BigDecimal(FEE)));

        // 提现手续费设置
        // 环迅出金 2元/笔
        moneyRecord.setFee(new BigDecimal(FEE));


        BankCard bankCard = userDao.findBankCard(userId);

        moneyRecord.setChnName(bankCard.getChnName());
        moneyRecord.setBankAccount(bankCard.getBankAccount());
        moneyRecord.setBankName(bankCard.getBankName());
        moneyRecord.setOpenBankName(bankCard.getOpenBankName());
        moneyRecord.setProvince(bankCard.getProvince());
        moneyRecord.setCity(bankCard.getCity());
        moneyRecord.setIdCard(bankCard.getIdCard());
        moneyRecord.setPhone(bankCard.getPhone());

        moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_DEFAULT);

        moneyRecord.preInsert();

        BigDecimal difMoney = moneyRecord.getMoney().add(moneyRecord.getFee()).multiply(new BigDecimal(-1));

        // Utils.formatStr("平仓资金变更{0}，下单流水号：{1}", difMoney.toString(), trade.getSerialNo())
        if(moneyDao.record(moneyRecord) != 1
                || !changeMoney(userId, difMoney, userById.getMoney(), Constants.Money.MONEY_CHANGE_TYPE_MONEY_OUT,
                    moneyRecord.getSerialNo())){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MONEY_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }



    /**
     * 0 充值 1 提现 2 建仓 3 平仓
     * @param userId
     * @param difMoney
     * @param beforeMoney
     * @param type
     * @param remark
     * @return
     */
    public boolean changeMoney(String userId, BigDecimal difMoney, BigDecimal beforeMoney, String type, String remark){
        // 资金变更
        MoneyChange moneyChange = new MoneyChange();
        moneyChange.setUserId(userId);
        moneyChange.setCreateTime(new Date());
        moneyChange.setDifMoney(difMoney);
        moneyChange.setBeforeMoney(beforeMoney);
        moneyChange.setAfterMoney(beforeMoney.add(difMoney));
        // 类型 0 充值 1 提现 2 认购  3 买入 4 平仓
        moneyChange.setType(type);
        moneyChange.setRemark(remark);
        moneyChange.preInsert();
        if(moneyDao.recordChangeMoney(moneyChange) != 1
                || userDao.changeUserMoney(moneyChange) != 1){
            return false;
        }
        if(Constants.Money.MONEY_CHANGE_TYPE_MONEY_IN.equals(type) && difMoney.doubleValue() > 0){
            if(userDao.changeUserRechargeMoney(moneyChange) != 1){
                return false;
            }
        }
        return true;
    }

    /**
     * 代金券变更
     * @param userId
     * @param difMoney
     * @param title
     * @param content
     * @return
     */
    public boolean changeCouponMoney(String userId, BigDecimal difMoney, String title, String content){
        UserMessage message = new UserMessage();
        message.setUserId(userId);
        message.setTitle(title);
        message.setContent(content);

        User user = new User();
        user.setId(userId);
        user.setCouponMoney(difMoney);

        message.preInsert();
        if(userMessageDao.record(message) != 1 || userDao.changeCouponMoney(user) != 1){
            return false;
        }
        return true;
    }

    private final Random random = new Random();
    /**
     * 获取流水号
     * @return
     */
    private String serialNo(){
        return "MO" + new Date().getTime()+""+String.format("%1$03d", random.nextInt(1000));
    }

    /**
     * 微信预支付
     * @param prePayBean
     * @return
     */
    public ResponseEntity prePay(PrePayBean prePayBean) {
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setUserId(AuthContext.getUserId());
        moneyRecord.setSerialNo(serialNo());
        moneyRecord.setCreateTime(new Date());

        moneyRecord.setChannel("微信");
        moneyRecord.setMoney(Utils.toBigDecimal(prePayBean.getMoney()));
        moneyRecord.setFee(new BigDecimal(0.00));
        moneyRecord.setType(Constants.Money.MONEY_RECORD_TYPE_IN);
        moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_DEFAULT);

        User userById = userDao.findUserById(AuthContext.getUserId());
        UnifiedOrderRespBean unifiedOrderRespBean = weiXinPayService.unifiedOrder("WEB", "会员微信充值", moneyRecord.getSerialNo(),
                moneyRecord.getMoney().multiply(new BigDecimal(100)).intValue(), prePayBean.getSpbillCreateIp(), prePayBean.getTradeType(), userById.getOpenId());
        if(!unifiedOrderRespBean.success()){
            return new ResponseErrorEntity(StatusCode.WX_PRE_PAY_ERROR, HttpStatus.BAD_REQUEST);
        }

        //微信支付流水号
        moneyRecord.setThirdSerialNo(unifiedOrderRespBean.getPrepay_id());
        moneyRecord.preInsert();
        if(moneyDao.record(moneyRecord) != 1){
            return new ResponseErrorEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(weiXinPayService.toWCPayReqBean(unifiedOrderRespBean), HttpStatus.OK);
    }

    @Override
    public boolean payResult(NotifyReqBean notifyReqBean) {
        String serialNo = notifyReqBean.getOut_trade_no();
        MoneyRecord m = moneyDao.findMoneyRecordBySerialNoWithLock(serialNo);
        // 如果已经处理直接返回成功
        if(Constants.Money.MONEY_RECORD_STATUS_SUCCESS.equals(m.getStatus())
                || Constants.Money.MONEY_RECORD_STATUS_FAIL.equals(m.getStatus())){
            return true;
        }
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setSerialNo(notifyReqBean.getOut_trade_no());
        moneyRecord.setFailureReason(notifyReqBean.getErr_code_des());
        moneyRecord.setCompleteTime(new Date());
        if(notifyReqBean.success()){
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_SUCCESS);
        }else {
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_FAIL);
        }
        User user = userDao.findUserById(m.getUserId());
        if(moneyDao.updateRecord(moneyRecord) != 1
                || (notifyReqBean.success() && !changeMoney(m.getUserId(), m.getMoney(), user.getMoney(), Constants.Money.MONEY_CHANGE_TYPE_MONEY_IN, notifyReqBean.getOut_trade_no()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public ResponseEntity deletePrePay(String thirdSerialNo) {
        Map<String, String> map = new HashMap<>(2);
        map.put("thirdSerialNo", thirdSerialNo);
        map.put("userId", AuthContext.getUserId());
        if(moneyDao.deletePrePayMoneyRecord(map) != 1){
            return new ResponseErrorEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity returnMoneyOut(MoneyOutBean moneyOutBean) {
        String userId = AuthContext.getUserId();

        User userById = userDao.findUserById(userId);

        if(userById == null || !userById.getTradePassword().equals(moneyOutBean.getPassword())){
            return new ResponseErrorEntity(StatusCode.PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
        }

        if(null == userDao.findBankCard(userId)){
            return new ResponseErrorEntity(StatusCode.NOT_BANKCARD,HttpStatus.NOT_FOUND);
        }

        //检查工作日提现
        Date date = new Date();
        Calendar calendar_start = Calendar.getInstance();
        calendar_start.setTime(date);
        calendar_start.set(Calendar.HOUR_OF_DAY,9);
        calendar_start.set(Calendar.MINUTE,00);
        Calendar calendar_end = Calendar.getInstance();
        calendar_end.setTime(date);
        calendar_end.set(Calendar.HOUR_OF_DAY,17);
        calendar_end.set(Calendar.MINUTE,00);

        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(date);

            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                return new ResponseErrorEntity(StatusCode.BEYOND_WITHDRAW_TIME,HttpStatus.BAD_REQUEST);
            }
            if(calendar.compareTo(calendar_start) == -1 || calendar.compareTo(calendar_end) == 1){
                return new ResponseErrorEntity(StatusCode.BEYOND_WITHDRAW_TIME, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //验证最低提现100元
        if(new BigDecimal(moneyOutBean.getMoney()).compareTo(new BigDecimal("100")) == -1){
            return new ResponseErrorEntity(StatusCode.WITHDRAW_MONEY_MIN,HttpStatus.BAD_REQUEST);
        }

        //验证提现金额，提现次数，是否超过规定
        ParameterSet parameterSet = parameterSetDao.findParameterSet();
        List<MoneyRecord> moneyRecordList = moneyDao.findUserMoneyRecord(userId);

        BigDecimal bigDecimal = new BigDecimal(0);
        Double moneyRecordMax = 0D;
        for (MoneyRecord moneyRecord : moneyRecordList){
            moneyRecordMax += moneyRecord.getMoney().doubleValue();
        }
        moneyRecordMax += new BigDecimal(moneyOutBean.getMoney()).doubleValue();
        if(null != parameterSet){
            if (new BigDecimal(moneyRecordMax).compareTo(parameterSet.getCashMoneyRation()) == 1){//提现额度大于规定额度时
                return new ResponseErrorEntity(StatusCode.CASH_MONEY_RATION_MAX, HttpStatus.BAD_REQUEST);
            }
            if(moneyRecordList.size() >= parameterSet.getCashMoneyCount()){
                return new ResponseEntity(StatusCode.CASH_MONEY_COUNT_MAX, HttpStatus.BAD_REQUEST);
            }
        }


        ReturnMoneyOut returnMoneyOut = new ReturnMoneyOut();
        returnMoneyOut.setUserId(userId);
        returnMoneyOut.setSerialNo(serialNo());
        returnMoneyOut.setCreateTime(new Date());

        returnMoneyOut.setMoney(Utils.toBigDecimal(moneyOutBean.getMoney()));
        returnMoneyOut.setFee(new BigDecimal(FEE));

        BankCard bankCard = userDao.findBankCard(userId);

        returnMoneyOut.setChnName(bankCard.getChnName());
        returnMoneyOut.setBankAccount(bankCard.getBankAccount());
        returnMoneyOut.setBankName(bankCard.getBankName());
        returnMoneyOut.setOpenBankName(bankCard.getOpenBankName());
        returnMoneyOut.setProvince(bankCard.getProvince());
        returnMoneyOut.setCity(bankCard.getCity());

        returnMoneyOut.preInsert();

        if(moneyDao.returnMoneyOut(returnMoneyOut)!= 1 || userDao.subtractReturnMoney(returnMoneyOut) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MONEY_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity prePayOfWeiFuTong(PrePayBean prePayBean) {
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setUserId(AuthContext.getUserId());
        moneyRecord.setSerialNo(serialNo());
        moneyRecord.setCreateTime(new Date());

        moneyRecord.setChannel("微信");
        moneyRecord.setMoney(Utils.toBigDecimal(prePayBean.getMoney()));
        moneyRecord.setFee(new BigDecimal(0.00));
        moneyRecord.setType(Constants.Money.MONEY_RECORD_TYPE_IN);
        moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_DEFAULT);

        User userById = userDao.findUserById(AuthContext.getUserId());
        PrePayRespBean prePayRespBean = weiFuTongPayService.payInit(moneyRecord.getSerialNo(), "充值支付",
                userById.getOpenId(), moneyRecord.getMoney().multiply(new BigDecimal(100)).intValue(), prePayBean.getSpbillCreateIp());
        if(!prePayRespBean.success()){
            Log.error("WFT预支付接口调用失败 ".concat(JSON.toJSONString(prePayRespBean)));
            return new ResponseErrorEntity(StatusCode.WX_PRE_PAY_ERROR, HttpStatus.BAD_REQUEST);
        }

        //威富通公众号js支付
//        moneyRecord.setThirdSerialNo();
        moneyRecord.preInsert();
        if(moneyDao.record(moneyRecord) != 1){
            return new ResponseErrorEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(prePayRespBean.getToken_id(), HttpStatus.OK);
    }

    public boolean payResultOfWeiFuTong(PayResultReqBean payResultReqBean) {
        String serialNo = payResultReqBean.getOut_trade_no();
        MoneyRecord m = moneyDao.findMoneyRecordBySerialNoWithLock(serialNo);
        // 如果已经处理直接返回成功
        if(Constants.Money.MONEY_RECORD_STATUS_SUCCESS.equals(m.getStatus())
                || Constants.Money.MONEY_RECORD_STATUS_FAIL.equals(m.getStatus())){
            return true;
        }
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setSerialNo(payResultReqBean.getOut_trade_no());
        moneyRecord.setFailureReason(payResultReqBean.getErr_msg());
        moneyRecord.setCompleteTime(new Date());
        if(payResultReqBean.success()){
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_SUCCESS);
        }else {
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_FAIL);
        }
        User user = userDao.findUserById(m.getUserId());
        if(moneyDao.updateRecord(moneyRecord) != 1
                || (payResultReqBean.success() && !changeMoney(m.getUserId(), m.getMoney(), user.getMoney(), Constants.Money.MONEY_CHANGE_TYPE_MONEY_IN, "充值支付"))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    //首信易支付扫码下单
    public ResponseEntity shouXinYinCreateOrder(PayRequestParamBean payRequestParamBean){
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setUserId(AuthContext.getUserId());
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String dateId1 = df1.format(new Date());//获取当前日期
        String serialNo = serialNo();
        String thirdSeriaNo = dateId1+"-"+ ProjectConstants.V_MID+"-"+serialNo;
        moneyRecord.setSerialNo(serialNo);
        moneyRecord.setThirdSerialNo(thirdSeriaNo);
        moneyRecord.setCreateTime(new Date());
        moneyRecord.setChannel("微信充值");
        moneyRecord.setMoney(Utils.toBigDecimal(payRequestParamBean.getMoney()));
        moneyRecord.setFee(new BigDecimal(0.00));
        moneyRecord.setType(Constants.Money.MONEY_RECORD_TYPE_IN);
        moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_DEFAULT);
        //调用下单接口
        PayOrderResBean payOrderResBean = payOrderService.createOrder(payRequestParamBean,moneyRecord.getThirdSerialNo(),moneyRecord.getSerialNo());
        //验证数据签名
        //v_status + v_oid + v_amount + v_moneytype+v_codeurl+v_bankurl
        String dataSign = payOrderResBean.getStatus()+payOrderResBean.getOid()+payOrderResBean.getAmount()+payOrderResBean.getMoneytype()+payOrderResBean.getCodeurl()+payOrderResBean.getBankurl();
        //验证签名
        //首信公钥文件路径
        String publicKey = MoneyService.class.getClassLoader().getResource("cert/Public1024.key").getPath();
        //签名信息
        String SignString = payOrderResBean.getSign();
        //原信息
        String strSource = dataSign;
        RSA_MD5 rsaMD5 = new RSA_MD5();
        //公钥验证 k=0验证成功
        int k = rsaMD5.PublicVerifyMD5(publicKey,SignString,strSource);
        if((!"0".equals(payOrderResBean.getStatus())) || (k != 0)){
            Log.error("create order fail respCode is "+ payOrderResBean.getStatus());
            return new ResponseErrorEntity("创建订单失败", HttpStatus.BAD_REQUEST);
        }
        //
        moneyRecord.preInsert();
        if(moneyDao.record(moneyRecord) != 1){
            return new ResponseErrorEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(payOrderResBean,HttpStatus.OK);
    }

    /**
     * 根据结果判断是否，充值和增加充值记录和资金变化
     */
    public boolean toAddMoney(String serialNo,String status){
        MoneyRecord m = moneyDao.findMoneyRecordBySerialNoWithLock(serialNo);
        // 如果已经处理直接返回成功
        if(m == null){
            return false;
        }
        if(Constants.Money.MONEY_RECORD_STATUS_SUCCESS.equals(m.getStatus())
                || Constants.Money.MONEY_RECORD_STATUS_FAIL.equals(m.getStatus())){
            return true;
        }
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setSerialNo(serialNo);
        moneyRecord.setCompleteTime(new Date());
        if("1".equals(status)){
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_SUCCESS);
        }else if("0".equals(status)){
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_DEFAULT);
            moneyRecord.setFailureReason("未支付");
        }else{
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_FAIL);
            moneyRecord.setFailureReason("支付失败");
        }
        User user = userDao.findUserById(m.getUserId());
        if(moneyDao.updateRecord(moneyRecord) != 1 || ("1".equals(status) && !changeMoney(m.getUserId(), m.getMoney(), user.getMoney(), Constants.Money.MONEY_CHANGE_TYPE_MONEY_IN, serialNo))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    /**
     *根据充值记录表查询，处理中的充值记录的支付结果
     */
    public List<MoneyRecord> selPayResult(){
        return moneyDao.selChargeResult();
    }

    /**
     * 首信易支付 银联支付下单
     */
    public ResponseEntity createUnionPayOrder(UnionPayRequestParam unionPayRequestParam){
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setUserId(AuthContext.getUserId());
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String dateId1 = df1.format(new Date());//获取当前日期
        String serialNo = serialNo();
        String thirdSeriaNo = dateId1+"-"+ ProjectConstants.V_MID+"-"+serialNo;
        moneyRecord.setSerialNo(serialNo);
        moneyRecord.setThirdSerialNo(thirdSeriaNo);
        moneyRecord.setCreateTime(new Date());
        moneyRecord.setChannel("银联充值");
        moneyRecord.setMoney(Utils.toBigDecimal(unionPayRequestParam.getMoney()));
        moneyRecord.setFee(new BigDecimal(0.00));
        moneyRecord.setType(Constants.Money.MONEY_RECORD_TYPE_IN);
        moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_DEFAULT);
        UnionPayOrderReq unionPayOrderReq = unionPayOrderService.createUnionPayOrder();
        unionPayOrderReq.setV_amount(unionPayRequestParam.getMoney());
        unionPayOrderReq.setV_oid(thirdSeriaNo);
        unionPayOrderReq.setV_ymd(dateId1);
        //数字签名加密
        String v_md5infoStr = unionPayOrderReq.getV_moneytype() + unionPayOrderReq.getV_ymd()+unionPayOrderReq.getV_amount()+unionPayOrderReq.getV_rcvname()+unionPayOrderReq.getV_oid()+unionPayOrderReq.getV_mid()+unionPayOrderReq.getV_url();
        String v_md5info = null;
        Md5 md5 = new Md5("");
        try {
            md5.hmac_Md5(v_md5infoStr, ProjectConstants.MD5KEY); //第一个参数是加密参数，第二个参数是加密密钥，测试密钥：test，正式上线之前注意修改
            byte b[] = md5.getDigest();
            v_md5info = md5.stringify(b);
            unionPayOrderReq.setV_md5info(v_md5info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        moneyRecord.preInsert();
        if(moneyDao.record(moneyRecord) != 1){
            return new ResponseErrorEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(unionPayOrderReq,HttpStatus.OK);
    }

    public ResponseEntity zhongNanQuickPayBean(ZhongNanQuickPayBean zhongNanQuickPayBean) {
        BigDecimal amount = new BigDecimal(zhongNanQuickPayBean.getMoney());
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "中南快捷支付");

        Map<String, String> map = new HashMap<>();
        map.put("card_no", zhongNanQuickPayBean.getCard_no());
        map.put("phone_no", zhongNanQuickPayBean.getPhone_no());
        map.put("card_name", zhongNanQuickPayBean.getCard_name());
        map.put("id_no", zhongNanQuickPayBean.getId_no());

        JSONObject order = zhongNanPayService.createOrder(zhongNanQuickPayBean.getMoney(), moneyRecord.getSerialNo(), "qkpay", map);
        if(order == null){
            return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
        }
        moneyDao.record(moneyRecord);
        Log.info("中南快捷支付参数:".concat(JSON.toJSONString(order)));
        return new ResponseEntity(order, HttpStatus.OK);
    }

    public ResponseEntity zhiNengCloudWechatPay(ZhiNengCloudPayBean zhiNengCloudPayBean) {
        BigDecimal amount = new BigDecimal(zhiNengCloudPayBean.getMoney());
        if(!checkZhiNengCloud(amount, "2")){
            return new ResponseErrorEntity("请选择正确金额", HttpStatus.BAD_REQUEST);
        }
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "智能云微信支付");

        Map<String, String> map = new HashMap<>();
        zhiNengCloudPayService.buildParamter(map, moneyRecord, "2");
        zhiNengCloudPayService.signature(map);
//        JSONObject order = zhongNanPayService.createOrder(zhongNanQuickPayBean.getMoney(), moneyRecord.getSerialNo(), "qkpay", map);
//        if(order == null){
//            return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
//        }
        moneyDao.record(moneyRecord);
        Log.info("智能云微信支付参数:".concat(JSON.toJSONString(map)));
        return new ResponseEntity(map, HttpStatus.OK);

    }

    public ResponseEntity zhiNengCloudAli(ZhiNengCloudPayBean zhiNengCloudPayBean) {
        BigDecimal amount = new BigDecimal(zhiNengCloudPayBean.getMoney());
        if(!checkZhiNengCloud(amount, "1")){
            return new ResponseErrorEntity("请选择正确金额", HttpStatus.BAD_REQUEST);
        }
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "智能云支付宝支付");

        Map<String, String> map = new HashMap<>();
        zhiNengCloudPayService.buildParamter(map, moneyRecord, "1");
        zhiNengCloudPayService.signature(map);
//        JSONObject order = zhongNanPayService.createOrder(zhongNanQuickPayBean.getMoney(), moneyRecord.getSerialNo(), "qkpay", map);
//        if(order == null){
//            return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
//        }
        moneyDao.record(moneyRecord);
        Log.info("智能云支付宝支付参数:".concat(JSON.toJSONString(map)));
        return new ResponseEntity(map, HttpStatus.OK);

    }

    public ResponseEntity rongyaUnionPay(ZhiNengCloudPayBean zhiNengCloudPayBean) {
        BigDecimal amount = new BigDecimal(zhiNengCloudPayBean.getMoney());
        if(!checkZhiNengCloud(amount, "1")){
            return new ResponseErrorEntity("请选择正确金额", HttpStatus.BAD_REQUEST);
        }
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "融雅网关支付");

        Map<String, Object> parameter = rongYaPayService.buildParameter(moneyRecord, "2088");
//        JSONObject order = zhongNanPayService.createOrder(zhongNanQuickPayBean.getMoney(), moneyRecord.getSerialNo(), "qkpay", map);
//        if(order == null){
//            return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
//        }
        moneyDao.record(moneyRecord);
        return new ResponseEntity(parameter, HttpStatus.OK);
    }

    public ResponseEntity rongyaWechatWapPay(ZhiNengCloudPayBean zhiNengCloudPayBean) {
        BigDecimal amount = new BigDecimal(zhiNengCloudPayBean.getMoney());
        if(!checkZhiNengCloud(amount, "1")){
            return new ResponseErrorEntity("请选择正确金额", HttpStatus.BAD_REQUEST);
        }
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "融雅微信wap支付");

        Map<String, Object> parameter = rongYaPayService.buildParameter(moneyRecord, "1005");
//        JSONObject order = zhongNanPayService.createOrder(zhongNanQuickPayBean.getMoney(), moneyRecord.getSerialNo(), "qkpay", map);
//        if(order == null){
//            return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
//        }
        moneyDao.record(moneyRecord);
        return new ResponseEntity(parameter, HttpStatus.OK);
    }

    public ResponseEntity rongyaAliWapPay(ZhiNengCloudPayBean zhiNengCloudPayBean) {
        BigDecimal amount = new BigDecimal(zhiNengCloudPayBean.getMoney());
        if(!checkZhiNengCloud(amount, "1")){
            return new ResponseErrorEntity("请选择正确金额", HttpStatus.BAD_REQUEST);
        }
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "融雅支付宝wap支付");

        Map<String, Object> parameter = rongYaPayService.buildParameter(moneyRecord, "1006");

        moneyDao.record(moneyRecord);
        return new ResponseEntity(parameter, HttpStatus.OK);
    }

    /**
     * 融合快捷支付
     * @param zhiNengCloudPayBean
     * @return
     */
    public ResponseEntity rongheFastPay(ZhiNengCloudPayBean zhiNengCloudPayBean) {
        BigDecimal amount = new BigDecimal(zhiNengCloudPayBean.getMoney());
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "融雅支付宝wap支付");

        String data = rongHePayService.fastPay(moneyRecord);

        if(data == null){
            return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
        }
        moneyDao.record(moneyRecord);
        return new ResponseEntity(data, HttpStatus.OK);
    }

    /**
     * 明付网关支付
     * @param zhiNengCloudPayBean
     * @return
     */
    public ResponseEntity mingfuUnionPay(ZhiNengCloudPayBean zhiNengCloudPayBean) {
        try {
            BigDecimal amount = new BigDecimal(zhiNengCloudPayBean.getMoney());
            MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "明付网关支付");

            Map<String, Object> parameter = mingFuPayService.mingFuPay(moneyRecord);

            if(parameter == null){
                return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
            }
            moneyDao.record(moneyRecord);
            String patamJson = JSON.toJSONString(parameter);
            String paramJsonEncode = URLEncoder.encode(new String(patamJson.getBytes(), "UTF-8"));
            return new ResponseEntity(paramJsonEncode, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 明付快捷支付
     * @param mingFuQuickBean
     * @return
     */
    public ResponseEntity mingfuQuickPay(MingFuQuickBean mingFuQuickBean) {
        BigDecimal amount = new BigDecimal(mingFuQuickBean.getMoney());
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "明付快捷支付");

        JSONObject parameter = mingFuPayService.mingFuQuickPay(moneyRecord, mingFuQuickBean);

        if(null == parameter){
            return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
        }
        moneyDao.record(moneyRecord);
        //放入缓存，等待用户短信验证
        /*MoneyRecord cacheRecord = (MoneyRecord)CacheFacade.getObject(AuthContext.getUserId());
        if(null != cacheRecord){
            CacheFacade.delete(AuthContext.getUserId());
        }
        CacheFacade.set(AuthContext.getUserId(), moneyRecord, 300);
        return new ResponseEntity("验证码发送成功，请在5分钟内支付！", HttpStatus.OK);*/
        return new ResponseEntity(parameter.get("codeUrl"), HttpStatus.OK);
    }

    public ResponseEntity validMingFuMsg(String msgCode){
        MoneyRecord moneyRecord = (MoneyRecord) CacheFacade.getObject(AuthContext.getUserId());
        if(null == moneyRecord){
            return new ResponseErrorEntity("验证码错误", HttpStatus.BAD_REQUEST);
        }
        boolean result = mingFuPayService.mingFuQuickMsgSub(moneyRecord, msgCode);
        if(!result){
            return new ResponseErrorEntity("短信验证失败，请稍后再试！", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("验证成功", HttpStatus.OK);
    }

    /**
     * 环迅网关支付
     * @param entity
     * @return
     */
    public ResponseEntity huanXunGetaWayPay(HuanXunMoneyVO entity) {
        if(new BigDecimal(100).compareTo(new BigDecimal(entity.getMoney())) == 1){
            return new ResponseErrorEntity(StatusCode.MONEY_RECORD_MIN_ERROR, HttpStatus.BAD_REQUEST);
        }
        BigDecimal amount = new BigDecimal(entity.getMoney());
        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(amount, amount.multiply(new BigDecimal(0)), "环迅网关支付");

        String parameter = huanXunPayService.buildParameter(moneyRecord, entity);

        if(parameter == null){
            return new ResponseErrorEntity(StatusCode.SERVER_ERROR, HttpStatus.BAD_REQUEST);
        }
        moneyDao.record(moneyRecord);
        return new ResponseEntity(parameter, HttpStatus.OK);
    }

     /*
   * 佣金转余额
   *
   *
     * 0 充值 1 提现 2 建仓 3 平仓 4提现失败退款 5 佣金转余额
     * @param userId
     * @param difMoney
     * @param beforeMoney
     * @param type
     * @param remark
     * @return
     */

    public ResponseEntity commissionToMoney() {
        try {
            User user = userDao.findUserById(AuthContext.getUserId());
            ReturnMoneyOut rmo=new ReturnMoneyOut();
            rmo.setUserId(user.getId() );
            rmo.setMoney(user.getReturnMoney());
            if(!changeMoney(user.getId(), user.getReturnMoney(), user.getMoney(), "5", "佣金转余额") ||
                    userDao.commissionToMoney(rmo)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 检查微信支付或支付宝支付金额是否正确，防止充值错误
     * @param money
     * @param payType 2: 微信支付 1:支付宝支付
     * @return
     */
    public boolean checkZhiNengCloud(BigDecimal money, String payType){
        int[] wechatMoney = {100, 1000, 5000, 10000};
        int[] aliMoney = {100, 1000, 5000, 10000, 15000, 20000};

        if(payType == "2"){
            for(int i = 0; i < wechatMoney.length; i++){
                if(wechatMoney[i] == money.intValue()){
                    return true;
                }
            }
        }else if(payType == "1"){
            for(int i = 0; i < aliMoney.length; i++){
                if(aliMoney[i] == money.intValue()){
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 获取用户充值提现记录
     * @return
     */
    public List<MoneyRecord> findUserPayMoneyRecord(MoneyRecord moneyRecord){
        String userId = AuthContext.getUserId();
        moneyRecord.setUserId(userId);
        if(StringUtils.isBlank(moneyRecord.getType())){
            moneyRecord.setType(null);
        }
        if(StringUtils.isBlank(moneyRecord.getWithDrawTime()))
            moneyRecord.setWithDrawTime(null);
        List<MoneyRecord> list = moneyDao.findUserPayMoneyRecord(moneyRecord);
        return list;
    }
}
