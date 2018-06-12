package com.rmkj.microcap.modules.user.controller;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.service.SysUserService;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.service.Ml3MemberUnitsService;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.subGoods.entity.SubGoods;
import com.rmkj.microcap.modules.trade.entity.Units;
import com.rmkj.microcap.modules.trade.service.TradeStatisticsService;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.user.service.UserService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private SysUserService sysUserService;

    @Autowired
    private Ml3MemberUnitsService ml3MemberUnitsService;

    @Autowired
    private Ml3OperateCenterService ml3OperateCenterService;

    @Autowired
    private IUserDao userDao;
    @Autowired
    private TradeStatisticsService tradeStatisticsService;

    @RequestMapping(value = "/subMoney", method = RequestMethod.GET)
    @ResponseBody
    public String subMoney(Model model){
        return userService.subMoney();
    }

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String listPage(Model model) {
        model.addAttribute("centerList", ml3OperateCenterService.queryList(null));
        model.addAttribute("unitsList", ml3MemberUnitsService.queryList(new Ml3MemberUnitsBean()));
        return "/user/user_list";
    }
    /**
     * 军团长列表页面
     * @return
     */
    @RequestMapping(value = "/juntuanlist", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String juntuanlistPage() {
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
    public GridPager juntuanlistData(UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        MybatisPagerInterceptor.useAutoGetPageTotal(false);
        List<UserBean> entityList = userService.juntuanList(entity);
        GridPager g = new GridPager();
        g.setTotal((int)userDao.juntuanListTotal(entity));
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
    //修改军团长密码
    @RequestMapping(value = "/editjuntuanPwd", method = RequestMethod.GET)
    @RequiresPermissions("user:editjuntuanPwd")
    public String editjuntuanPwdPage(String id,Map<String,Object> model) {
        model.put("model", userService.get(id));
        return "/user/juntuanPwd_edit";
    }
    @ResponseBody
    @RequestMapping(value = "/updatejuntuanPwd", method = RequestMethod.POST)
    @RequiresPermissions("user:editjuntuanPwd")
    public ExecuteResult updatejuntuanPwd(@Valid UserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        userService.update(entity);
        return new ExecuteResult(StatusCode.OK);
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
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public Map<String,Object> listData(UserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserBean> entityList = userService.queryList(entity);
        GridPager g = new GridPager();
        g.setRows(entityList);
        g.setTotal(MybatisPagerInterceptor.getTotal());

        Map<String,Object> map = new HashedMap();
        map.put("g",g);
        if(entity.getPage() == 1){
            UserBean totalList = new UserBean();
            totalList = userService.queryUserTotal(entity);
            BigDecimal tradeMoney = userService.queryUserTotalTradeMoney(entity);
            if(null != tradeMoney && tradeMoney.compareTo(new BigDecimal(0)) != 0){
                totalList.setTotalTradeCount(new BigDecimal(tradeMoney.doubleValue()));
            }
            map.put("userTotal",totalList);
        }
        return map;
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



    //导出到Excel
    @RequestMapping("exportExcel")
    public void exportExcel(UserBean userBean, HttpServletResponse response){
        HSSFWorkbook wb = userService.exportExcel(userBean);
        ExcelUtils.exportExcel("客户资料", response, wb);
    }
    //提现页面
    @RequestMapping(value = "/getCash", method = RequestMethod.GET)
    @RequiresPermissions("user:getCash")
    public String getCashPage(String id, Map<String,Object> model) {
        model.put("model", userService.get(id));
        return "/user/user_getCash";
    }
    //提现操作
    @RequestMapping(value = "/getCash", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("user:getCash")
    public ExecuteResult getCash(UserBean userBean){
        return userService.getCash(userBean);
    }

    //充值页面
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    @RequiresPermissions("user:recharge")
    public String rechargePage(String id, Map<String,Object> model) {
        model.put("model", userService.get(id));
        return "/user/user_recharge";
    }
    //充值操作
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("user:recharge")
    public ExecuteResult recharge(UserBean userBean){
        return userService.recharge(userBean);
    }







    //赠送代金券页面
    @RequestMapping(value = "/sendCoupon", method = RequestMethod.GET)
    @RequiresPermissions("user:sendCoupon")
    public String sendCoupon(String id, Map<String,Object> model) {
        model.put("model", userService.get(id));
        return "/user/user_sendCoupon";
    }
    //赠送代金券操作
    @RequestMapping(value = "/sendCoupon", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("user:sendCoupon")
    public ExecuteResult sendCoupon(UserBean us, UserMessageBean userMessageBean){
        return userService.sendCoupon(us,userMessageBean);
    }
    /**
     * 炮兵团列表
     * @return
     */
    @RequestMapping(value = "/paobinglist", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public String paobingtuanlistPage() {
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
    public String qibingtuanlistPage() {
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
    public String bubingtuanlistPage() {
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

    @ResponseBody
    @RequestMapping(value = "/controlIt", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public ExecuteResult controlIt(String userId){
        return userService.controlIt(userId);
    }

    @ResponseBody
    @RequestMapping(value = "/cancelControlIt", method = RequestMethod.POST)
    @RequiresPermissions("user")
    public ExecuteResult cancelControlIt(String userId){
        return userService.cancelControlIt(userId);
    }

    @RequestMapping(value = "/updPwd", method = RequestMethod.GET)
    public String updatePwd(String id,Map<String,Object> model){
        model.put("model", UserUtils.getUser().getId());
        return "/user/updPwd";
    }

    @ResponseBody
    @RequestMapping(value = "/updPwd", method = RequestMethod.POST)
    public ExecuteResult updatePwd1(@Valid SysUserBean entity, Errors errors){
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        entity.setPassword(SystemService.entryptPassword(entity.getPassword()));
        userService.updatePwd(entity);
        return new ExecuteResult(StatusCode.OK);
    }


    @RequestMapping(value = "/sub", method = RequestMethod.GET)
    public String sub(Model model) {
        List<Units> list = tradeStatisticsService.findUnits();
        List<SubGoods> goodsList = userService.getSubGoodsList();
        model.addAttribute("goodsList",goodsList);
        model.addAttribute("units", list);
        model.addAttribute("centerList", ml3OperateCenterService.queryList(null));
        return "/user/user_sub";
    }

    @RequestMapping(value = "/detailSub", method = RequestMethod.GET)
    public String detailSub(Model model) {
        List<Units> list = tradeStatisticsService.findUnits();
        List<SubGoods> goodsList = userService.getSubGoodsList();
        model.addAttribute("goodsList",goodsList);
        model.addAttribute("units", list);
        model.addAttribute("centerList", ml3OperateCenterService.queryList(null));
        return "/user/sub_detail";
    }


    @RequestMapping(value = "/takeGoodsDetail", method = RequestMethod.GET)
    public String takeGoodsDetail(Model model) {
        List<Units> list = tradeStatisticsService.findUnits();
        List<SubGoods> goodsList = userService.getGoodsList();
        model.addAttribute("goodsList",goodsList);
        model.addAttribute("units", list);
        model.addAttribute("centerList", ml3OperateCenterService.queryList(null));
        return "/user/take_goods_detail";
    }
}
