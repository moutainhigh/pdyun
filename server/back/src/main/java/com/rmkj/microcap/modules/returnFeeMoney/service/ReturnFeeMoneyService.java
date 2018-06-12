package com.rmkj.microcap.modules.returnFeeMoney.service;/**
 * Created by Administrator on 2017/10/9.
 */

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.returnFeeMoney.dao.ReturnFeeMoneyDao;
import com.rmkj.microcap.modules.returnFeeMoney.entity.*;
import com.rmkj.microcap.modules.trade.entity.TradeBean;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author k
 * @create -10-09-8:23
 **/
@Service
@Transactional
public class ReturnFeeMoneyService {

    @Autowired
    private ReturnFeeMoneyDao returnFeeMoneyDao;

    /**
     * 查询代理商手续费提现列表
     * @param returnFeeAgent
     * @return
     */
    public GridPager queryReturnFeeMoneyByAgent(ReturnFeeAgent returnFeeAgent){
        List<ReturnFeeAgent> list = returnFeeMoneyDao.queryReturnFeeMoneyByAgent(returnFeeAgent);
        GridPager page = new GridPager();
        page.setRows(list);
        page.setTotal((int) returnFeeMoneyDao.queryReturnFeeMoneyByAgentTotal(returnFeeAgent));
        return page;
    }

    /**
     * 查询会员单位手续费提现列表
     * @param entity
     * @return
     */
    public GridPager queryReturnFeeMoneyByUnits(ReturnFeeMemberUnits entity){
        List<ReturnFeeMemberUnits> list = returnFeeMoneyDao.queryReturnFeeMoneyByUnits(entity);
        GridPager page = new GridPager();
        page.setRows(list);
        page.setTotal((int) returnFeeMoneyDao.queryReturnFeeMoneyByUnitsTotal(entity));
        return page;
    }

    /**
     * 查询市场管理部手续费提现列表
     * @param entity
     * @return
     */
    public GridPager queryReturnFeeMoneyByCenter(ReturnFeeOperateCenter entity){
        List<ReturnFeeOperateCenter> list = returnFeeMoneyDao.queryReturnFeeMoneyByCenter(entity);
        GridPager page = new GridPager();
        page.setRows(list);
        page.setTotal((int) returnFeeMoneyDao.queryReturnFeeMoneyByCenterTotal(entity));
        return page;
    }

    public ExecuteResult saveMoneyPlatRecord(TradeReturnFeeRecord entity){
        //验证提现时间
//        if(!validateWithdrawTime()){
//            return new ExecuteResult(StatusCode.FAILED, "请在工作日下午六点之前提现");
//        }

        FeesBean feesBean = returnFeeMoneyDao.getFeesBean();
        if(entity.getFeeType().equals("1")){
            if(feesBean.getFeeMoney().compareTo(entity.getMoney())==-1){
                return new ExecuteResult(StatusCode.FAILED, "手续费不足");
            }
        }else if(entity.getFeeType().equals("2")){
            if(feesBean.getServiceFeeMoney().compareTo(entity.getMoney())==-1){
                return new ExecuteResult(StatusCode.FAILED, "服务费不足");
            }
        }else{
            return new ExecuteResult(StatusCode.FAILED, "请选择种类(服务费、手续费)");
        }

        entity.setId(Utils.uuid());
        entity.setSerialNo(serialNo());
        entity.setType("4");
        entity.setStatus("1");
        entity.setReviewStatus("1");
        entity.setCreateTime(new Date());
        entity.setReviewTime(new Date());
        entity.setCompleteTime(new Date());

        //减去手续费或者服务费
        if(returnFeeMoneyDao.updatePlatMoney(entity)!=1){
            return new ExecuteResult(StatusCode.FAILED, "平台计算出金错误！");
        }

        if(returnFeeMoneyDao.saveMoneyPlatRecord(entity) != 1){
            return new ExecuteResult(StatusCode.FAILED, "申请出金失败！");
        }

        return new ExecuteResult(StatusCode.OK, "申请出金成功！");
    }


    public GridPager moneyPlatRecord(TradeReturnFeeRecord entity){
        List<TradeReturnFeeRecord> list = returnFeeMoneyDao.moneyPlatRecord();
        GridPager page = new GridPager();
        page.setRows(list);
        page.setTotal((int) returnFeeMoneyDao.countMoneyPlatRecord());
        return page;
    }



    public HSSFWorkbook exportExcel(TradeReturnFeeRecord bean) {
        bean.setPage(1);
        bean.setRows(ExcelUtils.SHEET_MAX_SIZE);
        HSSFWorkbook wb = new HSSFWorkbook();

        String[] header = new String[]{"类型","申请金额","审核状态","银行名称","银行开户行名称","银行账户","开户人姓名","申请时间","完成时间"};
        String[] headerColumn = new String[]{"feeType","money","reviewStatus","bankName","openBankName","bankAccount","chnName","createTime","completeTime"};
        int[] columnWidth = new int[]{20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180};

        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("feeType", (Object obj) -> {
            TradeReturnFeeRecord u = (TradeReturnFeeRecord)obj;
            return u.getFeeType().equals("1") ? "手续费" : "服务费";
        });

        ExcelUtils.work(new ExcelUtils.DataFromDB<TradeReturnFeeRecord>(){
            private int page = 1;
            @Override
            public List<TradeReturnFeeRecord> find() {
                List<TradeReturnFeeRecord> list = returnFeeMoneyDao.moneyPlatRecord();
                bean.setPage(++page);
                return list;
            }
            @Override
            public long totalPage() {
                return (long)Math.ceil((int)returnFeeMoneyDao.countMoneyPlatRecord()/bean.getRows().doubleValue());
            }
        }, TradeReturnFeeRecord.class, wb, header, headerColumn, columnWidth, renderBox);

        return wb;
    }



