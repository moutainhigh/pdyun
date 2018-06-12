package com.rmkj.microcap.modules.moneyrecord.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyInRecordPageBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.ReturnMoney2User;
import com.rmkj.microcap.modules.user.entity.UserBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-10-17.
*/
@DataSource
public interface IMoneyRecordDao extends BaseDao<MoneyRecordBean>{
    List<MoneyRecordBean> moneyInList(MoneyRecordBean bean);//入金数据集合
    List<MoneyRecordBean> moneyOutList(MoneyRecordBean bean);//出金数据集合
    BigDecimal alreadyMoneyInTotal(MoneyRecordBean bean);
    BigDecimal moneyInTotal(MoneyRecordBean bean);
    BigDecimal noMoneyInTotal(MoneyRecordBean bean);
    BigDecimal alreadyMoneyOutTotal(MoneyRecordBean bean);
    BigDecimal moneyOutTotal(MoneyRecordBean bean);
    BigDecimal noMoneyOutTotal(MoneyRecordBean bean);
    int outPast(MoneyRecordBean bean);//出金审核不通过时
    int outPastIn(MoneyRecordBean bean);//出金审核通过时

    /**
     * TODO 威鹏支付出金时，修改审核状态，状态
     * @return
     */
    int outPastInStatusAndReviewStatus(MoneyRecordBean moneyRecordBean);

    /**
     * TODO  威鹏支付出金成功时，收到异步通知时修改
     * @param moneyRecordBean
     * @return
     */
    int updateDaiPayOutPastInStatus(MoneyRecordBean moneyRecordBean);

    /**
     * TODO 威鹏支付出金成功时，收到异步通知,根据订单号查询数据
     * @param serialNo
     * @return
     */
    MoneyRecordBean getDaiPayByserialNo(String serialNo);

    List<MoneyRecordBean> findNoResult2Check();

    int updateSuccess(String id);

    int updateTimeOut(String id);

    int updateFailure(String id);

    int updateFailureMap(Map<String, Object> map);

    List<WithdrawalsBean> queryInfoForOut(List<String> idArr);

    UserBean queryUserMoney(String userId);

    int returnMoney2User(ReturnMoney2User returnMoney2User);

    int addMoneyChange(MoneyChangeBean moneyChangeBean);

    int pass(List<String> idArr);

    int nopass(Map<String, Object> map);

    int review(MoneyRecordBean moneyRecord);

    /**
     * 根据id查询出金未审核的数据
     * @param list
     * @return
     */
    List<MoneyRecordBean> findByIds(List<String> list);

    List<MoneyRecordBean> findNoResultCheck();

    /**
     * 查询入金列表
     * @param entity
     * @return
     */
    List<MoneyInRecordPageBean> queryMoneyRecordPage(MoneyInRecordPageBean entity);

    /**
     * 查询入金列表条数
     * @param entity
     * @return
     */
    long queryMoneyRecordTotal(MoneyInRecordPageBean entity);

    /**
     * 查询入金通道
     * @return
     */
    List<String> queryRechargeChannel();

    BigDecimal queryMoneyRecordByStatus(MoneyInRecordPageBean entity);
}
