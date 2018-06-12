package com.rmkj.microcap.test.weifutong;

import com.rmkj.microcap.modules.moneyrecord.service.WeiFuTongService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by renwp on 2017/3/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-*.xml")
public class WeiFuTongTest {

    @Autowired
    private WeiFuTongService weiFuTongService;

    @Test
    public void t1(){
        weiFuTongService.reviewPass("0004bb34a3ed4f49a0334a8c80d90839");
    }

    @Test
    public void t2(){
        weiFuTongService.checkOutResult();
    }
}
