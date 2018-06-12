package com.rmkj.microcap.modules.moneyrecord.service;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.bean.WeiPengDaiPayResultBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.service.WeiPengDaiPayService;
import com.rmkj.microcap.common.modules.money.out.weipeng.utils.WeiPengUtils;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinService;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.moneychange.dao.IMoneyChangeDao;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyInRecordPageBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class MoneyRecordService implements IBaseService<MoneyRecordBean> {
    private final static Logger logger = Logger.getLogger(MoneyRecordService.class);

    @Autowired
    private IMoneyRecordDao moneyRecordDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IMoneyChangeDao moneyChangeDao;

    @Autowired
    private WeiPengDaiPayService weiPengDaiPayService;

    @Autowired
    private WeiXinService weiXinService;

    @Override
    public MoneyRecordBean get(String id) {
        return moneyRecordDao.get(id);
    }

    @Override
    public int update(MoneyRecordBean moneyRecordBean) {
        moneyRecordBean.preUpdate();
        return moneyRecordDao.update(moneyRecordBean);
    }

    @Override
    public int delete(String id) {
        return moneyRecordDao.delete(id);
    }

    @Override
    public int insert(MoneyRecordBean moneyRecordBean) {
        moneyRecordBean.preInsert();
        return moneyRecordDao.insert(moneyRecordBean);
    }

    @Override
    public List<MoneyRecordBean> queryList(MoneyRecordBean moneyRecordBean) {
        return moneyRecordDao.queryList(moneyRecordBean);
    }

    //入金列表数据
    public List<MoneyRecordBean> moneyInList(MoneyRecordBean bean) {
        return moneyRecordDao.moneyInList(bean);
    }
    //出金统计
    public BigDecimal moneyInTotal(MoneyRecordBean bean){
        return moneyRecordDao.moneyInTotal(bean);
    }

    public BigDecimal alreadyMoneyInTotal(MoneyRecordBean bean){
        return moneyRecordDao.alreadyMoneyInTotal(bean);
    }

    public BigDecimal noMoneyInTotal(MoneyRecordBean bean){
        return moneyRecordDao.noMoneyInTotal(bean);
    }

    //出金列表数据
    public List<MoneyRecordBean> moneyOutList(MoneyRecordBean bean) {
        return moneyRecordDao.moneyOutList(bean);
    }
    //出金统计
    public BigDecimal moneyOutTotal(MoneyRecordBean bean){
        return moneyRecordDao.moneyOutTotal(bean);
    }

    public BigDecimal alreadyMoneyOutTotal(MoneyRecordBean bean){
        return moneyRecordDao.alreadyMoneyOutTotal(bean);
    }

    public BigDecimal noMoneyOutTotal(MoneyRecordBean bean){
        return moneyRecordDao.noMoneyOutTotal(bean);
    }

    //入金数据导出
    public HSSFWorkbook exportExcel(MoneyRecordBean recordBean) {
        recordBean.setPage(1);
        recordBean.setRows(ExcelUtils.SHEET_MAX_SIZE);

        HSSFWorkbook wb = new HSSFWorkbook();
        String[] header = new String[]{"流水号", "会员id", "会员姓名", "会员手机号", "金额", "手续费", "类型", "第三方流水号", "状态", "创建时间", "完成时间", "姓名", "银行名称", "银行卡号", "失败原因", "备注"};
        String[] headerColumn = new String[]{"serialNo", "userId", "uname", "mobile", "money", "fee", "type", "thirdSerialNo", "status", "createTime", "completeTime", "chnName", "bankName", "bankAccount", "failureReason", "remark"};
        int[] columnWidth = new int[]{20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 240, 20 * 240, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("status", (Object obj) -> {
            MoneyRecordBean u = (MoneyRecordBean) obj;
            return u.getStatus() == 0 ? "处理中" : (u.getStatus() == 1 ? "成功" : "失败");
        }).add("type", (Object obj) -> {
            MoneyRecordBean u = (MoneyRecordBean) obj;
            return u.getType() == 0 ? "充值" : "提现";
        });

        ExcelUtils.work(new ExcelUtils.DataFromDB<MoneyRecordBean>() {

            private int page = 1;

            @Override
            public List<MoneyRecordBean> find() {
                List<MoneyRecordBean> list = moneyRecordDao.moneyInList(recordBean);
                recordBean.setPage(++page);
                return list;
            }

            @Override
            public long totalPage() {
                return (long) Math.ceil(MybatisPagerInterceptor.getTotal() / recordBean.getRows().doubleValue());
            }

        }, MoneyRecordBean.class, wb, header, headerColumn, columnWidth, renderBox);

        return wb;
    }


    //出金数据导出
    public HSSFWorkbook exportExcelout(MoneyRecordBean recordBean, Integer type) {
        recordBean.setPage(1);
        recordBean.setRows(ExcelUtils.SHEET_MAX_SIZE);

        HSSFWorkbook wb = new HSSFWorkbook();
        String[] header = new String[]{"流水号", "会员id", "会员姓名", "会员手机号", "金额", "手续费", "类型", "第三方流水号", "状态", "创建时间", "完成时间", "姓名", "银行名称", "支行名称", "银行卡号", "失败原因", "备注"};
        String[] headerColumn = new String[]{"serialNo", "userId", "uname", "mobile", "money", "fee", "type", "thirdSerialNo", "status", "createTime", "completeTime", "chnName", "bankName", "openBankName", "bankAccount", "failureReason", "remark"};
        int[] columnWidth = new int[]{20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 240, 20 * 240, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180, 20 * 180};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("status", (Object obj) -> {
            MoneyRecordBean u = (MoneyRecordBean) obj;
            return u.getStatus() == 0 ? "处理中" : (u.getStatus() == 1 ? "成功" : "失败");
        }).add("type", (Object obj) -> {
            MoneyRecordBean u = (MoneyRecordBean) obj;
            return u.getType() == 0 ? "充值" : "提现";
        });

        ExcelUtils.work(new ExcelUtils.DataFromDB<MoneyRecordBean>() {

            private int page = 1;

            @Override
            public List<MoneyRecordBean> find() {
                List<MoneyRecordBean> list = null;
                if (type == null) {
                    list = moneyRecordDao.moneyOutList(recordBean);
                } else {
                    recordBean.setStatus(1);
                    list = moneyRecordDao.moneyOutList(recordBean);
                }
                recordBean.setPage(++page);
                return list;
            }

            @Override
            public long totalPage() {
                return (long) Math.ceil(MybatisPagerInterceptor.getTotal() / recordBean.getRows().doubleValue());
            }

        }, MoneyRecordBean.class, wb, header, headerColumn, columnWidth, renderBox);

        return wb;
    }

    /**
     * 出金 审核 不通过
     * @param id
     * @return
     */
    public ExecuteResult outPast(String id, Integer s, String failureReason) {
        MoneyRecordBean bean = new MoneyRecordBean();
        bean.setId(id);
        bean.setStatus(s);
        bean.setFailureReason(failureReason);

        String uuid = Utils.uuid();
        MoneyRecordBean moneyRecordBean = moneyRecordDao.get(id);
        UserBean userBean = userDao.get(moneyRecordBean.getUserId());
        userBean.setMoney(moneyRecordBean.getMoney());
        //出金审核时，再修改用户表，新增资金变更表
        userDao.moneyBack(userBean);//修改用户表余额
        UserBean userBean1 = userDao.get(userBean);//修改用户表余额后的用户实体

        MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
        moneyChangeBean.setId(uuid);
        moneyChangeBean.setUserId(moneyRecordBean.getUserId());
        moneyChangeBean.setDifMoney(moneyRecordBean.getMoney());//变更金额
        moneyChangeBean.setType(1);
        moneyChangeBean.setAfterMoney(userBean1.getMoney());//变更后的金额
        BigDecimal moneybefore = userBean1.getMoney().subtract(moneyRecordBean.getMoney());//变更前金额
        moneyChangeBean.setBeforeMoney(moneybefore);
        moneyChangeBean.setCreateTime(new Date());
        moneyChangeBean.setRemark(failureReason);
        moneyChangeDao.insert(moneyChangeBean);//新增资金变更表
        return moneyRecordDao.outPast(bean) == 1 ? new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);
    }
    //出金审核通过时
    public ExecuteResult outPastIn(String id,Integer s){
        MoneyRecordBean bean = new MoneyRecordBean();
        bean.setId(id);
        bean.setStatus(s);
        return moneyRecordDao.outPastIn(bean) == 1?new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);
    }

    //出金审核通过时
    public String passIn(String ids,Integer s){
        if (ids.indexOf(",") == -1) {
            if(aLaPassIn(ids) == 1){
                return "审核成功";
            }
            return "审核失败";
        } else {
            /*储存成功失败笔数*/
            Integer success = 0, fail = 0;
            String[] id = ids.split(",");
            for (int i = 0 ; i < id.length; i++){
                int record = aLaPassIn(id[i]);
                if(record != 1){
                    fail++;
                }else{
                    success++;
                }
            }
            return "成功出金：".concat(success.toString()).concat("笔,失败：").concat(fail.toString()).concat("笔");
        }

    }

    public int aLaPassIn(String id){
        MoneyRecordBean recordBeanSuccess = new MoneyRecordBean();
        recordBeanSuccess.setId(id);
        recordBeanSuccess.setReviewStatus(1);
        recordBeanSuccess.setStatus(1);
        return moneyRecordDao.outPastInStatusAndReviewStatus(recordBeanSuccess);
    }


    /**
     * 导出啊啦代付文件
     * @param ids
     * @return
     */
    public HSSFWorkbook alaPayExcel(String ids){
        String[] idsArr = ids.split(",");
        List<String> stringList = Arrays.asList(idsArr);
        List<MoneyRecordBean> list = moneyRecordDao.findByIds(stringList);
        HSSFWorkbook wb = new HSSFWorkbook();

        String[] headerColumn = new String[]{"id", "chnName", "bankName", "bankAccount", "money", "serial_no"};
        int[] columnWidth = new int[]{20*180, 20*180, 20*340, 20*480, 20*180, 20*180};
        final int[] count = {0};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("id", (Object obj) -> (++count[0]) + "");
        ExcelUtils.mapper(list, WithdrawalsBean.class, wb, "确认提现报表", null, headerColumn, columnWidth, renderBox, 2);

        HSSFSheet sheet = wb.getSheetAt(0);

        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, headerColumn.length - 1);
        //在sheet里增加合并单元格
        sheet.addMergedRegion(cra);

        // 开始画第一行表头
        Font font = wb.createFont();
        //设置字体样式
        font.setFontHeightInPoints((short)23);

        HSSFCellStyle cellHeaderStyle = wb.createCellStyle();
        cellHeaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellHeaderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellHeaderStyle.setFont(font);

        HSSFRow headerRow = sheet.createRow(0);

        headerRow.setHeight((short)(20*28));
        /**
         * 隐藏头部
         */
