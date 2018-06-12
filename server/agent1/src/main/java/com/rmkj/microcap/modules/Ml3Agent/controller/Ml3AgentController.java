package com.rmkj.microcap.modules.Ml3Agent.controller;

import com.beust.jcommander.Parameter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.shiro.SystemAuthorizingRealm;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.common.modules.sys.bean.Ml3RoleBean;
import com.rmkj.microcap.common.modules.sys.service.Ml3AgentRoleService;
import com.rmkj.microcap.common.modules.sys.service.Ml3RoleService;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.modules.upload.bean.UploadFile;
import com.rmkj.microcap.common.utils.Digests;
import com.rmkj.microcap.common.utils.Encodes;
import com.rmkj.microcap.common.utils.MD5Utils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.CoderService;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.modules.Ml3Agent.service.ZxingDecoderService;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.service.Ml3MemberUnitsService;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
import com.rmkj.microcap.modules.parameterSet.entity.ParameterSet;
import com.rmkj.microcap.modules.parameterSet.service.ParameterService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.ByteSource;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;

/**
* Created by Administrator on 2016-11-17.
*/
@Controller
@RequestMapping("/Ml3Agent")
public class Ml3AgentController extends BaseController {
    @Autowired
    private Ml3AgentService ml3AgentService;
    @Autowired
    private com.rmkj.microcap.common.modules.sys.service.Ml3AgentService ml3AgentService1;
    @Autowired
    private Ml3RoleService ml3RoleService;
    @Autowired
    private Ml3AgentRoleService ml3AgentRoleService;
    @Autowired
    private Ml3MemberUnitsService ml3MemberUnitsService;
    @Autowired
    private CoderService coderService;
    @Autowired
    private ZxingDecoderService zxingDecoderService;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private Ml3OperateCenterService ml3OperateCenterService;


