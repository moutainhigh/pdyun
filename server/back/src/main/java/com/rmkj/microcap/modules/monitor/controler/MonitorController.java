package com.rmkj.microcap.modules.monitor.controler;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.modules.monitor.entity.DataSummary;
import com.rmkj.microcap.modules.monitor.entity.TodayDataSummary;
import com.rmkj.microcap.modules.monitor.service.MonitorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/9/27.
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController extends BaseController {

    @Autowired
    private MonitorService monitorService;

    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @RequiresPermissions("monitor")
    public String queryAllPage(){
        return "/monitor/queryAll";
    }

    @RequestMapping(value = "/queryToday", method = RequestMethod.GET)
    @RequiresPermissions("monitor")
    public String queryTodayPage(){
        return "/monitor/queryToday";
    }

    @RequestMapping(value = "/queryAll", method = RequestMethod.POST)
    @RequiresPermissions("monitor")
    @ResponseBody
    public DataSummary queryAll(){
        return monitorService.queryAll();
    }

    @RequestMapping(value = "/queryAllDateSection", method = RequestMethod.POST)
    @RequiresPermissions("monitor")
    @ResponseBody
    public DataSummary queryAllDateSection(DataSummary dataSummary){
        return monitorService.queryAllSectionDate(dataSummary);
    }

    @RequestMapping(value = "/queryToday", method = RequestMethod.POST)
    @RequiresPermissions("monitor")
    @ResponseBody
    public TodayDataSummary queryToday(){
        return monitorService.queryToday();
    }

//    @RequestMapping(value = "/queryDateSection", method = RequestMethod.POST)
//    @RequiresPermissions("monitor")
//    @ResponseBody
//    public TodayDataSummary queryDateSection(TodayDataSummary todayDataSummary){
//        return monitorService.queryDateSection(todayDataSummary);
//    }
}
