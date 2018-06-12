package com.rmkj.microcap.modules.subGoods.entity;/**
 * Created by Administrator on 2018/4/26.
 */

import com.rmkj.microcap.common.bean.DataEntity;

/**
 * 认购商品规格
 * @author k
 * @create -04-26-17:51
 **/

public class SubGoodsSpec extends DataEntity{

    private String goodsId;
    private String subTotalNum;
    private String subMakeNum;
    private String subSendNum;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSubTotalNum() {
        return subTotalNum;
    }

    public void setSubTotalNum(String subTotalNum) {
        this.subTotalNum = subTotalNum;
    }

    public String getSubMakeNum() {
        return subMakeNum;
    }

    public void setSubMakeNum(String subMakeNum) {
        this.subMakeNum = subMakeNum;
    }

    public String getSubSendNum() {
        return subSendNum;
    }

    public void setSubSendNum(String subSendNum) {
        this.subSendNum = subSendNum;
    }
}
