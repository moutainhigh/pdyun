package com.rmkj.microcap.modules.tradeReturnFeeWithdraw.service;/**
 * Created by Administrator on 2017/9/22.
 */

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.common.modules.sys.bean.Ml3RoleBean;
import com.rmkj.microcap.common.modules.sys.dao.IMl3AgentRoleDao;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.Ml3Agent.dao.IMl3AgentDao;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.dao.IMl3MemberUnitsDao;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.dao.IMl3OperateCenterDao;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;
import com.rmkj.microcap.modules.trade.entity.TradeStatisticsParams;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.dao.TradeReturnFeeChangeDao;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.dao.TradeReturnFeeDao;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.*;
import com.sun.corba.se.impl.oa.toa.TOA;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author k
 * @create -09-22-8:36
 **/
@Service
public class TradeReturnFeeService implements IBaseService{

    @Autowired
    private TradeReturnFeeDao tradeReturnFeeDao;

    @Autowired
    private IMl3AgentDao ml3AgentDao;

    @Autowired
    private IMl3AgentRoleDao ml3AgentRoleDao;

    @Autowired
    private IMl3MemberUnitsDao ml3MemberUnitsDao;

    @Autowired
    private IMl3OperateCenterDao ml3OperateCenterDao;

    @Autowired
    private TradeReturnFeeChangeDao tradeReturnFeeChangeDao;

