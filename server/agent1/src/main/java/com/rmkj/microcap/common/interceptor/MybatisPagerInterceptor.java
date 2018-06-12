package com.rmkj.microcap.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.DataEntity;
import com.rmkj.microcap.common.bean.PageParams;
import com.rmkj.microcap.common.bean.PagerBean;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.handler.CustomMyBatisParameterHandler;
import com.rmkj.microcap.common.modules.mybatis.MapperFactoryBeanProcessor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangbowen on 2016/1/25.
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisPagerInterceptor implements Interceptor {
    private static final ThreadLocal<PagerBean> localPager = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> useAutoGetPageTotal = new ThreadLocal<>();

    private static Logger logger = Logger.getLogger(MybatisPagerInterceptor.class);


    /**
     * 所有PageParams的子类做参数的查询都会走自动分页并查询总数
     * <br/>为了提高查询总数的速度，满足自己编写总数查询SQL的业务需求，请在分页查询之前调用此方法，设置为false
     * @param isUse
     */
    public static void useAutoGetPageTotal(boolean isUse){
        useAutoGetPageTotal.set(isUse);
    }
    /**
     * 获得pager
     *
     * @return
     */
    public static PagerBean getPager() {
        PagerBean pagerBean = localPager.get();
        localPager.remove();
        return pagerBean;
    }

    public static int getTotal() {
        return getPager().getTotal();
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            if(MapperFactoryBeanProcessor.isMore(mappedStatement.getId())){
                PagerBean pagerBean = localPager.get();
                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
                // 分页参数作为参数对象parameterObject的一个属性
                String sql = boundSql.getSql();
                //重写分页sql
                metaStatementHandler.setValue("delegate.boundSql.sql", buildPageSql(sql, pagerBean));
                if(useAutoGetPageTotal.get() == null || useAutoGetPageTotal.get()){
                    Connection connection = (Connection) invocation.getArgs()[0];
                    // 重设分页参数里的总页数等
                    setPageParameter(sql, connection, mappedStatement, boundSql, pagerBean);
                }
            }
            useAutoGetPageTotal.remove();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        //判断是否为查询处理
        if (target instanceof StatementHandler) {
            StatementHandler statementHandler = ((StatementHandler) target);
            ParameterHandler parameterHandler = statementHandler.getParameterHandler();
            if (parameterHandler.getParameterObject() instanceof DataEntity || parameterHandler.getParameterObject() instanceof PageParams) {
                int page = -1;
                Integer rows = null;
                if (parameterHandler.getParameterObject() instanceof DataEntity){
                    DataEntity dataEntity = (DataEntity) parameterHandler.getParameterObject();
                    page = dataEntity.getPage();
                    rows = dataEntity.getRows();
                }
                if (parameterHandler.getParameterObject() instanceof PageParams){
                    PageParams pageParams = (PageParams) parameterHandler.getParameterObject();
                    page = pageParams.getPage();
                    rows = pageParams.getRows();
                }
                if (page != 0 && rows != null) {//如果start不为空说明为分页请求
                    localPager.set(new PagerBean((page - 1) * rows, rows));
                    target =  Plugin.wrap(target, this);
                }else{
                    useAutoGetPageTotal.remove();
                }
            }else{
                useAutoGetPageTotal.remove();
            }
            // debug模式时，打印sql语句
            if(ProjectConstants.PRO_DEBUG){
                logger.info("SQL: ".concat(statementHandler.getBoundSql().getSql().replaceAll("\\s{2,}", " ")));
                Object parameterObject = statementHandler.getBoundSql().getParameterObject();
                if(parameterObject != null){
                    List<ParameterMapping> parameterMappings = statementHandler.getBoundSql().getParameterMappings();
                    final StringBuilder sb = new StringBuilder();
                    Class<?> aClass = parameterObject.getClass();
                    final Map<String, Field> stringFieldMap = allFields(aClass);
                    if(!aClass.getName().startsWith("java.")){
                        parameterMappings.forEach(parameterMapping -> {
                            String property = parameterMapping.getProperty();
                            try {
                                Field field = stringFieldMap.get(property);
                                if(field != null){
                                    if(sb.length() != 0){
                                        sb.append(",");
                                    }
                                    field.setAccessible(true);
                                    sb.append(property.concat(":").concat(field.get(parameterObject)+""));
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }else{
                        sb.append(JSON.toJSONString(parameterObject));
                    }
                    logger.info("Parameters: [".concat(sb.toString()).concat("]"));
                }

            }
        }
        return target;
    }

    private Map<String, Field> allFields(Class<?> aClass){
        Map<String, Field> maps = new HashMap<>();
        while(aClass != Object.class){
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field: declaredFields){
                if(!maps.containsKey(field.getName())){
                    maps.put(field.getName(), field);
                }
            }
            aClass = aClass.getSuperclass();
        }
        return maps;
    }

    /**
     * 设置总数
     *
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param pagerBean
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, PagerBean pagerBean) {
        // 记录总记录数
        String countSql = generateCountSql(sql);
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            ParameterHandler parameterHandler = new CustomMyBatisParameterHandler(mappedStatement, boundSql.getParameterObject(), countBS);
            parameterHandler.setParameters(countStmt);
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
                if(ProjectConstants.PRO_DEBUG){
                    logger.info("总数自动查询mapId：".concat(mappedStatement.getId()).concat("，共：").concat(totalCount+""));
                }
            }
            pagerBean.setTotal(totalCount);
        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 生成获得总数的语句
     *
     * @param sql
     * @return
     */
    private String generateCountSql(String sql) {
        //去除order by
        Pattern pOrder = Pattern.compile("order\\s+by\\s+[\\w\\s,._-]+",Pattern.CASE_INSENSITIVE);
        Matcher mOrder = pOrder.matcher(sql);
        String delOrderSql = mOrder.replaceAll("");
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer
                .append("select count(1) as total from (")
                .append(delOrderSql)
                .append(") _total");
        return stringBuffer.toString();
    }

    /**
     * 修改原SQL为分页SQL
     *
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, PagerBean page) {
        StringBuilder pageSql = new StringBuilder(200);
        pageSql.append(sql).append(" LIMIT ").append(page.getStart()).append(",").append(page.getRows());
        return pageSql.toString();
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
