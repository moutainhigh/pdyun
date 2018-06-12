package com.rmkj.microcap.modules.ReturnMoneyOut.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.ReturnMoney2User;
import com.rmkj.microcap.modules.user.entity.UserBean;

import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-12-7.
*/
@DataSource
public interface IReturnMoneyOutDao extends BaseDao<ReturnMoneyOutBean> {
    int outPast(ReturnMoneyOutBean bean);//提现审核不通过时
    int outPastIn(ReturnMoneyOutBean bean);//提现审核通过时
    List<MoneyRecordForOutBean> findNoResult2Check();//定频查询提现结果用
    List<ReturnMoneyOutBean> findNoResultCheckOutMoney();//定频查询提现结果用-阿拉支付
    int updateSuccess(String id);//修改提现状态成功
    int updateFailure(String id);//修改提现状态失败
    int updateTimeOut(String id);//超时
    int updateReturnMoneyOutSuccess(String id);//修改提现佣金审核状态
    List<WithdrawalsBean> queryInfoForOut(List<String> idArr);//根据id查询的交易记录 批量查询
    UserBean queryUserMoney(String userId);//查询用户id和可提现金额
    int returnMoney2User(ReturnMoney2User returnMoney2User);//提现失败时，修改用户表可提现佣金
    int addMoneyChange(MoneyChangeBean moneyChangeBean);//往资金变更表添加记录
    int pass(List<String> idArr);
    int nopass(Map<String, Object> map);

    int review(MoneyRecordBean moneyRecord);

    List<ReturnMoneyOutBean> findByIds(List<String> stringList);

    //根据id查询佣金记录 批量查询
    List<ReturnMoneyOutBean> queryInfoForReturnMoneyOut(List<String> idArr);

    /**
     * 威鹏支付出金时，修改审核状态，状态
     * @return
     */
    int outPastInStatusAndReviewStatus(ReturnMoneyOutBean returnMoneyOutBean);
    //威鹏代付 查询代付异常订单
    List<ReturnMoneyOutBean> findWeiPengNoResult2Check();

    /**
     * 根据订单号获取佣金提现记录
     * @param serialNo
     * @return
     */
    ReturnMoneyOutBean getReturnMoneyByserialNo(String serialNo);

    /**
     * 佣金代付成功后，修改佣金提现状态
     * @param returnMoneyOutBean
     * @return
     */
    int updateDaiPayOutPastInStatus(ReturnMoneyOutBean returnMoneyOutBean);
}
