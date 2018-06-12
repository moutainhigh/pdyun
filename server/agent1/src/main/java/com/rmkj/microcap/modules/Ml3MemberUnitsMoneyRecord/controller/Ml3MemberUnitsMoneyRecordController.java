package com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.controller;/**
 * Created by Administrator on 2017/5/9.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.service.Ml3MemberUnitsMoneyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.http.POST;

import java.util.List;

/**
 * @author k
 * @create -05-09-15:26
 **/
@Controller
@RequestMapping(value = "/Ml3MemberUnitsMoneyRecord")
public class Ml3MemberUnitsMoneyRecordController extends BaseController{

    @Autowired
    private Ml3MemberUnitsMoneyRecordService ml3MemberUnitsMoneyRecordService;

    @ResponseBody
    @RequestMapping(value = "/findMemberUnitsMoneyRecordList", method = RequestMethod.POST)
    public GridPager findMemberUnitsMoneyRecordList(Ml3MemberUnitsMoneyRecord entity){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        return ml3MemberUnitsMoneyRecordService.findMemberUnitsMoneyRecordList(entity);
    }
}
