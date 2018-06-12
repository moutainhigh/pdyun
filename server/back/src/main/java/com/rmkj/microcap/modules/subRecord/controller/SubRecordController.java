package com.rmkj.microcap.modules.subRecord.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.service.SysUserService;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.service.Ml3MemberUnitsService;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.subGoods.entity.SubGoods;
import com.rmkj.microcap.modules.subRecord.entity.SubRecordBean;
import com.rmkj.microcap.modules.subRecord.service.SubRecordService;
import com.rmkj.microcap.modules.trade.entity.Units;
import com.rmkj.microcap.modules.trade.service.TradeStatisticsService;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.user.service.UserService;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-10-17.
*/
@Controller
@RequestMapping("/sub/record")
public class SubRecordController extends BaseController{
    @Autowired
    private SubRecordService subRecordService;

    /**
     * 认购明细
     * @param subRecordBean
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addSubRecord", method = RequestMethod.POST)
    public ExecuteResult addSubRecord(SubRecordBean subRecordBean, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        return subRecordService.addSubRecord(subRecordBean);
    }

    /**
     * 列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GridPager listData(SubRecordBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<SubRecordBean> entityList = subRecordService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

}