    /**
     * 代理商返手续费提现
     * @param entity
     * @return
     */
    public synchronized ExecuteResult returnFeeTotalWithDraw(TradeReturnFeeRecord entity){
        int updateResult = 0, insertResult = 0;

        String userId = UserUtils.getUser().getId();

        //查询当前用户角色，判断是市场管理部或会员单位或代理商
        Ml3AgentRoleBean ml3AgentRoleBean = ml3AgentRoleDao.get(userId);
        if(Constants.ML3_AGENT_ROLE.ROLE_AGENT.equals(ml3AgentRoleBean.getRoleId())){//代理商返手续费余额提现
            //查询当前代理用户信息
            Ml3AgentBean agentBean = ml3AgentDao.get(userId);
            if(null == agentBean){
                return new ExecuteResult(StatusCode.FAILED, "该用户不存在");
            }
            if(entity.getFeeType().equals("1")){//手续费
                //验证手续费余额
                if(false == checkReturnFeeMoney(entity.getMoney(), agentBean.getAgentReturnFeeMoney())){
                    return new ExecuteResult(StatusCode.FAILED, "可提现返手续费余额不足");
                }
            }else if(entity.getFeeType().equals("2")){//服务费
                //验证服务费余额
                if(false == checkReturnFeeMoney(entity.getMoney(), agentBean.getAgentReturnServiceMoney())){
                    return new ExecuteResult(StatusCode.FAILED, "可提现返服务费费余额不足");
                }
            }else{
                return new ExecuteResult(StatusCode.FAILED, "请选择类型(手续费、服务费)");
            }

            entity.setType("3");
            entity.setSerialNo(serialNo());
            entity.setFee(new BigDecimal(0));
            entity.setStatus(Constants.WITHDRAW_STATUS.WAIT);
            entity.setReviewStatus(Constants.WITHDRAW_STATUS.WAIT);
            entity.setCreateTime(new Date());
            entity.setCenterId(agentBean.getCenterId());
            entity.setUnitsId(agentBean.getUnitsId());
            entity.setAgentId(userId);
            if(entity.getFeeType().equals("1")){
                updateResult = ml3AgentDao.updateReturnFeeMoneyAgent(entity);
            }else{
                updateResult = ml3AgentDao.updateReturnServiceFeeMoneyAgent(entity);
            }

            insertResult = addTradeReturnFeeWithDrawChange(entity, ml3AgentRoleBean.getRoleId(), agentBean, null, null);
        }

        if(Constants.ML3_AGENT_ROLE.ROLE_UNITS.equals(ml3AgentRoleBean.getRoleId())){//会员单位返手续费余额提现
            String unitsId = UserUtils.getUser().getUnitsId();
            //查询当前会员单位用户信息
            Ml3MemberUnitsBean ml3MemberUnitsBean = ml3MemberUnitsDao.get(unitsId);
            if(null == ml3MemberUnitsBean){
                return new ExecuteResult(StatusCode.FAILED, "该用户不存在");
            }
            if(entity.getFeeType().equals("1")){//手续费
                //验证手续费余额
                if(false == checkReturnFeeMoney(entity.getMoney(), ml3MemberUnitsBean.getUnitsReturnFeeMoney())){
                    return new ExecuteResult(StatusCode.FAILED, "可提现返手续费余额不足");
                }
            }else if(entity.getFeeType().equals("2")){//服务费
                //验证服务费余额
                if(false == checkReturnFeeMoney(entity.getMoney(), ml3MemberUnitsBean.getUnitsReturnServiceMoney())){
                    return new ExecuteResult(StatusCode.FAILED, "可提现返服务费余额不足");
                }
            }else{
                return new ExecuteResult(StatusCode.FAILED, "请选择类型(手续费、服务费)");
            }

            entity.setType("2");
            entity.setSerialNo(serialNo());
            entity.setFee(new BigDecimal(0));
            entity.setStatus(Constants.WITHDRAW_STATUS.WAIT);
            entity.setReviewStatus(Constants.WITHDRAW_STATUS.WAIT);
            entity.setCreateTime(new Date());
            entity.setCenterId(ml3MemberUnitsBean.getCenterId());
            entity.setUnitsId(ml3MemberUnitsBean.getId());
            if(entity.getFeeType().equals("1")){
                updateResult = ml3MemberUnitsDao.updateReturnFeeMoneyUnits(entity);
            }else{
                updateResult = ml3MemberUnitsDao.updateReturnServiceFeeMoneyUnits(entity);
            }
            insertResult = addTradeReturnFeeWithDrawChange(entity, ml3AgentRoleBean.getRoleId(), null, ml3MemberUnitsBean, null);
        }

        if(Constants.ML3_AGENT_ROLE.ROLE_CENTER.equals(ml3AgentRoleBean.getRoleId())){//市场管理部返手续费余额提现
            String centerId = UserUtils.getUser().getCenterId();
            //查询当前市场管理部用户信息
            Ml3OperateCenterBean operateCenterBean = ml3OperateCenterDao.get(centerId);
            if(null == operateCenterBean){
                return new ExecuteResult(StatusCode.FAILED, "该用户不存在");
            }
            if(entity.getFeeType().equals("1")){//手续费
                //验证手续费余额
                if(false == checkReturnFeeMoney(entity.getMoney(), operateCenterBean.getReturnFeeMoney())){
                    return new ExecuteResult(StatusCode.FAILED, "可提现返手续费余额不足");
                }
            }else if(entity.getFeeType().equals("2")){//服务费
                //验证服务费余额
                if(false == checkReturnFeeMoney(entity.getMoney(), operateCenterBean.getReturnServiceMoney())){
                    return new ExecuteResult(StatusCode.FAILED, "可提现返服务费余额不足");
                }
            }else{
                return new ExecuteResult(StatusCode.FAILED, "请选择类型(手续费、服务费)");
            }

            entity.setType(ml3AgentRoleBean.getRoleId());
            entity.setType("1");
            entity.setSerialNo(serialNo());
            entity.setFee(new BigDecimal(0));
            entity.setStatus(Constants.WITHDRAW_STATUS.WAIT);
            entity.setReviewStatus(Constants.WITHDRAW_STATUS.WAIT);
            entity.setCreateTime(new Date());
            entity.setCenterId(operateCenterBean.getId());
            if(entity.getFeeType().equals("1")){
                updateResult = ml3OperateCenterDao.updateReturnFeeMoneyCenter(entity);
            }else{
                updateResult = ml3OperateCenterDao.updateReturnServiceFeeMoneyCenter(entity);
            }
            insertResult = addTradeReturnFeeWithDrawChange(entity, ml3AgentRoleBean.getRoleId(), null, null, operateCenterBean);
        }

        entity.preInsert();

        int result = tradeReturnFeeDao.insertTradeFeeWithDraw(entity);
        if(1 == result && 1 == updateResult && 1 == insertResult){
            return new ExecuteResult(StatusCode.OK, "提现成功！");
        }else{
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ExecuteResult(StatusCode.FAILED, "请求超时！");
        }
    }



