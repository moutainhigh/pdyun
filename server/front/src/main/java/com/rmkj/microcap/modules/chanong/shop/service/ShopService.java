package com.rmkj.microcap.modules.chanong.shop.service;

import com.rmkj.microcap.modules.chanong.index.dao.IndexDao;
import com.rmkj.microcap.modules.chanong.shop.dao.ShopDao;
import com.rmkj.microcap.modules.trade.entity.Contract;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by jinghao on 2018/4/24.
 */
@Service
public class ShopService {
    private static Logger logger = Logger.getLogger(ShopService.class);
    @Autowired
    private ShopDao shopDao;

    /**
     * 获取产品
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getContract(){
        Map<String,Object> ret = new HashedMap();
        ret.put("list",shopDao.getContract());
        return ret;
    }

}
