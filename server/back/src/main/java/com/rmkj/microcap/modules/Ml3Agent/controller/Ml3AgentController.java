package com.rmkj.microcap.modules.Ml3Agent.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.service.Ml3MemberUnitsService;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
import com.rmkj.microcap.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.modules.sys.service.Ml3AgentRoleService;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.user.entity.UserShiftMl3Agent;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.Mode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-11-17.
*/
@Controller
@RequestMapping("/Ml3Agent")
public class Ml3AgentController extends BaseController {
    @Autowired
    private Ml3AgentService ml3AgentService;
    @Autowired
    private Ml3MemberUnitsService ml3MemberUnitsService;
    @Autowired
    private Ml3OperateCenterService ml3OperateCenterService;
    @Autowired
    private Ml3AgentRoleService ml3AgentRoleService;
    @Autowired
    private UserService userService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent")
    public String listPage() {
        return "/Ml3Agent/Ml3Agent_list";
    }
    /**
     * 会员用户列表页面
     * @return
     */
    @RequestMapping(value = "/units", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent")
    public String unitsListPage() {
        return "/Ml3Agent/units_list";
    }
    @ResponseBody
    @RequestMapping(value = "/units", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public GridPager unitsListData(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.unitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
     * 会员单位管理用户列表页面
     * @return
     */
    @RequestMapping(value = "/innerUnitsList", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent")
    public String innerUnitsListPage() {
        return "/Ml3Agent/innerUnits_list";
    }
    @ResponseBody
    @RequestMapping(value = "/innerUnitsList", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public GridPager innerUnitsListData(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.innerUnitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    //市场管理部用户列表页面
    @RequestMapping(value = "/oc", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent")
    public String OcListPage() {
        return "/Ml3Agent/oc_list";
    }
    @ResponseBody
    @RequestMapping(value = "/oc", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public GridPager OcListData(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.centerList(entity);
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
    @RequiresPermissions("Ml3Agent:add")
    public String addPage() {
        List<Ml3MemberUnitsBean> muList = ml3MemberUnitsService.muList();
        List<Ml3OperateCenterBean> ocList = ml3OperateCenterService.OcList();
        request.setAttribute("list",muList);
        request.setAttribute("list1",ocList);
        ArrayList<String> agentList = ml3AgentService.getAgentMobile();
        request.setAttribute("mobileList",agentList);
        ArrayList<String> accountList = ml3AgentService.getAgentAccount();
        request.setAttribute("accountList",accountList);
         return "/Ml3Agent/Ml3Agent_add";
    }
    /**
     * 新增会员单位用户页面
     * @return
     */
    @RequestMapping(value = "/addUnits", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent:addUnits")
    public String addUnitsPage() {
        List<Ml3MemberUnitsBean> muList = ml3MemberUnitsService.muList();
        request.setAttribute("list",muList);
        return "/Ml3Agent/Ml3AgentUnits_add";
    }
    /**
     * 新增市场管理部用户页面
     * @return
     */
    @RequestMapping(value = "/addOc", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent:addOc")
    public String addOcPage() {
        List<Ml3MemberUnitsBean> muList = ml3MemberUnitsService.muList();
        request.setAttribute("list",muList);
        return "/Ml3Agent/Ml3AgentOc_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", ml3AgentService.get(id));
        List<Ml3MemberUnitsBean> muList = ml3MemberUnitsService.muList();
        List<Ml3OperateCenterBean> ocList = ml3OperateCenterService.OcList();
        request.setAttribute("list",muList);
        request.setAttribute("list1",ocList);
        return "/Ml3Agent/Ml3Agent_edit";
    }
    /**
     * 编辑会员单位用户页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/editUnits", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent:editUnits")
    public String editUnitsPage(String id,Map<String,Object> model) {
        model.put("model", ml3AgentService.get(id));
        List<Ml3MemberUnitsBean> muList = ml3MemberUnitsService.muList();
        request.setAttribute("list",muList);
        return "/Ml3Agent/Ml3AgentUnits_edit";
    }
    /**
     * 编辑市场管理部用户页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/editOc", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent:editOc")
    public String editOcPage(String id,Map<String,Object> model) {
        model.put("model", ml3AgentService.get(id));
        List<Ml3MemberUnitsBean> muList = ml3MemberUnitsService.muList();
        request.setAttribute("list",muList);
        return "/Ml3Agent/Ml3AgentOc_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public GridPager listData(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
    * 保存
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent:add")
    public ExecuteResult save(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
//        String screen = request.getParameter("screen");//是否屏蔽军团用户手机号
//        if(screen.equals("是")){//屏蔽军团用户手机号
            entity.setCreateTime(new Date());
            entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
            entity.setStatus(0);
            entity.setRoleType(1);
            Ml3MemberUnitsBean unitsBean = ml3MemberUnitsService.get(entity.getUnitsId());
            entity.setCenterId(unitsBean.getCenterId());
            ml3AgentService.insert(entity);
            Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
            ml3AgentRoleBean.setAgentId(entity.getId());
            ml3AgentRoleBean.setRoleId("5");
            ml3AgentRoleService.insert(ml3AgentRoleBean);
//        }else{ //不屏蔽军团用户手机号
//            entity.setCreateTime(new Date());
//            entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
//            entity.setStatus(0);
//            entity.setRoleType(1);
//            ml3AgentService.insert(entity);
//            //同时添加代理角色表
//            //首先得到新添加用户的roleType
//            Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
//            ml3AgentRoleBean.setAgentId(entity.getId());
//            ml3AgentRoleBean.setRoleId("2");
//            ml3AgentRoleService.insert(ml3AgentRoleBean);
//        }
        return new ExecuteResult(StatusCode.OK);
    }
    /**
     * 保存会员单位用户
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveUnits", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent:addUnits")
    public ExecuteResult saveUnits(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.insertUnits(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 保存市场管理部用户
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveOc", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent:addOc")
    public ExecuteResult saveOc(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.insertOc(entity);
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
    @RequiresPermissions("Ml3Agent:edit")
    public ExecuteResult update(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    //修改会员单位
    @ResponseBody
    @RequestMapping(value = "/updateUnits", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent:editUnits")
    public ExecuteResult updateUnits(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.updateUnits(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //修改市场管理部
    @ResponseBody
    @RequestMapping(value = "/updateOc", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent:editOc")
    public ExecuteResult updateOc(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.updateOc(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent:delete")
    public ExecuteResult delete(String id) {
        ml3AgentService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/deleteUnits", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent:deleteUnits")
    public ExecuteResult deleteUnits(String id) {
        ml3AgentService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    //开启合约
    @ResponseBody
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public ExecuteResult open(Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentService.open(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //关闭合约
    @ResponseBody
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public ExecuteResult close(Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        Ml3AgentBean ml3AgentBean = ml3AgentService.getMl3AgentByUserId(entity.getId());
        ml3AgentRoleService.delete(ml3AgentBean.getId());//删除代理角色表的记录
        ml3AgentService.close(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //会员单位管理下的用户状态启用
    @ResponseBody
    @RequestMapping(value = "/open1", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public ExecuteResult open1(Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
//        Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
//        ml3AgentRoleBean.setAgentId(entity.getId());
//        ml3AgentRoleBean.setRoleId(Constants.ML3_AGENT_ROLE.AGENTID);
//        ml3AgentRoleService.insert(ml3AgentRoleBean);
        ml3AgentService.open(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //会员单位管理下的用户状态停用
    @ResponseBody
    @RequestMapping(value = "/close1", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public ExecuteResult close1(Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        //ml3AgentRoleService.delete(entity.getId());//删除代理角色表的记录
        ml3AgentService.close1(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //修改密码
    @RequestMapping(value = "/updPwd", method = RequestMethod.GET)
    public String updPwd(String id, Map<String,Object> model){
        model.put("model", ml3AgentService.get(id));
        return "/Ml3Agent/Ml3Agent_updPwd";
    }
    @ResponseBody
    @RequestMapping(value = "/updPwd1", method = RequestMethod.POST)
    public ExecuteResult updPwd1(Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.updPwd(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //代理商列表
    @RequestMapping(value = "/agentlist", method = RequestMethod.GET)
    @RequiresPermissions("Ml3Agent")
    public String agentlistPage(Model model) {
        model.addAttribute("memberUnits", ml3MemberUnitsService.queryList(new Ml3MemberUnitsBean()));
        return "/Ml3Agent/juntuanMl3Agent_list";
    }
    /**
     * 代理商列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/agentlist", method = RequestMethod.POST)
    @RequiresPermissions("Ml3Agent")
    public GridPager agentlistData(@Valid  UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = ml3AgentService.dailishangList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    @ResponseBody
    @RequestMapping(value = "/agentTotal", method = RequestMethod.POST)
    public Map<String, Object> queryTotalUserByUnits(@Valid UserBean userBean){
        Map<String, Object> result = new HashedMap();
        result.put("totalUser", ml3AgentService.queryTotalUserByUnits(userBean));
        return result;
    }
    //修改代理商的密码
    @RequestMapping(value = "/editMl3AgentPwd", method = RequestMethod.GET)
    public String editjuntuanPwdPage(String id,Map<String,Object> model) {
        model.put("model",userService .get(id));
        return "/Ml3Agent/agentPwd_edit";
    }
    @ResponseBody
    @RequestMapping(value = "/updateMl3AgentPwd", method = RequestMethod.POST)
    public ExecuteResult updatejuntuanPwd(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.updMl3AgentPwd(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 平移客户
     * @param ids
     * @param model
     * @return
     */
    @RequestMapping(value = "/findMl3AgentUser")
    public String findMl3AgentUserList(String ids, Model model){
        if(StringUtils.isBlank(ids)){
            return null;
        }
        model.addAttribute("ids", ids);
        ml3AgentService.findMl3AgentUserList(model);
        return "/Ml3Agent/Ml3AgentUser_select_list";
    }

    @ResponseBody
    @RequestMapping(value = "updateUserAgentInviteCode", method = RequestMethod.POST)
    public ExecuteResult updateUserAgentInviteCode(UserShiftMl3Agent entity, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return ml3AgentService.updateUserAgentInviteCode(entity);
    }

    @RequestMapping(value = "/findMl3AgentById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findMl3AgentByUnitsId(@RequestParam("unitsId") String unitsId){
        return ml3AgentService.findMl3AgentByUnitsId(unitsId);
    }

    @RequestMapping(value = "/updateReturnPercent", method = RequestMethod.GET)
    public String updateReturnPercent(String id, Model model){
        model.addAttribute("agent", ml3AgentService.get(id));
        return "/Ml3Agent/agent_edit_percent";
    }

    @ResponseBody
    @RequestMapping(value = "/updateMl3AgentPercent", method = RequestMethod.POST)
    public ExecuteResult updateReturnPercent(@Valid Ml3AgentBean ml3AgentBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return ml3AgentService.updateReturnPercent(ml3AgentBean);
    }

    @RequestMapping(value = "/updateMl3AgentInfo", method = RequestMethod.GET)
    public String updateMl3AgentInfo(String id, Map<String, Object> model) {
        model.put("model", ml3AgentService.getMl3AgentInfo(id));
        return "/Ml3Agent/agentInfo_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/updMl3AgentUserInfo", method = RequestMethod.POST)
    public ExecuteResult updMl3AgentUserInfo( Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentService.updMl3AgentUserInfo(entity);
        return new ExecuteResult(StatusCode.OK);
    }
}