    private final Random random = new Random();
    /**
     * 获取流水号
     * @return
     */
    private String serialNo(){
        return "MO" + new Date().getTime()+""+String.format("%1$03d", random.nextInt(1000));
    }

    /**
     * 添加返手续费提现记录
     * @param returnFeeRecord 手续费提现
     * @param Role  角色: 1:市场管理部 5:会员单位 4:代理商
     * @param ml3AgentBean 代理商信息
     * @param ml3MemberUnitsBean 会员单位信息
     * @param ml3OperateCenterBean 市场管理部信息
     * @return
     */
    public int addTradeReturnFeeWithDrawChange(TradeReturnFeeRecord returnFeeRecord, String Role, Ml3AgentBean ml3AgentBean, Ml3MemberUnitsBean ml3MemberUnitsBean, Ml3OperateCenterBean ml3OperateCenterBean){
        TradeReturnFeeChange tradeReturnFeeChange = new TradeReturnFeeChange();
        if(Constants.ML3_AGENT_ROLE.ROLE_AGENT.equals(Role)){
            tradeReturnFeeChange.setTotalFee(returnFeeRecord.getMoney());
            if(returnFeeRecord.getFeeType().equals("1")){
                tradeReturnFeeChange.setBeforMoney(ml3AgentBean.getAgentReturnFeeMoney());
                tradeReturnFeeChange.setAfterMoney(ml3AgentBean.getAgentReturnFeeMoney().subtract(returnFeeRecord.getMoney()));
            }else{
                tradeReturnFeeChange.setBeforMoney(ml3AgentBean.getAgentReturnServiceMoney());
                tradeReturnFeeChange.setAfterMoney(ml3AgentBean.getAgentReturnServiceMoney().subtract(returnFeeRecord.getMoney()));
            }
            tradeReturnFeeChange.setReturnFeePercent(ml3AgentBean.getAgentReturnFeePercent());
            tradeReturnFeeChange.setType(2);
            tradeReturnFeeChange.setCenterId(ml3AgentBean.getCenterId());
            tradeReturnFeeChange.setUnitsId(ml3AgentBean.getUnitsId());
            tradeReturnFeeChange.setAgentId(ml3AgentBean.getId());
            tradeReturnFeeChange.setCreateTime(new Date());

        }else if(Constants.ML3_AGENT_ROLE.ROLE_UNITS.equals(Role)){
            //TradeReturnFeeChange tradeReturnFeeChange = new TradeReturnFeeChange();
            tradeReturnFeeChange.setTotalFee(returnFeeRecord.getMoney());
            if(returnFeeRecord.getFeeType().equals("1")){
                tradeReturnFeeChange.setBeforMoney(ml3MemberUnitsBean.getUnitsReturnFeeMoney());
                tradeReturnFeeChange.setAfterMoney(ml3MemberUnitsBean.getUnitsReturnFeeMoney().subtract(returnFeeRecord.getMoney()));
            }else{
                tradeReturnFeeChange.setBeforMoney(ml3MemberUnitsBean.getUnitsReturnServiceMoney());
                tradeReturnFeeChange.setAfterMoney(ml3MemberUnitsBean.getUnitsReturnServiceMoney().subtract(returnFeeRecord.getMoney()));
            }
            tradeReturnFeeChange.setReturnFeePercent(ml3MemberUnitsBean.getUnitsReturnFeePercent());
            tradeReturnFeeChange.setType(2);
            tradeReturnFeeChange.setCenterId(ml3MemberUnitsBean.getCenterId());
            tradeReturnFeeChange.setUnitsId(ml3MemberUnitsBean.getId());
            tradeReturnFeeChange.setCreateTime(new Date());
        }else if(Constants.ML3_AGENT_ROLE.ROLE_CENTER.equals(Role)){
            //TradeReturnFeeChange tradeReturnFeeChange = new TradeReturnFeeChange();
            tradeReturnFeeChange.setTotalFee(returnFeeRecord.getMoney());
            if(returnFeeRecord.getFeeType().equals("1")){
                tradeReturnFeeChange.setBeforMoney(ml3OperateCenterBean.getReturnFeeMoney());
                tradeReturnFeeChange.setAfterMoney(ml3OperateCenterBean.getReturnFeeMoney().subtract(returnFeeRecord.getMoney()));
            }else{
                tradeReturnFeeChange.setBeforMoney(ml3OperateCenterBean.getReturnServiceMoney());
                tradeReturnFeeChange.setAfterMoney(ml3OperateCenterBean.getReturnServiceMoney().subtract(returnFeeRecord.getMoney()));
            }
            tradeReturnFeeChange.setReturnFeePercent(ml3OperateCenterBean.getReturnFeePercent());
            tradeReturnFeeChange.setType(2);
            tradeReturnFeeChange.setCenterId(ml3OperateCenterBean.getId());
            tradeReturnFeeChange.setCreateTime(new Date());
        }
        tradeReturnFeeChange.preInsert();
        int result = tradeReturnFeeChangeDao.insertWithDrawChange(tradeReturnFeeChange);
        return result;
    }

