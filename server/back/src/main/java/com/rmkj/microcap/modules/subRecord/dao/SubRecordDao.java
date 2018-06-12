package com.rmkj.microcap.modules.subRecord.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.subGoods.entity.SubGoods;
import com.rmkj.microcap.modules.subRecord.entity.SubParamBean;
import com.rmkj.microcap.modules.subRecord.entity.SubRecordBean;
import com.rmkj.microcap.modules.user.entity.SubMoney;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;

import java.math.BigDecimal;
import java.util.List;

/**
* Created by Administrator on 2016-10-17.
*/
@DataSource
public interface SubRecordDao {

    SubGoods getSubGoodsById(String goodsId);

    int saveSubRecord(SubRecordBean subRecordBean);

    SubParamBean getAgentLeftTimes(String id);

    SubParamBean getUnitsLeftTimes(String id);

    SubParamBean getCenterLeftTimes(String id);

    int updSubGoodsLeftNum(SubRecordBean subRecordBean);

    int updAgentSubTimes(String id);

    int updUnitsSubTimes(String id);

    int updCenterSubTimes(String id);

    List<SubRecordBean> queryList(SubRecordBean subRecordBean);

}
