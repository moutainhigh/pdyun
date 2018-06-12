package com.rmkj.microcap.modules.chanong.index.service;

import com.rmkj.microcap.modules.chanong.index.dao.IndexDao;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by jinghao on 2018/4/24.
 */
@Service
public class IndexService {
    private static Logger logger = Logger.getLogger(IndexService.class);
    @Autowired
    private IndexDao indexDao;

    /**
     * 获取当前价格
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getCurPrice(String id){
        Map<String,Object> ret = new HashedMap();
        if(StringUtils.isEmpty(id)){
            id = "1";
        }
        String prefix = indexDao.getCurPrice(id);
        String suffix = String.valueOf((int)(Math.random()*99));
        for(int i=2;i>suffix.length();i--){
            suffix = "0".concat(suffix);
        }
        ret.put("curPrice",prefix.concat(".").concat(suffix));
        return ret;
    }

    /**
     * 获取公告
     * @param type
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getNewsFlash(String type){
        Map<String,Object> ret = new HashedMap();
        if(StringUtils.isEmpty(type)){
            type = "2";
        }
        ret.put("list",indexDao.getNewsFlash(type));
        return ret;
    }

    /**
     * 获取单条公告
     * @param id
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getNewsById(String id){
        Map<String,Object> ret = new HashedMap();
        ret.put("news",indexDao.getNewsById(id));
        return ret;
    }

}