    /**
     * 检查提现金额是否大于返手续费余额
     * @param withDrawMoney
     * @param returnFeeMoney
     * @return
     */
    public boolean checkReturnFeeMoney(BigDecimal withDrawMoney, BigDecimal returnFeeMoney){
        if(null == withDrawMoney || null == returnFeeMoney){
            return false;
        }

        if(returnFeeMoney.compareTo(withDrawMoney) == -1){
            return false;
        }
        return true;
    }

    /**
     * 根据登录角色查询当前角色自己的，返手续提现记录
     * @param entity
     * @return
     */
    public GridPager findRetrunFeeMoneyRecordByRole(TradeReturnFeePage entity){
        String userId = UserUtils.getUser().getId();

        entity = getRoleId(entity);
        List<TradeReturnFeeRecord> feeMoneyRecordByRole = tradeReturnFeeDao.findRetrunFeeMoneyRecordByRole(entity);
        GridPager page = new GridPager();
        page.setTotal((int) tradeReturnFeeDao.findRetrunFeeMoneyRecordByRoleTotal(entity));
        page.setRows(feeMoneyRecordByRole);
        return page;
    }

    /**
     * 获取当前用户角色
     * @return
     */
    public TradeReturnFeePage getRoleId(TradeReturnFeePage entity){
        String userId = UserUtils.getUser().getId();
        //查询当前用户角色，判断是市场管理部或会员单位或代理商
        Ml3AgentRoleBean ml3AgentRoleBean = ml3AgentRoleDao.get(userId);

        //TradeReturnFeePage entity = new TradeReturnFeePage();
        if(Constants.ML3_AGENT_ROLE.ROLE_AGENT.equals(ml3AgentRoleBean.getRoleId())){
            entity.setAgentId(userId);
            entity.setUnitsId(UserUtils.getUser().getUnitsId());
            entity.setCenterId(UserUtils.getUser().getCenterId());
            entity.setRoleId(ml3AgentRoleBean.getRoleId());
        }else if(Constants.ML3_AGENT_ROLE.ROLE_UNITS.equals(ml3AgentRoleBean.getRoleId())){
            entity.setUnitsId(UserUtils.getUser().getUnitsId());
            entity.setCenterId(UserUtils.getUser().getCenterId());
            entity.setRoleId(ml3AgentRoleBean.getRoleId());
        }else if(Constants.ML3_AGENT_ROLE.ROLE_CENTER.equals(ml3AgentRoleBean.getRoleId())){
            entity.setCenterId(UserUtils.getUser().getCenterId());
            entity.setRoleId(ml3AgentRoleBean.getRoleId());
        }
        return entity;
    }

