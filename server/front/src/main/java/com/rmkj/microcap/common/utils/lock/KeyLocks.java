package com.rmkj.microcap.common.utils.lock;

import org.apache.commons.collections.map.HashedMap;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * Created by renwp on 2017/5/18.
 */
public final class KeyLocks {

    private Map<String, SoftReference<Object>> lockMap = new HashedMap();

    public KeyLocks() {
    }

    public Object lockByKey(String key){
        Object lock;
        synchronized (this){
            SoftReference<Object> objectSoftReference = lockMap.get(key);
            if(objectSoftReference == null || (lock = objectSoftReference.get()) == null){
                lock = new Object();
                lockMap.put(key, new SoftReference<>(lock));
            }
        }
        return lock;
    }

}
