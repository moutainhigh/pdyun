package com.rmkj.microcap.modules.moneyrecord.service;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.rongya.service.RongYaWithdrawalsService;
import com.rmkj.microcap.common.modules.money.out.zhongnan.service.ZhongNanWithdrawalsService;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */
@Service
public class RongYaService extends CustomerWithdrawalsService {

	private final Logger Log = Logger.getLogger(getClass());

	@Autowired
	private BaseService baseService;

	@Autowired
	private RongYaWithdrawalsService moneyService;

	@Autowired
	private IMoneyRecordDao iMoneyRecordDao;

	public synchronized String reviewPass(String ids) {
		// 出金审核通过
		String[] idArr;
		if(StringUtils.isBlank(ids)){
			idArr = new String[0];
		}else{
			idArr = ids.split(",");
		}
		// 剔除掉已经审核的
		List<WithdrawalsBean> list = iMoneyRecordDao.queryInfoForOut(Arrays.asList(idArr));
		return moneyService.batchOut(list, this);
	}

	public synchronized String reviewNoPass(String ids, String failureReason) {
		return baseService.reviewNoPass(ids, failureReason);
	}

	/**
	 * 主动定频查询提现结果
	 * 不开事务
	 * 单笔处理，提现失败 更新提现记录表->更新用户余额(小概率更新失败)->添加变更记录
	 */
    //@Scheduled(initialDelay = 1 * 1000 * 30, fixedDelay = 60000*5)
	public void checkOutResult(){
		List<MoneyRecordBean> list = iMoneyRecordDao.findNoResult2Check();
		if(ProjectConstants.PRO_DEBUG){
			return;
		}
		list.forEach(moneyRecord -> moneyService.queryResult(moneyRecord, this));
	}
}
