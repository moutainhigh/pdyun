package com.rmkj.microcap.common.modules.money.out.guofu.impl;/**
 * Created by Administrator on 2017/10/18.
 */

import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;

import java.util.Map;

/**
 * @author k
 * @create -10-18-15:14
 **/

public interface GuoFuInterface {

    /**
     * 构造代付数据
     *
     * @return
     */
    public void buildParam(Map<String, Object> param, MoneyRecordForOutBean moneyRecordBean) throws Exception;

    /**
     * 出金代付或审核失败时，资金返回账户余额
     * @param moneyRecordBean
     * @param failureReason
     * @return
     */
    int updateNotOutMoney(MoneyRecordBean moneyRecordBean, String failureReason);

    /**
     * 通知用户消息
     * @param userId
     * @param content
     */
    void sendMessageToUser(String userId, String content);
}
