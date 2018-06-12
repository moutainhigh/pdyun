package com.rmkj.microcap.modules.moneyrecord.service;

import com.rmkj.microcap.common.modules.money.out.MoneyOutServiceInterface;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.utils.WeiPengUtils;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/9/20.
 */
@Service("CustomerWithdrawalsService")
public class CustomerWithdrawalsService implements MoneyOutServiceInterface {

	private final Logger Log = Logger.getLogger(getClass());

	@Autowired
	private BaseService baseService;

	@Autowired
	private IMoneyRecordDao iMoneyRecordDao;

	@Override
	public void review(WithdrawalsBean moneyRecord){
		MoneyRecordBean recordBean = (MoneyRecordBean) moneyRecord;
		iMoneyRecordDao.review(recordBean);
	}

	/**
	 * 失败
	 * @param moneyRecord
	 */
	@Override
	public void failure(WithdrawalsBean moneyRecord){
		MoneyRecordBean recordBean = (MoneyRecordBean) moneyRecord;
		baseService.checkFailure(recordBean, false);
		/*baseService.sendWeiXinMsg(recordBean,
				moneyRecord.getMoney().add(moneyRecord.getFee()).toString(),
				"余额", "失败", WeiPengUtils.getNowTime("yyyy-MM-dd HH:mm:ss"), "及时联系客服处理");*/
	}
	/**
	 * 失败
	 * @param moneyRecord
	 */
	@Override
	public void success(WithdrawalsBean moneyRecord){
		MoneyRecordBean recordBean = (MoneyRecordBean) moneyRecord;
		baseService.checkSuccess(recordBean, false);
		/*baseService.sendWeiXinMsg(recordBean,
				moneyRecord.getMoney().add(moneyRecord.getFee()).toString(), "余额", "成功，手续费".concat(moneyRecord.getFee().toString()).concat("元"),
				WeiPengUtils.getNowTime("yyyy-MM-dd HH:mm:ss"), "查收");*/
	}

	@Override
	public WithdrawalsBean find(String serialNo) {
		return iMoneyRecordDao.getDaiPayByserialNo(serialNo);
	}
}