    /**
     * 会员单位下的代理商列表页面
     *
     * @return
     */
    @RequestMapping(value = "/ml3agentinnerunits", method = RequestMethod.GET)
    public String agentlistPage(String id, Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getUnitsId());
        return "/Ml3Agent/Ml3AgentInnerUnits_list";
    }

    /**
     * 会员单位下的代理商列表数据
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ml3agentinnerunits", method = RequestMethod.POST)
    public GridPager agentlistData(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.getMl3AgentInnerUnits(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 账户信息会员单位
     *
     * @return
     */
    @RequestMapping(value = "/onelist", method = RequestMethod.GET)
    public String onelistPage(String id, Map<String, Object> model) {
        model.put("id", UserUtils.getUser().getId());
        return "/Ml3Agent/Ml3Agent_onelist";
    }

    //账户信息
    @ResponseBody
    @RequestMapping(value = "/onelist", method = RequestMethod.POST)
    public GridPager onelistData(Ml3MemberUnitsBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3MemberUnitsBean> entityList = ml3AgentService.getOneAgent(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 账户信息代理商
     *
     * @return
     */
    @RequestMapping(value = "/getone", method = RequestMethod.GET)
    public String getonePage(String id, Map<String, Object> model) {
        model.put("id", UserUtils.getUser().getId());
        return "/Ml3Agent/Ml3Agent_getone";
    }

    //账户信息代理商
    @ResponseBody
    @RequestMapping(value = "/getone", method = RequestMethod.POST)
    public GridPager getoneData(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.getAgent(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 账户信息会员单位
     *
     * @return
     */
    @RequestMapping(value = "/getoneInnerUnits", method = RequestMethod.GET)
    public String getonePage1(String id, Map<String, Object> model) {
        model.put("id", UserUtils.getUser().getId());
        return "/Ml3Agent/Ml3Agent_getoneInnerUnits";
    }

    //账户信息会员单位
    @ResponseBody
    @RequestMapping(value = "/getoneInnerUnits", method = RequestMethod.POST)
    public GridPager getoneData1(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.getAgentInnerUnits(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 账户信息市场管理部
     *
     * @return
     */
    @RequestMapping(value = "/getoneOperateCenter", method = RequestMethod.GET)
    public String getoneCenterPage1(String id, Map<String, Object> model) {
        model.put("id", UserUtils.getUser().getId());
        return "/Ml3Agent/Ml3Agent_getoneOperateCenter";
    }

    //账户信息市场管理部
    @ResponseBody
    @RequestMapping(value = "/getoneOperateCenter", method = RequestMethod.POST)
    public GridPager getoneCenterData1(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.getAgentOperateCenter(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 修改代理商密码
     *
     * @return
     */
    @RequestMapping(value = "/editPwdByAgent", method = RequestMethod.GET)
    public String editPwdByAgentPage(String id, Map<String, Object> model) {
        model.put("model", ml3AgentService.get(UserUtils.getUser().getId()));
        return "/Ml3Agent/Ml3Agent_editPwdByAgent";
    }

    @ResponseBody
    @RequestMapping(value = "/update1", method = RequestMethod.POST)
    public ExecuteResult editPwdByAgent(@Valid Ml3AgentBean entity, Errors errors, Map<String, Object> model) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.updatePwdByAgent(entity);
//        System.out.println("---------------------"+);
        model.put("msg", new ExecuteResult(StatusCode.OK).getMsg());
//        request.setAttribute("msg",new ExecuteResult(StatusCode.OK).getMsg());
        return new ExecuteResult(StatusCode.OK);
    }

    /*********************************************************************************/


    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage(String id, Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getUnitsId());
        return "/Ml3Agent/Ml3Agent_list";
    }

    /**
     * 列表数据
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GridPager listData(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    @RequestMapping(value = "/updateAgent", method = RequestMethod.GET)
    public String updateAgent(String ids, Model model){
        model.addAttribute("model", ids);
        Ml3AgentBean ml3AgentBean = new Ml3AgentBean();
        ml3AgentBean.setId(ids);

        model.addAttribute("agent", ml3AgentService.getAgent(ml3AgentBean).get(0));
        return "/Ml3Agent/agent_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateMl3AgentPercent", method = RequestMethod.POST)
    public ExecuteResult updateAgent(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentService.updateMl3AgentPercnt(entity);
        return new ExecuteResult(StatusCode.OK);
    }


    //修改代理商的密码
    @RequestMapping(value = "/editMl3AgentPwd", method = RequestMethod.GET)
    public String editMl3AgentPwdPage(String id, Map<String, Object> model) {
        model.put("model", id);
        return "/Ml3Agent/agentPwd_edit";
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

    @ResponseBody
    @RequestMapping(value = "/updateMl3AgentPwd", method = RequestMethod.POST)
    public ExecuteResult editMl3AgentPwd(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.updatePwd(entity);
        return new ExecuteResult(StatusCode.OK);
    }


    /**
     * 收益列表页面
     *
     * @return
     */
    @RequestMapping(value = "/shouyi", method = RequestMethod.GET)
    public String ShouYiPage(String id, Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getUnitsId());
        return "/Ml3Agent/Ml3Agent_ShouYiList";
    }

    //收益列表数据
    @ResponseBody
    @RequestMapping(value = "/shouyi", method = RequestMethod.POST)
    public GridPager ShouYiData(Ml3AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.getShouYiList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 新增页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage() {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        List<Ml3MemberUnitsBean> muList = ml3MemberUnitsService.muList(ml3AgentBean.getCenterId());
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
     * 保存会员单位用户
     */
    @ResponseBody
    @RequestMapping(value = "/saveMemberUser", method = RequestMethod.POST)
    public ExecuteResult saveMemberUser(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
//        String screen = request.getParameter("screen");//是否屏蔽军团用户手机号
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
//        if(screen.equals("是")){//屏蔽军团用户手机号
            entity.setCreateTime(new Date());
            entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
            entity.setStatus(0);
            entity.setId(Utils.uuid());
            entity.setCenterId(ml3AgentBean.getCenterId());
            ml3AgentService.insertMemberUser(entity);
            Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
            ml3AgentRoleBean.setAgentId(entity.getId());
            ml3AgentRoleBean.setRoleId("5");
            ml3AgentRoleService.insert(ml3AgentRoleBean);
//        }else{ //不屏蔽军团用户手机号
//            entity.setCreateTime(new Date());
//            entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
//            entity.setStatus(0);
//            entity.setId(Utils.uuid());
//            entity.setCenterId(ml3AgentBean.getCenterId());
//            ml3AgentService.insertMemberUser(entity);
//            //同时添加代理角色表
//            //首先得到新添加用户的roleType
//            Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
//            ml3AgentRoleBean.setAgentId(entity.getId());
//            ml3AgentRoleBean.setRoleId("5");
//            ml3AgentRoleService.insert(ml3AgentRoleBean);
//        }
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 保存
     *
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ExecuteResult save(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
//        List<Ml3AgentBean> list = ml3AgentService.getAllAgent();
//        for(int i = 0;i<list.size();i++){
//            if(entity.getMobile().equals(list.get(i).getMobile())){
//                return new ExecuteResult(StatusCode.MOBILE_FAILED);
//            }
//        }
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        entity.setUnitsId(ml3AgentBean.getUnitsId());
        entity.setRoleType(0);
        ml3AgentService.insert(entity);
        Ml3AgentBean ml3AgentBean1 = ml3AgentService.get(entity.getId());
        String code = ml3AgentBean1.getAgentInviteCode();//邀请码
        ml3AgentService.updateUserInviteCode(ml3AgentBean1);//新增代理同时修改用户表的邀请码
        //添加代理同时新增代理角色表
        Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
        ml3AgentRoleBean.setAgentId(entity.getId());
        Ml3RoleBean ml3RoleBean = new Ml3RoleBean();
        ml3RoleBean.setName("代理商");
        Ml3RoleBean ml3RoleBean1 = ml3RoleService.selectRoleIdByName(ml3RoleBean);
        ml3AgentRoleBean.setRoleId(ml3RoleBean1.getId());
        ml3AgentRoleService.insert(ml3AgentRoleBean);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 编辑页面  修改会员单位密码
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(String id, Map<String, Object> model) {
        model.put("model", ml3AgentService.get(UserUtils.getUser().getId()));
        return "/Ml3Agent/Ml3Agent_edit";
    }

    /**
     * 更新
     *
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ExecuteResult update(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ExecuteResult delete(String id) {
        ml3AgentService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    //开启合约
    @ResponseBody
    @RequestMapping(value = "/open", method = RequestMethod.POST)
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
    public ExecuteResult close(Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentService.close(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
    public String updatePwd(String id, Map<String, Object> model) {

        model.put("model", ml3AgentService.get(id));
        return "/Ml3Agent/Ml3Agent_updatePwd";
    }

    @ResponseBody
    @RequestMapping(value = "/updatePwd1", method = RequestMethod.POST)
    public ExecuteResult updatePwd1(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setSafePassword(SystemService.entryptPassword(entity.getSafePassword()));
        ml3AgentService.updatePwd(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    //查看客户列表页面
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String moneyRecordPage(String id, Map<String, Object> model) {
        model.put("id", id);
        return "/Ml3Agent/Ml3Agent_userList";
    }

    //查看客户列表数据
    @ResponseBody
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public GridPager moneyRecord(Ml3AgentBean ml3AgentBean) {
        ml3AgentBean.setStart(evalStart(ml3AgentBean.getPage(), ml3AgentBean.getRows()));
        List<Ml3AgentBean> entityList = ml3AgentService.userList(ml3AgentBean);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 会员单位后台下的账号信息修改
     *
     * @return
     */
    @RequestMapping(value = "/editInfo", method = RequestMethod.GET)
    public String hoeditPage(String id, Map<String, Object> model) {
        model.put("model", ml3AgentService.get(UserUtils.getUser().getId()));
        return "/Ml3Agent/Ml3Agent_editInfo";
    }

    /**
     * 会员单位后台下的账号信息修改
     *
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public ExecuteResult aupdate(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentService.updateInfo(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 代理商后台下的账号信息修改
     *
     * @return
     */
    @RequestMapping(value = "/editUnitsInfo", method = RequestMethod.GET)
    public String UnitsInfoPage(String id, Map<String, Object> model) {
        model.put("model", ml3AgentService.get(UserUtils.getUser().getId()));
        return "/Ml3Agent/Ml3Agent_editUnitsInfo";
    }

    /**
     * 代理商后台下的账号信息修改
     *
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUnitsInfo", method = RequestMethod.POST)
    public ExecuteResult UnitsInfoupdate(@Valid Ml3AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentService.updateInfo(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    //会员单位下的会员单位账号信息
    @RequestMapping(value = "/getUnitsInfo", method = RequestMethod.GET)
    public String getUnitsInfoPage(String id, Map<String, Object> model) {
        Ml3AgentBean bean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", bean.getUnitsId());
        return "/Ml3Agent/Ml3Agent_getUnitsInfo";
    }

    //会员单位下的会员单位账号信息
    @ResponseBody
    @RequestMapping(value = "/getUnitsInfo", method = RequestMethod.POST)
    public GridPager getUnitsInfoData(Ml3MemberUnitsBean memberUnitsBean) {
        memberUnitsBean.setStart(evalStart(memberUnitsBean.getPage(), memberUnitsBean.getRows()));
        List<Ml3MemberUnitsBean> entityList = ml3AgentService.getUnitsInfo(memberUnitsBean);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 会员单位后台下的会员单位管理信息修改
     *
     * @return
     */
    @RequestMapping(value = "/editUnitsInfo01", method = RequestMethod.GET)
    public String editUnitsInfoPage(String id, Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("model", ml3MemberUnitsService.get(ml3AgentBean.getUnitsId()));
        return "/Ml3Agent/Ml3Agent_editUnitsInfo01";
    }

    /**
     * 会员单位后台下的会员单位管理信息修改
     *
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUnitsInfo01", method = RequestMethod.POST)
    public ExecuteResult editUnitsInfo(@Valid Ml3MemberUnitsBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3MemberUnitsService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 会员单位后台下的会员单位管理信息修改
     *
     * @return
     */
    @RequestMapping(value = "/generalizedCode", method = RequestMethod.GET)
    public String generalizedCodePage(Model model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        Ml3MemberUnitsBean ml3MemberUnitsBean = ml3MemberUnitsService.get(ml3AgentBean.getUnitsId());

        String url = parameterService.findParameterSetList().getQrCodeUrl();
        if(!StringUtils.isBlank(url)) {
            url = Utils.formatStr(url, ml3MemberUnitsBean.getTwoLevelDomain(), ml3AgentBean.getAgentInviteCode(),"");
        }
        model.addAttribute("url", url);
        return "/Ml3Agent/Ml3Agent_generalizedCode";
    }

    @RequestMapping(value="/zxingdecode", method= RequestMethod.GET)
    public ModelAndView zxingdecode(@RequestParam("realImgPath") String realImgPath, HttpSession session) {
        String uploadPath = "/images" ;
        String realUploadPath = session.getServletContext().getRealPath(uploadPath) ;
        String imgPath = realUploadPath+"/"+realImgPath ;
        String result = zxingDecoderService.zxingdecode(imgPath) ;

        ModelAndView ret = new ModelAndView() ;
        ret.addObject("result", result) ;
        ret.setViewName("/Ml3Agent/Ml3Agent_generalizedCode");

        return ret ;
    }

    @RequestMapping(value="/zxingcoder", method=RequestMethod.GET)
    public ModelAndView watermark(HttpSession session) throws Exception {
        System.out.println("====================");
        String uploadPath = "/images" ;
        String realUploadPath = session.getServletContext().getRealPath(uploadPath) ;
        String imageName = "12345"+".png" ;
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        Ml3MemberUnitsBean ml3MemberUnitsBean = ml3MemberUnitsService.get(ml3AgentBean.getUnitsId());
        String twoLevelDomain = ml3MemberUnitsBean.getTwoLevelDomain();//二级域名
        String userid = UserUtils.getUser().getId();
        String agentInviteCode = ml3AgentBean.getAgentInviteCode();
        //生成内容
        String contents = "http://"+twoLevelDomain+".rongmei.com/front/wap/reg?agentInviteCode="+userid+"&userId="+agentInviteCode;
        int width = 300;
        int height = 300;
        String zxingImage = coderService.encode(contents, width, height, uploadPath, realUploadPath, imageName);
//
        ModelAndView ret = new ModelAndView() ;
        ret.addObject("imageUrl", zxingImage) ;
        ret.addObject("imageName", imageName) ;
        ret.setViewName("/Ml3Agent/Ml3Agent_generalizedCode");

        return ret ;
    }
    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public String qrcodePage()throws  Exception {
        return "/Ml3Agent/Ml3Agent_qrcode";
    }
    @RequestMapping(value = "/qrcode1", method = RequestMethod.GET)
    public String qrcode1Page(Map<String,Object> model)throws  Exception {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        //查询当前代理会员的会员单位
        Ml3MemberUnitsBean ml3MemberUnitsBean = ml3MemberUnitsService.get(ml3AgentBean.getUnitsId());

        //读取本地图片输入流
        File file = new File(ProjectConstants.AGENT_QRCODE + ml3MemberUnitsBean.getId() + ProjectConstants.AGENT_QRCODE_TYPE);
        //File file = new File(ProjectConstants.AGENT_QRCODE + UserUtils.getUser().getId() + ProjectConstants.AGENT_QRCODE_TYPE);
        FileInputStream inputStream = null;
        if(file.exists()){
           // inputStream = new FileInputStream(ProjectConstants.AGENT_QRCODE + UserUtils.getUser().getId() + ProjectConstants.AGENT_QRCODE_TYPE);
            inputStream = new FileInputStream(ProjectConstants.AGENT_QRCODE + ml3MemberUnitsBean.getId() + ProjectConstants.AGENT_QRCODE_TYPE);
            int i = inputStream.available();
            //byte数组用于存放图片字节数据
            byte[] buff = new byte[i];
            inputStream.read(buff);
            //记得关闭输入流
            inputStream.close();
            //设置发送到客户端的响应内容类型
            response.setContentType("image/*");
            OutputStream out = response.getOutputStream();
            out.write(buff);
            //关闭响应输出流
            out.close();
        }else{
            return "no";
        }

        return "ok";
    }
    @RequestMapping(value = "/saveqrcodes", method = RequestMethod.POST)
    public String showImages(@RequestParam("file") CommonsMultipartFile file) throws Exception{
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        //查询当前代理会员的会员单位
        Ml3MemberUnitsBean ml3MemberUnitsBean = ml3MemberUnitsService.get(ml3AgentBean.getUnitsId());

        //用来检测程序运行时间
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename());
        //在本地创建一个文件夹存放上传的会员单位二维码的图片

        File file1 = new File(ProjectConstants.AGENT_QRCODE);
        if  (!file1.exists() && !file1.isDirectory())
        {
            //此时目录不存在，就创建一个新的文件夹
//            System.out.println("//不存在");
            file1.mkdir();
        } else
        {
            //此时目录存在
//            System.out.println("//目录存在");
        }
        try {
            //获取输出流
            //OutputStream os=new FileOutputStream(ProjectConstants.AGENT_QRCODE + UserUtils.getUser().getId() + ProjectConstants.AGENT_QRCODE_TYPE);
            OutputStream os=new FileOutputStream(ProjectConstants.AGENT_QRCODE + ml3MemberUnitsBean.getId() + ProjectConstants.AGENT_QRCODE_TYPE);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法一的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "/Ml3Agent/Ml3Agent_qrcode";
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
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        entity.setCenterId(ml3AgentBean.getCenterId());
        List<Ml3AgentBean> entityList = ml3AgentService.innerUnitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    @ResponseBody
    @RequestMapping(value = "/findMl3AgentById", method = RequestMethod.GET)
    public Map<String, Object> findMl3AgentByUnitsId(@RequestParam("unitsId") String unitsId){
        Map<String, Object> result = new HashedMap();
        result.put("agentList", ml3AgentService.findMl3AgentByUnitsId(unitsId));
        return result;
    }

}