    /**
     * 查询市场管理部下会员单位的手续费出金记录
     * @param entity
     * @return
     */
    public GridPager queryReturnFeeMoneyByUnits(TradeReturnFeeRecord entity){
        List<ReturnFeeMemberUnits> returnFeeMemberUnitses = tradeReturnFeeDao.queryReturnFeeMoneyByUnits(entity);
        int total = (int) tradeReturnFeeDao.queryReturnFeeMoneyByUnitsTotal(entity);

        GridPager page = new GridPager();
        page.setRows(returnFeeMemberUnitses);
        page.setTotal(total);
        return page;
    }

    /**
     * 查询会员单位下代理商的手续费出金
     * @param entity
     * @return
     */
    public GridPager queryReturnFeeMoneyByAgent(TradeReturnFeeRecord entity){
        List<ReturnFeeAgent> returnFeeAgents = tradeReturnFeeDao.queryReturnFeeMoneyByAgent(entity);
        int total = (int) tradeReturnFeeDao.queryReturnFeeMoneyByAgentTotal(entity);

        GridPager page = new GridPager();
        page.setRows(returnFeeAgents);
        page.setTotal(total);
        return page;
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
        List<TradeReturnFeeRecord> returnFeeAgents = tradeReturnFeeDao.filterNoPassRecord(idList);
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
        List<TradeReturnFeeRecord> returnFeeAgents = tradeReturnFeeDao.filterNoPassRecord(idList);
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
            return new ExecuteResult(StatusCode.FAILED, "不可重复操作!");
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
            int update = tradeReturnFeeDao.updateReviewStatusAndStatus(record);
            if(1 == update){
                if(!Constants.WITHDRAW_STATUS.SUCCESS.equals(status)){
                    if("2".equals(returnFeeMoney.getType())){ //返会员单位
                        if(1 != tradeReturnFeeDao.returnWithDrawfeeMoneyByUnits(returnFeeMoney)){
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    }else if("3".equals(returnFeeMoney.getType())){ //返代理
                        if(1 != tradeReturnFeeDao.returnWithDrawfeeMoneyByAgent(returnFeeMoney)){
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    }else{

                    }
                }
                result[0] += 1;
            }else{
                result[1] += 1;
            }
        });
        return new ExecuteResult(StatusCode.OK,"审核成功：".concat(String.valueOf(result[0])).concat("笔,失败：").concat(String.valueOf(result[1])).concat("笔"));
    }

    @Override
    public List queryList(Object obj) {
        return null;
    }

    @Override
    public int insert(Object obj) {
        return 0;
    }

    @Override
    public int update(Object obj) {
        return 0;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public Object get(String id) {
        return null;
    }

    /**
     * 导出返手续费提现线下出金表格
     * @param ids
     * @return
     */
    public HSSFWorkbook exportAlaExcel(String ids){
        String[] idsArr = ids.split(",");
        List<String> stringList = Arrays.asList(idsArr);
        List<TradeReturnFeeRecord> list = tradeReturnFeeDao.filterNoPassRecord(stringList);
        HSSFWorkbook wb = new HSSFWorkbook();

        String[] headerColumn = new String[]{"id", "chnName", "bankAccount", "openBankName", "money"};
        int[] columnWidth = new int[]{20*180, 20*180, 20*340, 20*480, 20*180};
        final int[] count = {0};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("id", (Object obj) -> (++count[0]) + "");
        ExcelUtils.mapper(list, TradeReturnFeeRecord.class, wb, "确认提现报表", null, headerColumn, columnWidth, renderBox, 2);
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
        headerCell.setCellValue("商户返手续费提现表");

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
        String[] columnHeader = new String[]{"序号", "结算户名", "结算账号", "开户行名", "金额"};
        for (int i = 0; i < columnHeader.length; i++) {
            headerCell2 = headerRow2.createCell(i);
            headerCell2.setCellStyle(cellHeaderStyle2);
            headerCell2.setCellValue(columnHeader[i]);
        }

        return wb;
    }
}
