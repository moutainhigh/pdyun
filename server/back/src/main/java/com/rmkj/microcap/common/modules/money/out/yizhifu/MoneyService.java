package com.rmkj.microcap.common.modules.money.out.yizhifu;

import com.capinfo.crypt.Md5;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by renwp on 2017/1/4.
 */
@Service
public class MoneyService {

    private final Logger Log = Logger.getLogger(MoneyService.class);

    @HttpService
    private MoneyOutApi moneyOutApi;

    @HttpService
    private BankNoApi bankNoApi;

    @Value("${money_out_yizhifu_mid}")
    private String v_mid;
    @Value("${money_out_yizhifu_secret}")
    private String v_key;

    public String queryOverMoney(){
        try {
            //
            Response<byte[]> execute = moneyOutApi.queryOverMoney(v_mid, md5(v_mid)).execute();
            if(execute.isSuccessful()){
                String result = new String(execute.body());
                Log.info(result);
                return sub(result, "balance");
            }else {
                Log.error(execute.errorBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 0－正常
     * 1－错误
     * @param v_data
     * @return
     */
    public String[] batchOut(String v_data){
        try {
            //
            Log.info("v_data=".concat(v_data));
            String v_mac = md5(v_mid.concat(URLEncoder.encode(v_data, "UTF-8")));
            String v_version = "1.0";
            Response<byte[]> execute = moneyOutApi.batchOut(v_mid, v_data, v_mac, v_version).execute();
            if(execute.isSuccessful()){
                String result = new String(execute.body());
                Log.info(result);
                return new String[]{sub(result, "status"), sub(result, "statusdesc")};
            }else {
                Log.error(execute.errorBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 0-未处理，
     * 1-已成功
     * 2-处理中
     * 3-已失败
     * 4-待处理（自动处理程序状态）
     * 8-没有该用户标识对应的代付记录
     * 9-查询失败
     * 提现结果查询
     * @param v_data 提现记录表ID
     */
    public String queryResult(String v_data){
        try {
            //
            String v_mac = md5(v_mid.concat(URLEncoder.encode(v_data, "UTF-8")));
            String v_version = "1.0";
            Response<byte[]> execute = moneyOutApi.queryResult(v_mid, v_data, v_mac, v_version).execute();
            if(execute.isSuccessful()){
                String result = new String(execute.body());
                Log.info(result);
                return sub(result, "status");
            }else {
                Log.error(execute.errorBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化联行号
     */
    public void initBankNo(){
//        File file = null;
//        FileOutputStream fos = null;
//        try {
//            Response<InputStream> execute = bankNoApi.lianhang().execute();
//            if(execute.isSuccessful()){
//                file = new File("C:\\Users\\Administrator\\Desktop\\".concat(new Random().nextInt(10000)+"").concat(".txt"));
//                fos = new FileOutputStream(file);
//                byte[] bb = new byte[1024];
//                int read = -1;
//                while((read = execute.body().read(bb)) != -1){
//                    fos.write(bb, 0, read);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if(fos != null){
//                    fos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        // TODO 初始化联行号
        File file = new File("C:\\Users\\Administrator\\Desktop\\".concat("lianhang").concat(".txt"));
        FileReader fr = null;
        try {
            fr = new FileReader(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(fr != null){
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String md5(String source) {
        Md5 md5 = new Md5("");
        try {
            md5.hmac_Md5(source, v_key) ;
            return md5.stringify(md5.getDigest());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * xml字符串中取第一个标签名为tag的其内容
     * @param source
     * @param tag
     * @return
     */
    public String sub(String source, String tag){
        if(source.indexOf("<".concat(tag).concat(">")) == -1){
            return null;
        }
        return source.substring(source.indexOf("<".concat(tag).concat(">"))+2+tag.length(), source.indexOf("</".concat(tag).concat(">")));
    }

    /**
     * 批次号：代付商户设置开通代付商户文件批次唯一性检查
     * 批次号格式：商户编号-日期(yyyymmdd)-顺序号 例： 888-20140709-000001 保证批次号的唯一性(总长不超过 30 位字符)
     * @return
     */
    public String batchNo(){
        return v_mid.concat("-").concat(String.format("%1$tY%1$tm%1$td-%1$tH%1$tM%1$tS", new Date()));
    }

}
