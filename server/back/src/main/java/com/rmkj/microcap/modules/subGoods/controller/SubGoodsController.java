package com.rmkj.microcap.modules.subGoods.controller;/**
 * Created by Administrator on 2018/4/26.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.subGoods.entity.IntegralBean;
import com.rmkj.microcap.modules.subGoods.entity.SubGoods;
import com.rmkj.microcap.modules.subGoods.entity.TakeGoodsParam;
import com.rmkj.microcap.modules.subGoods.service.SubGoodsService;
import com.rmkj.microcap.modules.user.entity.UserBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author k
 * @create -04-26-13:48
 **/
@Controller
@RequestMapping(value = "/subgoods")
public class SubGoodsController extends BaseController {

    @Autowired
    private SubGoodsService subGoodsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("subgoods")
    public String listQuery(){
        return "/subGoods/subGoods_list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("subgoods")
    public GridPager queryList(SubGoods subGoods){
        subGoods.setStart(evalStart(subGoods.getPage(), subGoods.getRows()));
        return subGoodsService.queryList(subGoods);
    }

    /**
     * 添加认购商品
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("subgoods:add")
    public String saveSubGoods(Model model){
        model.addAttribute("goodsTypeList", subGoodsService.getGoodsType());
        return "/subGoods/subGoods_add";
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("subgoods:add")
    public ExecuteResult addSubGoods(@Valid SubGoods entity, Errors errors, MultipartHttpServletRequest request){
//        if(errors.hasErrors()){
//            return parseErrors(errors);
//        }
        return subGoodsService.saveSubGoods(entity, request);
    }

    /**
     * 读取认购商品图片
     * @param fileName
     * @param imgName
     * @param request
     * @param response
     */
    @RequestMapping(value = "/show/{fileName}/{imgName}",method = RequestMethod.GET)
    public void fileReLoad(@PathVariable String fileName, @PathVariable String imgName, HttpServletRequest request, HttpServletResponse response){
        subGoodsService.fileReLoad(fileName, imgName, request, response);
    }

    /**
     * 修改商品信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @RequiresPermissions("subgoods:edit")
    public String update(String id, Model model){
        model.addAttribute("subGoods", subGoodsService.getById(id));
        return "/subGoods/subGoods_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("subgoods:edit")
    public ExecuteResult updateSubGoods(@Valid SubGoods subGoods){
        return subGoodsService.updateSubGoods(subGoods);
    }

    /**
     * 商品下架
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public ExecuteResult updateStatusByIdClose(String id){
        return subGoodsService.updateStatusByIdClose(id);
    }

    /**
     * 商品上架
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    public ExecuteResult updateStatusByIdOpen(String id){
        return subGoodsService.updateStatusByIdOpen(id);
    }

    /**
     * 提货明细
     * @param takeGoodsParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/takeGoodsList", method = RequestMethod.POST)
    public GridPager takeGoodsList(TakeGoodsParam takeGoodsParam){
        return subGoodsService.takeGoodsList(takeGoodsParam);
    }

    /**
     * 提货申请操作
     * @param id
     * @param operate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/goodsOperate", method = RequestMethod.POST)
    public ExecuteResult goodsOperate(String id, String operate){
        return subGoodsService.goodsOperate(id,operate);
    }

    /**
     * 跳转积分明细页面
     * @return
     */
    @RequestMapping(value = "/integralList", method = RequestMethod.GET)
    public String integralList(){
        return "/subGoods/integral_list";
    }

    /**
     * 积分明细
     * @param mobile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIntegralList", method = RequestMethod.POST)
    public GridPager takeGoodsList(String mobile){
        return subGoodsService.getIntegralList(mobile);
    }

    /**
     * 积分明细导出
     * @param integralBean
     * @param response
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(IntegralBean integralBean, HttpServletResponse response){
        HSSFWorkbook wb = subGoodsService.exportExcel(integralBean);
        ExcelUtils.exportExcel("积分明细", response, wb);
    }
}
