package com.rmkj.microcap.common.modules.cache.impl;


import com.rmkj.microcap.common.modules.cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangbowen on 2016/4/5.
 */
public class LocalCache implements Cache {
    // 缓存
    private Map<String, Object> maps = null;

    public LocalCache() {
        maps = new ConcurrentHashMap<>();
    }

    @Override
    public void set(String key, Object value, int exp) {
        CacheObj cacheObj = new CacheObj();
        cacheObj.setObj(value);
        cacheObj.setSaveTime(System.currentTimeMillis());
        cacheObj.setExp(exp);
        maps.put(key, cacheObj);
    }

    /*
             * 删除缓存数据
             */
    @Override
    public void delete(String key) {
        maps.remove(key);
    }

    /*
     * 获取缓存数据,如果关键字不存在返回null
     */
    @Override
    public Object get(String key) {
        CacheObj cacheObj = (CacheObj) maps.get(key);
        //说明没过期
        if (cacheObj != null && (cacheObj.getExp() == 0 || ((System.currentTimeMillis() - cacheObj.getSaveTime()) <= cacheObj.getExp() * 1000))) {
            return cacheObj.getObj();
        }
        return null;
    }

    @Override
    public void shutdown() {

    }

    private class CacheObj {
        //对象
        private Object obj;
        //缓存时间
        private int exp;
        //存入时间
        private long saveTime;

        public long getSaveTime() {
            return saveTime;
        }

        public void setSaveTime(long saveTime) {
            this.saveTime = saveTime;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }
    }
}
