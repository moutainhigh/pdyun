package com.rmkj.microcap.modules.weiXinMenu.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.weiXinMenu.entity.EventClick;

/**
 * Created by renwp on 2016/12/24.
 */
@DataSource
public interface WeiXinDao {

    int deleteEventClick(String key);

    int addEventClick(EventClick eventClick);

    String getContentById(String key);
}
