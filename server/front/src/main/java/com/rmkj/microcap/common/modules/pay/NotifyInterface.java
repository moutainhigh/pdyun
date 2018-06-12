package com.rmkj.microcap.common.modules.pay;

/**
 * Created by renwp on 2017/5/27.
 */
public interface NotifyInterface {

    /**
     * 支付成功
     * @param serialNo
     * @param thirdSerialNo
     * @return
     */
    boolean success(String serialNo, String thirdSerialNo);

    /**
     * 支付失败
     * @param serialNo
     * @param thirdSerialNo
     * @return
     */
    boolean failure(String serialNo, String thirdSerialNo);

}
