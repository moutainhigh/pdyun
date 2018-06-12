package com.rmkj.microcap.modules.chanong.shop.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.index.entity.Broadcast;
import com.rmkj.microcap.modules.trade.entity.Contract;

import java.util.List;

@DataSource
public interface ShopDao {
    List<Contract> getContract();
}