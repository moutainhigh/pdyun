package com.rmkj.microcap.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.cache.CacheFacade;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinService;
import com.rmkj.microcap.common.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by renwp on 2016/11/15.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring-*.xml")
public class SpringTest {

//    @Autowired
//    private WeiXinService weiXinService;
//
//    @Autowired
//    private WeiXinMenuService1 weiXinMenuService1;
//
//    @Before
//    public void t(){
//        CacheFacade.set(Constants.WeiXin.TOKEN,
//                "pwUX10rR4BfT5YWnh6vv9TYuQ8RrQGxBeC3rErf2iJrwuD3vfF1lX3Qq_5mKV_q8XfjyeDGAJLpQHA5UT899UNOsARNuXTM8E29BUceEgFQzT145yARdEQrHRtH3LVpsRKNdAEABUX",
//                120000);
//    }
//
//    @Test
//    public void te(){
//        weiXinService.initToken();
//        System.out.println((String)CacheFacade.getObject(Constants.WeiXin.TOKEN));
//    }
//
//    /**
//     * 查询菜单
//     */
//    @Test
//    public void te1(){
//        System.out.println(JSONArray.toJSONString(weiXinMenuService1.query()));
//    }
//
//    /**
//     * 新增一级菜单
//     */
//    @Test
//    public void te3(){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "测试");
//        System.out.println(weiXinMenuService1.create(jsonObject, "add", null));
//    }
//
//    /**
//     * 修改一级菜单
//     */
//    @Test
//    public void te4(){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "我们112222");
//        // 修改名字
//        jsonObject.put("new_name", "2222");
//        System.out.println(weiXinMenuService1.create(jsonObject, "update", null));
//    }
//
//    /**
//     * 删除一级菜单
//     */
//    @Test
//    public void te5(){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "3333");
//        System.out.println(weiXinMenuService1.create(jsonObject, "delete", null));
//    }
//
//    /**
//     * 新增2级菜单
//     */
//    @Test
//    public void te6(){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "点我1");
//
////        jsonObject.put("url", "http://www.baidu.com");
//
//        jsonObject.put("type", "click");
//        jsonObject.put("content", "hello world");
//
////        jsonObject.put("type", "media_id");
////        jsonObject.put("media_id", "xH1I5JSamgonMLXtqebhHqxrZbl55rkLQzt_qDBEPVk");
//        System.out.println(weiXinMenuService1.create(jsonObject, "add", "测试"));
//    }
//
//    /**
//     * 修改2级菜单
//     */
//    @Test
//    public void te7(){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "点我1");
////        jsonObject.put("url", "http://www.baidu.com/1");
//        // 修改名字
////        jsonObject.put("new_name", "33333");
//
//        jsonObject.put("type", "click");
//        jsonObject.put("key", "4862a954ed5740dfbeaa09caa0c87938");
//        jsonObject.put("content", "hello world 11111");
//
//        System.out.println(weiXinMenuService1.create(jsonObject, "update", "测试"));
//    }
//
//    /**
//     * 删除2级菜单
//     */
//    @Test
//    public void te8(){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "点我1");
//        System.out.println(weiXinMenuService1.create(jsonObject, "delete", "测试"));
//    }
//
//    /**
//     * 素材
//     */
//    @Test
//    public void te9(){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("type", "image");
//        jsonObject.put("media", "xH1I5JSamgonMLXtqebhHqxrZbl55rkLQzt_qDBEPVk");
//        weiXinMenuService1.createMedia(jsonObject);
//    }
//
//    /**
//     * 查询图片素材
//     */
//    @Test
//    public void te10(){
//        GridPager image = weiXinMenuService1.queryMedias("image", 0, 10);
//        System.out.println(JSON.toJSONString(image));
//    }
}