//        HSSFCell headerCell = headerRow.createCell(0);
//        headerCell.setCellStyle(cellHeaderStyle);
//        headerCell.setCellValue("商户提现表");

        // 开始画第二行表头
        Font font2 = wb.createFont();
        //设置字体样式
        font2.setFontHeightInPoints((short)16);

        HSSFCellStyle cellHeaderStyle2 = wb.createCellStyle();
        cellHeaderStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellHeaderStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellHeaderStyle2.setFont(font2);

        HSSFCell headerCell2 = null;
        HSSFRow headerRow2 = sheet.createRow(1);
        // 表头高度
        headerRow2.setHeight((short)(20*20));
        String[] columnHeader = new String[]{"顺序号", "收款户名", "收款银行", "收款账号", "收款金额", "备注"};
        for (int i = 0; i < columnHeader.length; i++) {
            headerCell2 = headerRow2.createCell(i);
            headerCell2.setCellStyle(cellHeaderStyle2);
            headerCell2.setCellValue(columnHeader[i]);
        }

        return wb;
    }

    public Map<String, Object> queryMoneyInRecordPage(MoneyInRecordPageBean entity){
        Map<String, Object> result = new HashedMap();
        GridPager page = new GridPager();
        List<MoneyInRecordPageBean> list = moneyRecordDao.queryMoneyRecordPage(entity);
        page.setRows(list);
        page.setTotal((int) moneyRecordDao.queryMoneyRecordTotal(entity));
        result.put("g", page);
        BigDecimal moneyInTotal = moneyRecordDao.queryMoneyRecordByStatus(entity);
        entity.setStatus(0);
        BigDecimal noMoneyInTotal = moneyRecordDao.queryMoneyRecordByStatus(entity);
        entity.setStatus(1);
        BigDecimal alreadyInTotal = moneyRecordDao.queryMoneyRecordByStatus(entity);

        /*BigDecimal moneyInTotal = moneyRecordService.moneyInTotal(entity);
        BigDecimal noMoneyInTotal = moneyRecordService.noMoneyInTotal(entity);
        BigDecimal alreadyInTotal = moneyRecordService.alreadyMoneyInTotal(entity);*/
        result.put("moneyInTotal", moneyInTotal == null ? 0 : moneyInTotal);
        result.put("noMoneyInTotal",noMoneyInTotal == null ? 0 : noMoneyInTotal);
        result.put("alreadyMoneyInTotal",alreadyInTotal == null ? 0 : alreadyInTotal);
        return result;
    }

    public List<String> queryRechargeChannel(){
        return moneyRecordDao.queryRechargeChannel();
    }

}
