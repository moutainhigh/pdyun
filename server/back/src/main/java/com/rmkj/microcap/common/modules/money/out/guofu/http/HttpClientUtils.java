package com.rmkj.microcap.common.modules.money.out.guofu.http;/**
 * Created by Administrator on 2017/10/18.
 */

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.guofu.entity.GuoFuResultBean;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author k
 * @create -10-18-15:57
 **/

public class HttpClientUtils {

    private static Logger logger = Logger.getLogger(HttpClientUtils.class);

    /**
     * 发送httpPost请求
     * @param url 请求路径
     * @param param url参数
     * @param encod 编码方式
     * @throws IOException
     */
    public static String doPost(String url, String param, String encod) throws IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entityParams = new StringEntity(param, encod);
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            //String execute = client.execute(httpPost, responseHandler);
            GuoFuResultBean guofuResultBean = null;
            String result = "";
            if (response != null && response.getEntity() != null) {
                result = EntityUtils.toString(response.getEntity());
                logger.info("代付请求返回字符串:".concat(result));
                 guofuResultBean = JSON.parseObject(result, GuoFuResultBean.class);
            }
            logger.info("代付请求返回json:".concat(JSON.toJSONString(guofuResultBean)));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(response.getEntity());
        }finally {
            if(response != null){
                response.close();
            }
            if(client != null){
                client.close();
            }
        }
        return null;
    }

    /**
     * 发送httpPost请求，结果不进行格式化，原样返回
     * @param url 请求路径
     * @param param 参数
     * @param encod 编码方式
     * @throws IOException
     */
    public static String doPostPre(String url, String param, String encod) throws IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entityParams = new StringEntity(param, encod);
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            //String execute = client.execute(httpPost, responseHandler);
            GuoFuResultBean guofuResultBean = null;
            String result = "";
            if (response != null && response.getEntity() != null) {
                result = EntityUtils.toString(response.getEntity());
                logger.info("代付请求返回字符串:".concat(result));
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(response.getEntity());
        }finally {
            if(response != null){
                response.close();
            }
            if(client != null){
                client.close();
            }
        }
        return null;
    }

    public static String map2str(Map<String, Object> param) {
        StringBuffer sb = new StringBuffer();
        boolean flag = true;
        Set<String> set = param.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = (String) param.get(key);
            if (flag) {
                flag = false;
            } else {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }
}