    private final Random random = new Random();
    private String serialNo(){
        return "MO" + new Date().getTime()+""+String.format("%1$03d",random.nextInt(1000));
    }

    public boolean validateWithdrawTime(){
        Date date = new Date();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(date);
        if(nowCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || nowCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return false;
        }
        if(nowCalendar.compareTo(getStartWithdrawTime(date)) == -1 || nowCalendar.compareTo(getEndWithdrawTime(date)) == 1){
            return false;
        }
        return true;
    }
    /**
     * TODO 获取提现开始时间
     * @param date
     * @return
     */
    public Calendar getStartWithdrawTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,30);
        return calendar;
    }

    /**
     * TODO 提现结束时间
     * @param date
     * @return
     */
    public Calendar getEndWithdrawTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,18);
        calendar.set(Calendar.MINUTE,00);
        return calendar;
    }

    /**
     * 返手续费提现线下审核通过，不走代付
     * @param ids
     * @return
     */
    public ExecuteResult passReturnFeeMoney(String ids){
        String[] array = null;
        if(StringUtils.isBlank(ids)){
            array = new String[0];
        }else{
            array = ids.split(",");
        }
        List<String> idList = Arrays.asList(array);
        List<TradeReturnFeeRecord> returnFeeAgents = returnFeeMoneyDao.filterNoPassRecord(idList);
        return updateReturnFeeRecordStatus(returnFeeAgents, Constants.WITHDRAW_STATUS.SUCCESS, null);
    }

    /**
     * 返手续费提现线下审核通过，不走代付
     * @param ids
     * @return
     */
    public ExecuteResult noPassReturnFeeMoney(String ids, String failureReason){
        String[] array = null;
        if(StringUtils.isBlank(ids)){
            array = new String[0];
        }else{
            array = ids.split(",");
        }
        List<String> idList = Arrays.asList(array);
        List<TradeReturnFeeRecord> returnFeeAgents = returnFeeMoneyDao.filterNoPassRecord(idList);
        return updateReturnFeeRecordStatus(returnFeeAgents, Constants.WITHDRAW_STATUS.FAILD, failureReason);
    }

    /**
     * 修改手续费提现订单表，审核状态和状态
     * @param list
     * @param status 1通过 2不通过
     * @return
     */
    public ExecuteResult updateReturnFeeRecordStatus(List<TradeReturnFeeRecord> list, String status, String failureReason){
        if(null == list && 0 >= list.size()){
            return new ExecuteResult(StatusCode.WEIPENG_DAI_PAY_FAIL);
        }
        final int[] result = {0,0};
        list.forEach(returnFeeMoney ->{
            TradeReturnFeeRecord record = new TradeReturnFeeRecord();
            record.setId(returnFeeMoney.getId());
            record.setStatus(status);
            record.setReviewStatus(status);
            record.setCompleteTime(new Date());
            record.setRemark("手动审核");
            if(null != failureReason){
                record.setFailureReason(failureReason);
            }
                if(!Constants.WITHDRAW_STATUS.SUCCESS.equals(status)){
                    if("2".equals(returnFeeMoney.getType())){ //返会员单位
                        if(1 != returnFeeMoneyDao.updateReviewStatusAndStatus(record) || 1 != returnFeeMoneyDao.returnWithDrawfeeMoneyByUnits(returnFeeMoney)){
                            result[1] += 1;
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }else{
                            result[0] += 1;
                        }
                    }else if("3".equals(returnFeeMoney.getType())){ //返代理
                        if(1 != returnFeeMoneyDao.updateReviewStatusAndStatus(record) || 1 != returnFeeMoneyDao.returnWithDrawfeeMoneyByAgent(returnFeeMoney)){
                            result[1] += 1;
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }else{
                            result[0] += 1;
                        }
                    }else{
                        if(1 != returnFeeMoneyDao.updateReviewStatusAndStatus(record) || 1 != returnFeeMoneyDao.returnWithDrawfeeMoneyByCenter(returnFeeMoney)){
                            result[1] += 1;
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }else{
                            result[0] += 1;
                        }
                    }
                }else{
                    if(1 != returnFeeMoneyDao.updateReviewStatusAndStatus(record)){
                        result[1] += 1;
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    }else{
                        result[0] += 1;
                    }
                }

        });
        return new ExecuteResult(StatusCode.OK,"审核成功：".concat(String.valueOf(result[0])).concat("笔,失败：").concat(String.valueOf(result[1])).concat("笔"));
    }


    /**
     * 导出返手续费提现线下出金表格
     * @param ids
     * @return
     */
    public HSSFWorkbook exportAlaExcel(String ids){
        String[] idsArr = ids.split(",");
        List<String> stringList = Arrays.asList(idsArr);
        List<TradeReturnFeeRecord> list = returnFeeMoneyDao.filterNoPassRecord(stringList);
        HSSFWorkbook wb = new HSSFWorkbook();

        String[] headerColumn = new String[]{"id", "chnName", "openBankName", "bankAccount", "money", "serial_no"};
        int[] columnWidth = new int[]{20*180, 20*180, 20*340, 20*480, 20*180};
        final int[] count = {0};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("id", (Object obj) -> (++count[0]) + "");
        ExcelUtils.mapper(list, TradeReturnFeeRecord.class, wb, "确认提现报表", null, headerColumn, columnWidth, renderBox, 2);
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
        /*HSSFCell headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(cellHeaderStyle);
        headerCell.setCellValue("商户返手续费提现表");*/

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

}
