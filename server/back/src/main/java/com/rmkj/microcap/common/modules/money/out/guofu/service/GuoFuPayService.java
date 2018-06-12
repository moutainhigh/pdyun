package com.rmkj.microcap.common.modules.money.out.guofu.service;/**
 * Created by Administrator on 2017/10/18.
 */

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.guofu.impl.GuoFuInterface;
import com.rmkj.microcap.common.modules.weixin.bean.MenuBean;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.moneychange.dao.IMoneyChangeDao;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author k
 * @create -10-18-15:13
 **/
@Service
public class GuoFuPayService implements GuoFuInterface{

    private static Logger logger = Logger.getLogger(GuoFuPayService.class);

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IMoneyChangeDao moneyChangeDao;

    /**
     * 构造代付数据
     * @param moneyRecordBean 出金记录
     * @throws Exception
     */
    @Override
    public void buildParam(Map<String, Object> param, MoneyRecordForOutBean moneyRecordBean) throws Exception {
        param.put("cardno", ProjectConstants.GUOFU_VR_NO);
        param.put("traceno", moneyRecordBean.getSerialNo());
        param.put("amount", moneyRecordBean.getMoney().toString());
        param.put("accountno", moneyRecordBean.getBankAccount());
        param.put("accountName", URLEncoder.encode(moneyRecordBean.getChnName(), "GBK"));
        param.put("mobile", "15013698981");
        param.put("bankno", "105584001299");
        param.put("bankName", URLEncoder.encode(moneyRecordBean.getOpenBankName(), "GBK"));
        param.put("bankType", URLEncoder.encode(moneyRecordBean.getBankName(), "GBK"));
        param.put("remark", URLEncoder.encode("虚拟账户转账", "GBK"));
        param.put("signature", signature(param));
    }

    private static String signature(Map<String, Object> param) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();
        sb.append("cardno=" + param.get("cardno"));
        sb.append("&traceno=" + param.get("traceno"));
        sb.append("&amount=" + param.get("amount"));
        sb.append("&accountno=" + param.get("accountno"));
        sb.append("&mobile=" + param.get("mobile"));
        sb.append("&bankno=" + param.get("bankno"));
        sb.append("&key=".concat(ProjectConstants.GUOFU_KEY));
        String data = sb.toString();
        logger.info("签名数据:\r\n" + data);
        return Utils.md5(data);
    }

    /**
     * 构造国富代付查询参数
     * @param param
     * @param moneyRecordBean
     */
    public void buildQueryParam(Map<String, Object> param, MoneyRecordBean moneyRecordBean) throws NoSuchAlgorithmException {
        param.put("cardno", ProjectConstants.GUOFU_VR_NO);
        param.put("traceno", moneyRecordBean.getSerialNo());
        param.put("signature", signatureQuery(param));
    }

    private static String signatureQuery(Map<String, Object> param) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();
        sb.append("cardno=" + param.get("cardno"));
        sb.append("&traceno=" + param.get("traceno"));
        sb.append("&key=".concat(ProjectConstants.GUOFU_KEY));
        String data = sb.toString();
        logger.info("签名数据:\r\n" + data);
        return Utils.md5(data);
    }

    /**
     * 出金代付或审核失败时，资金返回账户余额
     * @param moneyRecordBean
     * @param failureReason
     * @return
     */
    @Override
    public int updateNotOutMoney(MoneyRecordBean moneyRecordBean, String failureReason) {
        UserBean userBean = userDao.get(moneyRecordBean.getUserId());
        userBean.setMoney(moneyRecordBean.getMoney().add(moneyRecordBean.getFee()));
        //出金审核时，再修改用户表，新增资金变更表
        int status =  userDao.moneyBack(userBean);//修改用户表余额
        if(1 == status){
            UserBean userBean1 = userDao.get(userBean);//修改用户表余额后的用户实体

            MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
            moneyChangeBean.setId(Utils.uuid());
            moneyChangeBean.setUserId(moneyRecordBean.getUserId());
            moneyChangeBean.setDifMoney(moneyRecordBean.getMoney());//变更金额
            moneyChangeBean.setType(1);
            moneyChangeBean.setAfterMoney(userBean1.getMoney());//变更后的金额
            BigDecimal moneybefore = userBean1.getMoney().subtract(moneyRecordBean.getMoney().add(moneyRecordBean.getFee()));//变更前金额
            moneyChangeBean.setBeforeMoney(moneybefore);
            moneyChangeBean.setCreateTime(new Date());
            moneyChangeBean.setRemark(failureReason);
            moneyChangeDao.insert(moneyChangeBean);//新增资金变更表
        }else{
            return -1;
        }
        return status;
    }

    /**
     * 通知用户
     * @param userId
     * @param content
     */
    @Override
    public void sendMessageToUser(String userId, String content) {
        UserMessageBean userMessageBean = new UserMessageBean();
        userMessageBean.setId(Utils.uuid());
        userMessageBean.setUserId(userId);
        userMessageBean.setTitle("提现");
        userMessageBean.setContent(content);
        userMessageBean.setReadStatus(0);
        userMessageBean.setType(0);
        userMessageBean.setCreateTime(new Date());
        userDao.insertUserMessage(userMessageBean);
    }

}
