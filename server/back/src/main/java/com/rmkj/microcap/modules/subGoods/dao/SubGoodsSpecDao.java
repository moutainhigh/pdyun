package com.rmkj.microcap.modules.subGoods.dao;/**
 * Created by Administrator on 2018/4/26.
 */

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.subGoods.entity.SubGoodsSpec;

/**
 * @author k
 * @create -04-26-17:54
 **/
@DataSource
public interface SubGoodsSpecDao {

    int insert(SubGoodsSpec subGoodsSpec);

    int updateByGoodsId(SubGoodsSpec subGoodsSpec);
}
