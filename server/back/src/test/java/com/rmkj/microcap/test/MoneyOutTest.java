package com.rmkj.microcap.test;

import com.rmkj.microcap.common.modules.money.out.yizhifu.MoneyService;
import com.rmkj.microcap.common.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by renwp on 2017/1/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-*.xml")
public class MoneyOutTest {

    @Autowired
    MoneyService moneyOutService;

    @Test
    public void t1(){
        moneyOutService.queryOverMoney();
    }

    @Test
    public void t2(){
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<balancemessage>\n" +
                "<messagehead>\n" +
                "<status>[v_status]</status>\n" +
                "<statusdesc>[v_desc]</statusdesc>\n" +
                "<mid>[v_mid]</mid></messagehead>\n" +
                "<messagebody>\n" +
                "<balance>[v_balance]</balance>\n" +
                "<sign>[v_sign]</sign>\n" +
                "</messagebody>\n" +
                "</balancemessage>";
        System.out.println(moneyOutService.sub(str, "messagebody"));
    }

    // 分帐信息总行数|分帐总金额|批次号$ 收方帐号 1 | 收方帐户名 1 | 收方开户行 1 |3
    // 收方省份 1 | 收方城市 1 | 付款金额 1 | 客户标识 1|联行号 1
    @Test
    public void t3(){
        String v_data = "1|20.00|".concat(moneyOutService.batchNo()).concat("$41001610020052520937|张三|中国建设银行股份有限公司郑州东区分行|河南省|郑州市|20.00|20160829|105491001646");
        System.out.println(v_data);
//        v_data = "5|750.00|10535-20170104-163755$5400001|张三|中国工商银行股份有限公司北京东四" +
//                "南支行|北京市|北京市|400.00|2001|102100000101$5400002|李四|中国工商银行股份有限" +
//                "公司北京安定门支行|北京市|北京市|200.00|2002|102100000110$5400003|张三|中国建设" +
//                "银行北京阜成路支行|北京市|北京市|10.00|2001|105100003120$5400004|张三|招商银行" +
//                "股份有限公司北京金融街支行|北京市|北京市|40.00|2001|308100005264$5400005|王五|" +
//                "中国工商银行长沙市南门口支行|湖南省|长沙市|100.00|2003|102551000131";
        moneyOutService.batchOut(v_data);
    }

    @Test
    public void t4(){
        System.out.println(moneyOutService.batchNo());
    }

    @Test
    public void t5(){
        System.out.println(Utils.uuid());
    }

    @Test
    public void t6(){
        moneyOutService.initBankNo();
    }

    @Test
    public void t7(){
        String s = moneyOutService.queryResult("1f68849b3f1348c2867d38b298d52bcd");
        System.out.println(s);
    }

}
