package com.rmkj.microcap.modules.subGoods.service;/**
 * Created by Administrator on 2018/4/26.
 */

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.upload.service.UploadService;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.subGoods.dao.SubGoodsDao;
import com.rmkj.microcap.modules.subGoods.dao.SubGoodsSpecDao;
import com.rmkj.microcap.modules.subGoods.entity.*;
import com.rmkj.microcap.modules.trade.dao.ITradeDao;
import com.rmkj.microcap.modules.trade.entity.TradeBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author k
 * @create -04-26-13:46
 **/
@Service
@Transactional
public class SubGoodsService {

    @Autowired
    private SubGoodsDao subGoodsDao;
    @Autowired
    private SubGoodsSpecDao subGoodsSpecDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private ITradeDao tradeDao;
    @Autowired
    private UploadService uploadService;

    /**
     * 查询全部认购商品
     * @param subGoods
     * @return
     */
    public GridPager queryList(SubGoods subGoods){
        GridPager page = new GridPager();
        List<SubGoods> list = subGoodsDao.queryList(subGoods);
        page.setRows(list);
        page.setTotal((int) subGoodsDao.queryListTotal(subGoods));
        return page;
    }

    public List<GoodsType> getGoodsType(){
        return subGoodsDao.getGoodsType();
    }

    /**
     * 添加认购商品，上传商品图片
     * @param entity
     * @param request
     * @return
     */
    public ExecuteResult saveSubGoods(SubGoods entity, MultipartHttpServletRequest request){
        //根据客户手机号查询客户信息 进行商品认购， 认购数量为 商铺总数 - (商品总数 * 认购比例)
        if(StringUtils.isEmpty(entity.getUserMobile())){
           return new ExecuteResult(StatusCode.FAILED, "请输入所属客户手机号码");
        }
        UserBean userBean = userDao.queryUserByMobile(entity.getUserMobile());
        if(null == userBean){
            return new ExecuteResult(StatusCode.FAILED, "不存在的用户");
        }

        Map<String, String> result = fileUpload(request);
        if(null == result){
            return new ExecuteResult(StatusCode.FAILED, "图片上传失败");
        }
        entity.setId(Utils.uuid());
        String[] path = result.get("path").split(",");
        String[] imgLoadPath = result.get("imgLoadPath").split(",");
        entity.setImgPath(path[0]);
        entity.setImgLoadPath(imgLoadPath[0]);
        entity.setImgDetailPath(path[1]);
        entity.setImgDetailLoadPath(imgLoadPath[1]);
        entity.setGoodsLeftNum(entity.getGoodsTotalNum());
        entity.setStatus(Constants.SUB_GOODS_STATUS.SUB);
        entity.setCreateTime(new Date());
        entity.setUserId(userBean.getId());
        //认购规格
        SubGoodsSpec subGoodsSpec = new SubGoodsSpec();
        subGoodsSpec.setId(Utils.uuid());
        subGoodsSpec.setGoodsId(entity.getId());
        subGoodsSpec.setSubTotalNum(entity.getSubTotalNum());
        subGoodsSpec.setSubMakeNum(entity.getSubMakeNum());
        subGoodsSpec.setSubSendNum(entity.getSubSendNum());

        //发布商品同时给客户认购剩余的商品
        double subScale = entity.getSubScale().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).doubleValue();
        double holeNum = entity.getGoodsTotalNum() - (entity.getGoodsTotalNum() * subScale);
        TradeBean tradeBean = new TradeBean();
        tradeBean.setId(Utils.uuid());
        tradeBean.setUserId(userBean.getId());
        tradeBean.setSerialNo(serialNo());
        tradeBean.setHoldNum((int) holeNum);
        tradeBean.setServiceFee(new BigDecimal(0));
        tradeBean.setMoney(new BigDecimal(0));
        tradeBean.setGoodsName(entity.getGoodsName());
        tradeBean.setGoodsId(entity.getId());
        tradeBean.setFeeBuy(new BigDecimal(0));
        tradeBean.setFeeSell(new BigDecimal(0));
        tradeBean.setBuyTime(new Date());
        tradeBean.setBuyPoint(entity.getGoodsSubPrice());
        tradeBean.setStatus(Integer.valueOf(Constants.SUB_GOODS_STATUS.SUB));
        //写入代理
        UserBean userLevel = userDao.queryUserLevelById(userBean.getId());
        tradeBean.setAgentId(userLevel.getAgentId());
        tradeBean.setUnitsId(userLevel.getUnitsId());
        tradeBean.setCenterId(userLevel.getCenterId());
        tradeBean.setHoldFlag("1");

