package com.rmkj.microcap.modules.ReturnMoneyOut.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.ReturnMoneyOut.dao.IReturnMoneyOutDao;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.DocFlavor;
import java.lang.Object;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;


/**
* Created by Administrator on 2016-12-7.
*/
@Service
@Transactional
public class ReturnMoneyOutService implements IBaseService<ReturnMoneyOutBean> {
    @Autowired
    private IReturnMoneyOutDao returnMoneyOutDao;

    @Override
    public ReturnMoneyOutBean get(String id){
        return returnMoneyOutDao.get(id);
    }
    @Override
    public int update(ReturnMoneyOutBean returnMoneyOutBean){
        returnMoneyOutBean.preUpdate();
        return returnMoneyOutDao.update(returnMoneyOutBean);
    }
    @Override
    public int delete(String id){
        return returnMoneyOutDao.delete(id);
    }
    @Override
    public int insert(ReturnMoneyOutBean returnMoneyOutBean){
        returnMoneyOutBean.preInsert();
        return returnMoneyOutDao.insert(returnMoneyOutBean);
    }
    @Override
    public List<ReturnMoneyOutBean> queryList(ReturnMoneyOutBean returnMoneyOutBean){
        return returnMoneyOutDao.queryList(returnMoneyOutBean);
    }
    //提现申请审核通过时
    public ExecuteResult outPastIn(String id, Integer s){
        ReturnMoneyOutBean bean = new ReturnMoneyOutBean();
        bean.setId(id);
        bean.setStatus(s);
        return returnMoneyOutDao.outPastIn(bean) == 1?new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);
    }
    //提现申请审核没通过时
    public ExecuteResult outPast(String id,Integer s,String failureReason) {
        ReturnMoneyOutBean bean = new ReturnMoneyOutBean();
        bean.setId(id);
        bean.setStatus(s);
        bean.setFailureReason(failureReason);
        System.out.println("失败原因-----"+failureReason);
        return returnMoneyOutDao.outPast(bean) == 1 ? new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);
    }

    public HSSFWorkbook alaPayExcel(String ids) {
        String[] idsArr = ids.split(",");
        List<String> stringList = Arrays.asList(idsArr);
        List<ReturnMoneyOutBean> list = returnMoneyOutDao.findByIds(stringList);
        HSSFWorkbook wb = new HSSFWorkbook();

        String[] headerColumn = new String[]{"id", "chnName", "bankAccount", "openBankName", "money"};
        int[] columnWidth = new int[]{20*180, 20*180, 20*340, 20*480, 20*180};
        final int[] count = {0};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("id", (Object obj) -> (++count[0]) + "");
        ExcelUtils.mapper(list, ReturnMoneyOutBean.class, wb, "确认提现报表", null, headerColumn, columnWidth, renderBox, 2);

        HSSFSheet sheet = wb.getSheetAt(0);

        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 4);
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
        HSSFCell headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(cellHeaderStyle);
        headerCell.setCellValue("商户提现表");

        // 开始画第二行表头
        Font font2 = wb.createFont();
        //设置字体样式
        font2.setFontHeightInPoints((short)16);

        HSSFCellStyle cellHeaderStyle2 = wb.createCellStyle();
        cellHeaderStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellHeaderStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellHeaderStyle2.setFont(font2);

        HSSFRow headerRow2 = sheet.createRow(1);
        // 表头高度
        headerRow2.setHeight((short)(20*20));
        String[] columnHeader = new String[]{"序号", "结算户名", "结算账号", "开户行名", "金额"};
        for (int i = 0; i < columnHeader.length; i++) {
            HSSFCell headerCell2 = headerRow2.createCell(i);
            headerCell2.setCellStyle(cellHeaderStyle2);
            headerCell2.setCellValue(columnHeader[i]);
        }
        return wb;
    }

    /**
     * 威鹏先下打款-审核
     * @param ids
     * @param s
     * @return
     */
    public String returnMoneyOutPassIn(String ids,Integer s){
        if (ids.indexOf(",") == -1) {
            if(returnMoneyOutDao.updateReturnMoneyOutSuccess(ids) == 1){
                return "审核成功";
            }
            return "审核失败";
        } else {
            /*储存成功失败笔数*/
            Integer success = 0, fail = 0;
            String[] id = ids.split(",");
            for (int i = 0 ; i < id.length; i++){
                int record = returnMoneyOutDao.updateReturnMoneyOutSuccess(id[i]);
                if(record != 1){
                    fail++;
                }else{
                    success++;
                }
            }
            return "成功出金：".concat(success.toString()).concat("笔,失败：").concat(fail.toString()).concat("笔");
        }

    }

}
