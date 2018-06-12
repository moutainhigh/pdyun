package com.rmkj.microcap.modules.chanong.index.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.index.entity.Broadcast;
import com.rmkj.microcap.modules.index.entity.ParameterSet;

import java.math.BigDecimal;
import java.util.List;

@DataSource
public interface IndexDao {
    String getCurPrice(String id);

    List<Broadcast> getNewsFlash(String type);

    Broadcast getNewsById(String id);
}