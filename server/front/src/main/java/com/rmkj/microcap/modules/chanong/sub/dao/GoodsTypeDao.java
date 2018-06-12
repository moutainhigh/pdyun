package com.rmkj.microcap.modules.chanong.sub.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.chanong.sub.bean.GoodsTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */
@DataSource
public interface GoodsTypeDao {

    List<GoodsTypeBean> findGoodsType();
}
