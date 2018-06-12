package com.rmkj.microcap.common.base;

import java.util.List;

/**
 * Created by zhangbowen on 2015/6/30.
 */
public interface IBaseService<T> {
    List<T> queryList(T obj);

    int insert(T obj);

    int update(T obj);

    int delete(String id);

    T get(String id);
}