        //保存认购商品和认购规格
        if(subGoodsDao.insert(entity) != 1 || subGoodsSpecDao.insert(subGoodsSpec) != 1 || tradeDao.saveSubGoodsMake(tradeBean) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ExecuteResult(StatusCode.FAILED, "发布认购商品失败");
        }
        return new ExecuteResult(StatusCode.OK, "发布认购商品成功");
    }

    private int i = 0;

    public Map<String, String> fileUpload(MultipartHttpServletRequest request){
        Map<String, String> imgInfo = null;
        //存放多个图片信息
        List<String> imgPathList = new ArrayList();
        List<String> imgLoadPathList = new ArrayList();

        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()){
            String path = "";
            MultipartFile file = request.getFile(fileNames.next());
            String fileName = file.getOriginalFilename();
            //创建文件目录
            File filePath = null;
            if(ProjectConstants.PRO_DEBUG){
                String debugPath = request.getSession().getServletContext().getRealPath("fileDir");
                filePath = new File(debugPath.concat(File.separator).concat(ProjectConstants.PIC_PATH_TYPE.CKEDITOR).concat(File.separator).concat(DateUtils.getDate("yyyyMMdd")));
            }else {
                filePath = new File(ProjectConstants.PIC_PATH.concat(File.separator).concat(DateUtils.getDate("yyyyMMdd")));
            }
            if(!filePath.exists()){
                filePath.mkdirs();
            }

            //写入绝对路径
            String imageName = DateUtils.getDate("yyyyMMddHHmmss").concat(String.valueOf(i)).concat(".jpg");
            File fileSrc = new File(filePath, imageName);
            try(FileOutputStream fos = new FileOutputStream(fileSrc);
                InputStream inputStream = file.getInputStream();){
                byte[] bb = new byte[1024];
                int read = -1;
                while ((read = inputStream.read(bb)) != -1){
                    fos.write(bb, 0, read);
                }
                path = fileSrc.getCanonicalPath();
                String ip = "";
                if(ProjectConstants.PRO_DEBUG){
                    ip = request.getRemoteAddr();
                }else{
                    ip = "pdshoucang.com";
                }
                String src  = request.getScheme() + "://" + ip + (request.getServerPort() == 80 ? "" : ":".concat(request.getServerPort()+"")) + request.getContextPath() + "/subgoods/show/" + DateUtils.getDate("yyyyMMdd")+ "/" + imageName;
                //记录图片路径
                imgPathList.add(path);
                imgLoadPathList.add(src);

            }catch (Exception e){
                e.printStackTrace();
            }
            imgInfo = new HashMap();
            i++;
        }
        //返回图片存放路径
        if(null == imgInfo){
            return null;
        }
        imgInfo.put("path", String.join(",", imgPathList));
        imgInfo.put("imgLoadPath", String.join(",", imgLoadPathList));
        return imgInfo;
    }

    public synchronized void fileReLoad(String fileName, String imgName, HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream outputStream = null;
        FileInputStream fis = null;
        try {
            response.setContentType("image/jpg");
            outputStream = response.getOutputStream();
            //读取图片路径
            File file = null;
            if(ProjectConstants.PRO_DEBUG){
                String debugPath = request.getSession().getServletContext().getRealPath("fileDir");
                file = new File(debugPath.concat(File.separator).concat(ProjectConstants.PIC_PATH_TYPE.CKEDITOR).concat(File.separator).concat(fileName), imgName.concat(".jpg"));
            }else{
                file = new File(ProjectConstants.PIC_PATH.concat(File.separator).concat(fileName), imgName.concat(".jpg"));
            }
            if(file.exists()){
                if(file.exists()){
                    fis = new FileInputStream(file);
                    byte[] bb = new byte[1024];
                    int read = -1;
                    while((read = fis.read(bb)) != -1){
                        outputStream.write(bb, 0, read);
                    }
                    fis.close();
                    fis = null;
                }
            }
            outputStream.flush();
            outputStream.close();

            outputStream = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SubGoods getById(String id){
        return subGoodsDao.getById(id);
    }

    /**
     * 修改商品信息
     * @param subGoods
     * @return
     */
    public ExecuteResult updateSubGoods(SubGoods subGoods){
        SubGoodsSpec subGoodsSpec = new SubGoodsSpec();
        subGoodsSpec.setGoodsId(subGoods.getId());
        subGoods.setSubTotalNum(subGoods.getSubTotalNum());
        subGoodsSpec.setSubMakeNum(subGoods.getSubMakeNum());
        subGoodsSpec.setSubSendNum(subGoods.getSubSendNum());
        if(subGoodsDao.update(subGoods) != 1 || subGoodsSpecDao.updateByGoodsId(subGoodsSpec) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ExecuteResult(StatusCode.FAILED, "修改失败");
        }
        return new ExecuteResult(StatusCode.OK, "修改成功");
    }

    public ExecuteResult updateStatusByIdClose(String id){
        if(subGoodsDao.updateStatusById(id, Constants.SUB_GOODS_STATUS.SOLD_OUT) != 1){
            return new ExecuteResult(StatusCode.FAILED, "下架失败");
        }
        return new ExecuteResult(StatusCode.OK, "下架成功");
    }

    public ExecuteResult updateStatusByIdOpen(String id){
        if(subGoodsDao.updateStatusById(id, Constants.SUB_GOODS_STATUS.SUB) != 1){
            return new ExecuteResult(StatusCode.FAILED, "上架失败");
        }
        return new ExecuteResult(StatusCode.OK, "上架成功");
    }

    public GridPager takeGoodsList(TakeGoodsParam takeGoodsParam){
        GridPager page = new GridPager();
        List<TakeGoodsBean> list = subGoodsDao.takeGoodsList(takeGoodsParam);
        page.setRows(list);
        page.setTotal(subGoodsDao.countTakeGoodsList(takeGoodsParam));
        return page;
    }


    private final Random random = new Random();
    /**
     * 获取交易流水号
     * @return
     */
    private String serialNo(){
        return "JY" + new Date().getTime()+""+String.format("%1$03d",random.nextInt(1000));
    }

    public ExecuteResult goodsOperate(String id, String operate){
        Map<String,Object> param = new HashedMap();
        param.put("id",id);
        if(operate.equals("pass")){//通过
            param.put("status","4");
        }else if(operate.equals("frozen")){//冻结
            param.put("status","2");
        }else{//refuse 拒绝c
            param.put("status","3");
            //获取提货订单
            TakeGoodsBean takeGoodsBean = subGoodsDao.getTakeGoods(id);
            //获取原订单
            SubTradeBean subTradeBean = subGoodsDao.getSubTrade(id);
            if(subTradeBean.getStatus().equals("1")){//状态：1 持仓  2挂单 3 平仓（交割） 4 下架
                subTradeBean.setHoldNum(subTradeBean.getHoldNum()+takeGoodsBean.getGoodsNum());
                subTradeBean.setServiceFee(subTradeBean.getServiceFee().add(takeGoodsBean.getServiceFee()));
                subTradeBean.setFeeBuy(subTradeBean.getFeeBuy().add(takeGoodsBean.getBuyFee()));
                subTradeBean.setMoney(subTradeBean.getMoney().add(takeGoodsBean.getMoney()));
                if(subGoodsDao.returnOldSubTrade(subTradeBean)!=1){
                    return new ExecuteResult(StatusCode.FAILED, "操作失败");
                }
            }else{
                SubTradeBean trade = new SubTradeBean();
                trade.setId(Utils.uuid());
                String prefix = subTradeBean.getSerialNo().substring(0,2);
                trade.setSerialNo(serialNo(prefix));
                trade.setUserId(subTradeBean.getUserId());
                trade.setGoodsId(takeGoodsBean.getGoodsId());
                trade.setGoodsName(takeGoodsBean.getGoodsName());
                trade.setHoldNum(takeGoodsBean.getGoodsNum());
                trade.setMoney(takeGoodsBean.getMoney());
                trade.setFeeBuy(takeGoodsBean.getBuyFee());
                trade.setServiceFee(takeGoodsBean.getServiceFee());
                trade.setFeeSell(new BigDecimal(0));
                trade.setBuyPoint(subTradeBean.getBuyPoint());
                trade.setBuyTime(subTradeBean.getBuyTime());
                trade.setStatus("1");
                User user = userDao.findUserById(subTradeBean.getUserId());
                trade.setCenterId(user.getCenterId());
                trade.setUnitsId(user.getUnitsId());
                trade.setAgentId(user.getAgentId());
                trade.setHoldFlag(subTradeBean.getHoldFlag());
                trade.setParent1Id(user.getParent1Id());
                trade.setParent2Id(user.getParent2Id());
                trade.setParent3Id(user.getParent3Id());
                if(subGoodsDao.createNewTrade(trade)!=1){
                    return new ExecuteResult(StatusCode.FAILED, "操作失败");
                }
            }
        }
        if(subGoodsDao.goodsOperate(param)!=1){
            return new ExecuteResult(StatusCode.FAILED, "操作失败");
        }
        return new ExecuteResult(StatusCode.OK);
    }

    public String serialNo(String type){
        String prefix = type;
        return type + new Date().getTime()+""+String.format("%1$03d",random.nextInt(1000));
    }

    public GridPager getIntegralList(String mobile){
        GridPager page = new GridPager();
//      List<IntegralBean> integralList = subGoodsDao.getIntegralList(mobile);
        page.setRows(subGoodsDao.getIntegralList(mobile));
        page.setTotal(subGoodsDao.countIntegral(mobile));
        return page;
    }

    //导出到Excel
    public HSSFWorkbook exportExcel(IntegralBean integralBean) {
        integralBean.setPage(1);
        integralBean.setRows(ExcelUtils.SHEET_MAX_SIZE);
        HSSFWorkbook wb = new HSSFWorkbook();
        String[] header = new String[]{"id","用户名","手机号","类型","积分变更","积分变更前","积分变更后","积分变更时间","备注"};
        String[] headerColumn = new String[]{"id","userName","mobile","type","integral","integralBefore","integralAfter","createTime","remark"};
        int[] columnWidth = new int[]{20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("type", (Object obj) -> {
            IntegralBean u = (IntegralBean)obj;
            return u.getType().equals("1") ? "正积分" : "负积分";
        });
        ExcelUtils.work(new ExcelUtils.DataFromDB<IntegralBean>(){
            private int page = 1;
            @Override
            public List<IntegralBean> find() {
                List<IntegralBean> integralList = subGoodsDao.getIntegralList(integralBean.getMobile());
                integralBean.setPage(++page);
                return integralList;
            }
            @Override
            public long totalPage() {
                return (long)Math.ceil(subGoodsDao.countIntegral(integralBean.getMobile())/integralBean.getRows().doubleValue());
            }
        }, IntegralBean.class, wb, header, headerColumn, columnWidth, renderBox);
        return wb;
    }
}
