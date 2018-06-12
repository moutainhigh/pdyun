package com.rmkj.microcap.modules.user.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.common.modules.sys.service.Ml3AgentRoleService;
import com.rmkj.microcap.common.modules.sys.service.Ml3PermissionService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.modules.Ml3MemberUnits.service.Ml3MemberUnitsService;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private Ml3AgentService ml3AgentService;
    @Autowired
    private Ml3AgentRoleService ml3AgentRoleService;
    @Autowired
    private Ml3MemberUnitsService ml3MemberUnitsService;
    @Autowired
    private IUserDao userDao;

    /**
     * 列表页面
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String listPage() {
        return "/user/user_list";
    }

    /**
     * 列表数据，查询代理下所有的会员
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public Map<String,Object> listData(UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        entity.setAgentInviteCode(ml3AgentBean.getAgentInviteCode());
        List<UserBean> entityList = userService.queryCustomerList(entity);

        GridPager g = new GridPager();
        g.setRows(entityList);
        g.setTotal(MybatisPagerInterceptor.getTotal());

        Map<String,Object> map = new HashedMap();
        map.put("g", g);

        if(entity.getPage() == 1){
            UserBean userBean = userService.queryCustomerTotal(entity);
            BigDecimal bigDecimal = userDao.queryUserTotalTradeMoneyAgent(entity);
            userBean.setTotalTradeCount(bigDecimal == null ? new BigDecimal(0) : bigDecimal);
            map.put("queryCustomerTotal", userBean == null ? null : userBean);
        }

        return map;
    }


    /**
     * 会员单位下的客户列表
     * @return
     */
    @RequestMapping(value = "/userinnerunits", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String userinnerunitsPage(Model info, Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id",ml3AgentBean.getUnitsId());
        info.addAttribute("agentList", ml3AgentService.findMl3AgentByUnitsId(ml3AgentBean.getUnitsId()));
        return "/user/userInnerUnits_list";
    }

    /**
     * 会员单位下的客户列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userinnerunits", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public Map<String,Object> userinnerunitsData(@Valid UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.userListInnerUnits(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        Map<String,Object> map = new HashedMap();
        map.put("g",g);
        UserBean userBean = userService.queryInnerUnitsTotal(entity);
        if(!CollectionUtils.isEmpty(entityList) && entity.getPage() == 1){
            BigDecimal bigDecimal = userDao.queryUserTotalTradeMoneyCenter(entity);
            userBean.setTotalTradeCount(bigDecimal == null ? new BigDecimal(0) : bigDecimal);
        }
        map.put("innerUserTotal",userBean);

        return map;
    }

    /**
     * 市场管理部下的客户列表
     * @return
     */
    @RequestMapping(value = "/useroperatecenter", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String userOprateCenterPage(Map<String, Object> model)
    {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id",ml3AgentBean.getCenterId());
        model.put("unitsList", ml3MemberUnitsService.findMemberUnitsByAgentId());
        return "/Ml3OperateCenter/userOperateCenter_list";
    }

    /**
     * 市场管理部下的客户列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/useroperatecenter", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public Map<String,Object> userOprateCenterPage(@Valid UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.userListOperateCenter(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        Map<String,Object> map = new HashedMap();
        map.put("g",g);
        UserBean userBean = userService.queryOperateCenterTotal(entity);
        if(entity.getPage() == 1 && !CollectionUtils.isEmpty(entityList)){
            BigDecimal bigDecimal = userDao.queryUserTotalTradeMoneyUnits(entity);
            userBean.setTotalTradeCount(bigDecimal == null ? new BigDecimal(0) : bigDecimal);
        }
        map.put("innerUserTotal",userBean);

        return map;
    }

    /************************* 分水岭 **************************/

    /**
     * 代理商下的客户列表页面
     * @return
     */
    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    public String userlistPage(String id, Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("agentInviteCode", ml3AgentBean.getAgentInviteCode());
        return "/user/user_userlist";
    }

    /**
     * 会员单位下的军团长列表页面
     * @return
     */
    @RequestMapping(value = "/juntuanInnerUnitslist", method = RequestMethod.GET)
    public String juntuanInnerUnitslistPage(String id,Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id",ml3AgentBean.getId());
        ArrayList<String> userIdList = ml3AgentService.getAllUserId();
        model.put("userIdList",userIdList);
        return "/user/juntuanInnerUnits_list";
    }
    /**
     * 会员单位下的军团长列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/juntuanInnerUnitslist", method = RequestMethod.POST)
    public GridPager juntuanInnerUnitslistData(@Valid UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.juntuanInnerUnitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    //开启军团长为代理商
    @RequestMapping(value = "/insertMl3Agent", method = RequestMethod.GET)
    public String addMl3AgentPage() {
        String id = request.getParameter("id");
        UserBean userBean = userService.get(id);
        request.setAttribute("userBean",userBean);
        ArrayList<String> agentList = ml3AgentService.getAllAgent();
        request.setAttribute("list",agentList);
        ArrayList<String> accountList = ml3AgentService.getAllAgentAccount();
        request.setAttribute("accountList",accountList);
        return "/user/juntuanMl3Agent_add";
    }
    //新增军团长为代理商
    @ResponseBody
    @RequestMapping(value = "/saveMl3Agent", method = RequestMethod.POST)
    public ExecuteResult saveMl3Agent(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        userService.insertMl3Agent(entity);
        return new ExecuteResult(StatusCode.OK);
    }


    /**
     * 会员单位炮兵团列表
     * @return
     */
    @RequestMapping(value = "/paobingInnerUnits", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String paobingInnerUnitsPage(String id,Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id",ml3AgentBean.getId());
        return "/user/paobingInnerUnits_list";
    }
    /**
     * 会员单位炮兵团列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/paobingInnerUnits", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public GridPager paobingInnerUnitsData(@Valid UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.paobingInnerUnitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
     * 会员单位骑兵团列表
     * @return
     */
    @RequestMapping(value = "/qibingInnerUnits", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String qibingInnerUnitsPage(String id,Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id",ml3AgentBean.getUnitsId());
        Ml3AgentRoleBean ml3AgentRoleBean =  ml3AgentRoleService.get(UserUtils.getUser().getId());
        String roleId = ml3AgentRoleBean.getRoleId();
        model.put("roleId",roleId);
        return "/user/qibingInnerUnits_list";
    }
    /**
     * 会员单位骑兵团列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qibingInnerUnits", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public GridPager qibingInnerUnitsData(@Valid UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.qibingInnerUnitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 会员单位步兵团列表
     * @return
     */
    @RequestMapping(value = "/bubingInnerUnits", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String bubingInnerUnitsPage(String id,Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id",ml3AgentBean.getUnitsId());
        Ml3AgentRoleBean ml3AgentRoleBean =  ml3AgentRoleService.get(UserUtils.getUser().getId());
        String roleId = ml3AgentRoleBean.getRoleId();
        model.put("roleId",roleId);
        return "/user/bubingInnerUnits_list";
    }
    /**
     * 会员单位步兵团列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bubingInnerUnits", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public GridPager bubingInnerUnitsData(@Valid UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.bubingInnerUnitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }





    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("user:add")
    public String addPage() {
         return "/user/user_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("user:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", userService.get(id));
        return "/user/user_edit";
    }

    /**
    * 保存
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("user:add")
    public ExecuteResult save(@Valid UserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        userService.insert(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 更新
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("user:edit")
    public ExecuteResult update(@Valid UserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        userService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("user:delete")
    public ExecuteResult delete(String id) {
        userService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    //详细信息展示
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(UserBean userBean, Map<String,Object> model){
        model.put("model", userService.info(userBean));
        return "/user/user_info";
    }
    //提现充值信息
    @RequestMapping(value = "/moneyRecord", method = RequestMethod.GET)
    public String moneyRecordPage(String id, Map<String, Object> model){
        model.put("id", id);
        return "/user/user_money_record";
    }

    //充值提现列表数据
    @ResponseBody
    @RequestMapping(value = "/moneyRecord", method = RequestMethod.POST)
    public GridPager moneyRecord(MoneyRecordBean moneyRecordBean){
        moneyRecordBean.setStart(evalStart(moneyRecordBean.getPage(), moneyRecordBean.getRows()));
        List<MoneyRecordBean> entityList = userService.queryMoneyRecordList(moneyRecordBean);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    //导出到Excel
    @RequestMapping("exportExcel")
    public void exportExcel(UserBean userBean, HttpServletResponse response){
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        userBean.setId(ml3AgentBean.getUnitsId());
        HSSFWorkbook wb = userService.exportExcel(userBean);
        ExcelUtils.exportExcel("客户资料", response, wb);
    }

    /**
     * 军团长列表页面
     * @return
     */
    @RequestMapping(value = "/juntuanlist", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String juntuanlistPage(String id,Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id",ml3AgentBean.getUserId());
        return "/user/juntuan_list";
    }
    /**
     * 军团长列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/juntuanlist", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public GridPager juntuanlistData(@Valid UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.juntuanList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
     * 获取当前军团长下的军团的成员
     * @return
     */
    @RequestMapping(value = "/getJunTuanList", method = RequestMethod.GET)
    public String getJunTuanPage(String id, Map<String, Object> model){
        model.put("id", id);
        return "/user/getJunTuan_list";
    }
    /**
     * 获取军团长下的军团的成员的列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getJunTuanList", method = RequestMethod.POST)
    public GridPager getJunTuanListData(UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.getJunTuanList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
     * 炮兵团列表
     * @return
     */
    @RequestMapping(value = "/paobinglist", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String paobingtuanlistPage(String id,Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id",ml3AgentBean.getUserId());
        return "/user/paobing_list";
    }
    /**
     * 炮兵团列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/paobinglist", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public GridPager paobingtuanlistData(UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.paobingList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
     * 骑兵团列表
     * @return
     */
    @RequestMapping(value = "/qibinglist", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String qibingtuanlistPage(Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("agentInviteCode",ml3AgentBean.getAgentInviteCode());
        return "/user/qibing_list";
    }
    /**
     * 骑兵团列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qibinglist", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public GridPager qibingtuanlistData(UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.qibingList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
     * 步兵团列表
     * @return
     */
    @RequestMapping(value = "/bubinglist", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String bubingtuanlistPage(Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("agentInviteCode",ml3AgentBean.getAgentInviteCode());
        return "/user/bubing_list";
    }
    /**
     * 步兵团列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bubinglist", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public GridPager bubingtuanlistData(UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.bubingList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

}
