package com.rmkj.microcap.common.modules.sys.utils;


import com.alibaba.fastjson.TypeReference;
import com.rmkj.microcap.common.handler.SpringContextHolder;
import com.rmkj.microcap.common.modules.cache.CacheFacade;
import com.rmkj.microcap.common.modules.sys.bean.SysDictBean;
import com.rmkj.microcap.common.modules.sys.dao.ISysDictDao;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * <p/>
 * Created by zhangbowen on 2015/12/22.
 */
public class DictUtils {

    public static final String CACHE_DICT_MAP = "dictMap";
    private static ISysDictDao dictDao = SpringContextHolder.getBean(ISysDictDao.class);

    /**
     * 类型，值查询标题
     *
     * @param value        值
     * @param type         类型
     * @param defaultValue 默认值
     */
    public static String getDictLabel(String value, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (SysDictBean dict : getDictList(type)) {
                if (type.equals(dict.getType()) && value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    /**
     * 标题，类型查询值
     *
     * @param label        标题
     * @param type         类型
     * @param defaultLabel
     */
    public static String getDictValue(String label, String type, String defaultLabel) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
            for (SysDictBean dict : getDictList(type)) {
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())) {
                    return dict.getValue();
                }
            }
        }
        return defaultLabel;
    }

    /**
     * 标题，类型查询值
     *
     * @param label 标题
     * @param type  类型
     */
    public static SysDictBean getDict(String label, String type) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
            for (SysDictBean dict : getDictList(type)) {
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())) {
                    return dict;
                }
            }
        }
        return null;
    }

    /**
     * 类型查询字典列表
     *
     * @param type 类型
     */
    public static List<SysDictBean> getDictList(String type) {
        Map<String, List<SysDictBean>> dictMap = CacheFacade.getObject(CACHE_DICT_MAP, new TypeReference<Map<String, List<SysDictBean>>>() {
        });
        if (dictMap == null) {
            dictMap = new HashMap<>();
            SysDictBean d = new SysDictBean();
            for (SysDictBean dict : dictDao.findList(d)) {
                List<SysDictBean> dictList = dictMap.get(dict.getType());
                if (dictList != null) {
                    dictList.add(dict);
                } else {
                    List<SysDictBean> list = new ArrayList<>();
                    list.add(dict);
                    dictMap.put(dict.getType(), list);
                }
            }
            CacheFacade.set(CACHE_DICT_MAP, dictMap, 0);
        }
        List<SysDictBean> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = new ArrayList<>();
        }
        return dictList;
    }

    /**
     * 获取lables
     *
     * @param type
     */
    public static List<String> getDicLabelList(String type) {
        List<SysDictBean> dictList = DictUtils.getDictList(type);
        List<String> dictStrList = new ArrayList<String>();
        for (SysDictBean dict : dictList) {
            dictStrList.add(dict.getLabel());
        }
        return dictStrList;
    }
    /**
     * 类型，值查询标题
     *
     * @param values
     * @param type
     * @param defaultValue
     * @return
     */
    public static String getDictLabels(String values, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
            List<String> valueList = new ArrayList<>();
            for (String value : StringUtils.split(values, ",")) {
                valueList.add(getDictLabel(value, type, defaultValue));
            }
            return StringUtils.join(valueList, ",");
        }
        return defaultValue;
    }
    /**
     * 根据类型获取easyUi formatter标签
     *
     * @param type
     * @return
     */
    public static String getDictTypeFormatterTag(String type) {
        List<SysDictBean> dictList = getDictList(type);
        StringBuffer branchStr = new StringBuffer();//获取分支语句
        for (int i = 0, len = dictList.size(); i < len; i++) {
            SysDictBean dict = dictList.get(i);
            if (i == 0) {
                branchStr.append("if");
            } else {
                branchStr.append("else if");
            }
            branchStr.append("(value!=''&&value=='").append(dict.getValue()).append("'){");
            branchStr.append("return '").append(dict.getLabel()).append("';}");
        }
        String resultTag = "formatter:function(value){" + branchStr + "}";
        return resultTag;
    }
}
