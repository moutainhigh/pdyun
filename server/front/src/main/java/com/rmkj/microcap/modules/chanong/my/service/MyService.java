package com.rmkj.microcap.modules.chanong.my.service;

import com.rmkj.microcap.common.bean.AuthEntity;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.chanong.my.bean.*;
import com.rmkj.microcap.modules.chanong.my.dao.MyDao;
import com.rmkj.microcap.modules.chanong.shop.dao.ShopDao;
import com.rmkj.microcap.modules.corps.dao.CorpsDao;
import com.rmkj.microcap.modules.trade.entity.Trade;
import com.rmkj.microcap.modules.user.dao.UserDao;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jinghao on 2018/4/24.
 */
@Service
public class MyService {
    private static Logger logger = Logger.getLogger(MyService.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private MyDao myDao;
    @Autowired
    private CorpsDao corpsDao;

    /**
     * 获取用户信息
     * @param
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getMyInfo(){
        String userId = AuthContext.getUserId();
        Map<String,Object> ret = new HashedMap();
        User user = userDao.findUserById(userId);
        MyBean myBean = new MyBean();
        myBean.setId(user.getId());
        myBean.setMobile(new StringBuffer(user.getMobile()).replace(3,7,"****").toString());
        myBean.setMoney(user.getMoney());
        myBean.setSerialNo("CY".concat(user.getMobile()));
        MyStatisticBean myStatisticBean = myDao.getMyStatistic(userId);
        myBean.setInviteName(myStatisticBean.getInviteName());
        myBean.setTradeNum(myStatisticBean.getTradeNum());
        myBean.setTransferNum(0);
        myBean.setReturnMoney(user.getReturnMoney());
        ret.put("myInfo",myBean);
        return ret;
    }

    /**
     * 添加地址
     * @param myAddressBean
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addMyAddress(MyAddressBean myAddressBean){
        myAddressBean.setId(Utils.uuid());
        myAddressBean.setUserId(AuthContext.getUserId());
        myDao.addMyAddress(myAddressBean);
    }

    /**
     * 修改地址
     * @param myAddressBean
     */
    public void updAddress(MyAddressBean myAddressBean){
        myDao.updAddress(myAddressBean);
    }

    /**
     * 管理地址
     * @param
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getMyAddress(){
        String userId = AuthContext.getUserId();
        Map<String,Object> ret = new HashedMap();
        ret.put("addressList",myDao.getMyAddress(userId));
        return ret;
    }

    /**
     * 设为默认地址
     * @param id
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void setDefaultAddress(String id){
        myDao.setDefaultAddress(id);
        myDao.setOtherAddress(id);
    }

    /**
     * 删除地址
     * @param id
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteMyAddress(String id){
        myDao.deleteMyAddress(id);
    }

    /**
     * 获取订单
     * @param myTradeBean
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getMyTrade(MyTradeBean myTradeBean){
        myTradeBean.setUserId(AuthContext.getUserId());
        Map<String,Object> ret = new HashedMap();
        List<Trade> list;
//        if(StringUtils.isEmpty(myTradeBean.getStatus()) || myTradeBean.getStatus().equals("0")){//持仓
//            list = myDao.getHoldTrade(myTradeBean);
//        }else{//平仓
//            list = myDao.getSellTrade(myTradeBean);
//        }
        list = myDao.getSellTrade(myTradeBean);
        ret.put("list",list);
        return ret;
    }

    /**
     * 资金管理
     * @param moneyBean
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> moneyManager(MoneyBean moneyBean){
        Map<String,Object> ret = new HashedMap();
        moneyBean.setUserId(AuthContext.getUserId());
        ret.put("moneyRecord",myDao.getUserMoneyRecord(moneyBean));
        return ret;
    }

    /**
     * 绑卡
     * @param bankCardBean
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity addBankCard(BankCardBean bankCardBean){
        String userId = AuthContext.getUserId();
        BankCardBean bankCardBean1 = myDao.alreadyHasCard(userId);
        if(!StringUtils.isEmpty(bankCardBean1)){
            return new ResponseErrorEntity(StatusCode.BIND_BANK_CARD_ERROR, HttpStatus.BAD_REQUEST);
        }
        bankCardBean.setId(Utils.uuid());
        bankCardBean.setUserId(AuthContext.getUserId());
        myDao.addBankCard(bankCardBean);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改绑定银行卡信息
     * @param bankCardBean
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity updateBankCard(BankCardBean bankCardBean){
        Map<String,Object> ret = new HashedMap();
        bankCardBean.setUserId(AuthContext.getUserId());
        if(myDao.updateBankCard(bankCardBean) != 1){
            return new ResponseErrorEntity(StatusCode.UPDATE_BANK_CARD_ERROR, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取银行卡信息
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String,Object> getBankCard(){
        Map<String,Object> ret = new HashedMap();
        ret.put("cardInfo",myDao.getBankCard(AuthContext.getUserId()));
        return ret;
    }

    /**
     * 查询军团信息
     * @return
     */
    public Map<String, Object> queryCorpsList(){
        Map<String, Object> ret = new HashedMap();
        ret.put("corps", corpsDao.queryCorpsList(AuthContext.getUserId()));
        return ret;
    }
}
