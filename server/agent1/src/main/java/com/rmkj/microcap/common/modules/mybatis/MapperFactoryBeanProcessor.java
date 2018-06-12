package com.rmkj.microcap.common.modules.mybatis;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by renwp on 2017/3/30.
 */
public class MapperFactoryBeanProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    private static final Set<String> selectMoreMethod = new TreeSet<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof MapperFactoryBean){
            MapperFactoryBean mapperFactoryBean = (MapperFactoryBean) bean;
            Class mapperInterface = mapperFactoryBean.getMapperInterface();
            addSelectMoreMethod(beanName, mapperInterface);
            //继承的接口
            Class[] interfaces = mapperInterface.getInterfaces();
            for (Class anInterface : interfaces) {
                addSelectMoreMethod(beanName, anInterface);
            }
        }
        return super.postProcessAfterInitialization(bean, beanName);
    }

    private void addSelectMoreMethod(String beanName, Class mapperInterface){
        Method[] declaredMethods = mapperInterface.getMethods();
        for (Method method :declaredMethods){
            if(method.getReturnType().isAssignableFrom(List.class) || method.getReturnType().isArray()){
                selectMoreMethod.add(beanName.concat(".").concat(method.getName()));
            }
        }
    }

    /**
     *
     * @param mapperId
     * @return
     */
    public static boolean isMore(String mapperId){
        return selectMoreMethod.contains(mapperId);
    }
}
