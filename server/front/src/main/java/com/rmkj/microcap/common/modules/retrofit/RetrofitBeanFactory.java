package com.rmkj.microcap.common.modules.retrofit;

import com.rmkj.microcap.common.modules.retrofit.bean.HttpApiBean;
import com.rmkj.microcap.common.modules.retrofit.bean.RetrofitBean;
import com.rmkj.microcap.common.modules.retrofit.convert.FastJsonConverterFactory;
import com.rmkj.microcap.common.modules.retrofit.interceptor.LoggingInterceptor;
import com.rmkj.microcap.common.utils.Utils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import retrofit2.Retrofit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangbowen on 2016/5/12.
 */
public class RetrofitBeanFactory {
    //key:请求地址 value:当前请求地址下class所对应的service（key:class value:service）
    private static Map<String, RetrofitBean> resolvableDependencies = new HashMap(16);
    private static final int readTimeOut = 15;
    private static final int writeTimeOut = 15;
    private static final int connTimeOut = 15;

    /**
     * 获得service服务实体
     *
     * @param requiredType
     * @return
     * @throws BeansException
     */
    public static Object getBean(Class requiredType) throws BeansException {
        for (Map.Entry<String, RetrofitBean> entrySet : resolvableDependencies.entrySet()) {
            RetrofitBean retrofitBean = entrySet.getValue();
            for (Map.Entry<Class, Object> serviceSet : retrofitBean.getService().entrySet()) {
                if (requiredType == serviceSet.getKey()) {
                    return serviceSet.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 创建service服务实体
     *
     * @param httpApiBean
     */
    public static Object putBean(HttpApiBean httpApiBean) {
        String baseUrl = httpApiBean.getBaseUrl();
        Class[] interceptorClass = httpApiBean.getInterceptors();
        Class serviceClass = httpApiBean.getClazz();
        if (StringUtils.isEmpty(baseUrl)) {
            return null;
        }
        RetrofitBean retrofitBean = resolvableDependencies.get(baseUrl);
        //如果为空设置一个
        if (retrofitBean == null) {
            retrofitBean = new RetrofitBean();
            OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder()
                    .connectTimeout(readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                    .readTimeout(connTimeOut, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor());
            if (interceptorClass != null && interceptorClass.length > 0) {
                for (Class clazz : interceptorClass) {
                    if (Interceptor.class.isAssignableFrom(clazz)) {
                        try {
                            clientBuilder.addInterceptor((Interceptor) clazz.newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(clientBuilder.build())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();
            retrofitBean.setRetrofit(retrofit);
            resolvableDependencies.put(baseUrl, retrofitBean);
        }
        Retrofit retrofit = retrofitBean.getRetrofit();
        Object bean = retrofit.create(serviceClass);
        retrofitBean.getService().put(serviceClass, bean);
        return bean;
    }

    public static Map<String, RetrofitBean> getResolvableDependencies() {
        return resolvableDependencies;
    }

    /**
     * 设置证书
     *
     * @param clientBuilder
     * @param certPath
     */
    public static void setCertificates(OkHttpClient.Builder clientBuilder, String certPath, String password) {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            char[] bytePwd = password.toCharArray();
            InputStream certificate = Utils.getClassPathFileStream(certPath);
            ks.load(certificate, bytePwd);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, bytePwd);
            SSLContext ssl = SSLContext.getInstance("TLS");
            ssl.init(kmf.getKeyManagers(), null, null);
            clientBuilder.sslSocketFactory(ssl.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
