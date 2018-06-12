package com.rmkj.microcap.modules.weiXinMenu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.modules.weixin.http.WeiXinApi;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.weiXinMenu.dao.WeiXinDao;
import com.rmkj.microcap.modules.weiXinMenu.entity.EventClick;
import com.rmkj.microcap.modules.weiXinMenu.entity.MediaBean;
import com.rmkj.microcap.modules.weiXinMenu.entity.WeixinMenuBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renwp on 2016/12/19.
 */
@Service
@Transactional
public class WeixinMenuService {

    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private WeiXinDao weiXinDao;

    @HttpService
    private WeiXinApi weiXinApi;

    public List<WeixinMenuBean> query(){
        try {
            weiXinService.initToken();
            Response<String> execute = weiXinApi.get().execute();
            if(execute.isSuccessful()){
                List<WeixinMenuBean> array = new ArrayList<>();
                String body = execute.body();
                JSONObject jsonObject = JSONObject.parseObject(body);
                JSONObject menu = jsonObject.getJSONObject("menu");
                if(menu == null){
                    return array;
                }
                JSONArray buttons = menu.getJSONArray("button");
                buttons.forEach(str -> {
                    JSONObject button = JSONObject.parseObject(str.toString());
                    WeixinMenuBean weiXinMenuBean1 = new WeixinMenuBean();
                    weiXinMenuBean1.setName(button.getString("name"));
                    weiXinMenuBean1.setUrl(button.getString("url"));
                    weiXinMenuBean1.setType(button.getString("type"));
                    weiXinMenuBean1.setKey(button.getString("key"));
                    if(StringUtils.isNotBlank(weiXinMenuBean1.getKey())){
                        weiXinMenuBean1.setContent(weiXinDao.getContentById(weiXinMenuBean1.getKey()));
                    }
                    weiXinMenuBean1.setMediaId(button.getString("media_id"));

                    JSONArray sub_button = button.getJSONArray("sub_button");
                    sub_button.forEach(sub_str -> {
                        JSONObject sutton111 = JSONObject.parseObject(sub_str.toString());
                        WeixinMenuBean sub_weiXinMenuBean = new WeixinMenuBean();
                        sub_weiXinMenuBean.setName(sutton111.getString("name"));
                        sub_weiXinMenuBean.setUrl(sutton111.getString("url"));
                        sub_weiXinMenuBean.setType(sutton111.getString("type"));
                        sub_weiXinMenuBean.setKey(sutton111.getString("key"));
                        if(StringUtils.isNotBlank(sub_weiXinMenuBean.getKey())){
                            sub_weiXinMenuBean.setContent(weiXinDao.getContentById(sub_weiXinMenuBean.getKey()));
                        }
                        sub_weiXinMenuBean.setMediaId(sutton111.getString("media_id"));

                        sub_weiXinMenuBean.setParentName(weiXinMenuBean1.getName());

                        array.add(sub_weiXinMenuBean);
                    });
                    array.add(weiXinMenuBean1);
                });
                return array;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String _query(){
        try {
            weiXinService.initToken();
            Response<String> execute = weiXinApi.get().execute();
            if(execute.isSuccessful()){
                String body = execute.body();
                System.out.println(body);
                return body;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param button
     * @param type add delete update
     * @param parentName
     * @return
     */
    public boolean create(JSONObject button, String type, String parentName){
        try {
            weiXinService.initToken();
            if(button == null){
                return false;
            }
            String query = _query();
            JSONObject oraign = JSONObject.parseObject(query);
            JSONObject menu = oraign.getJSONObject("menu");
            if(menu == null){
                menu = new JSONObject();
            }
            JSONArray buttons = menu.getJSONArray("button");
            if(buttons == null){
                buttons = new JSONArray();
            }
            JSONObject b = null;
            JSONObject sub_b = null;
            if(StringUtils.isBlank(parentName)){
                if("update".equals(type) || "delete".equals(type)){
                    for(int i = 0; i < buttons.size(); i++){
                        b = buttons.getJSONObject(i);
                        if(b.getString("name").equals(button.getString("name"))){
                            buttons.remove(i);
                            if("update".equals(type)){
                                if(button.containsKey("new_name")){
                                    button.put("name", button.getString("new_name"));
                                }
                                button.put("new_name",null);
                                button.put("sub_button", b.getJSONArray("sub_button"));
                                if(button.getString("type") == null || "view".equals(button.getString("type"))){
                                    button.put("type","view");
                                    if(StringUtils.isBlank(button.getString("url"))){
                                        button.put("url","http://woqunianmailegebiao");
                                    }
                                }else if("click".equals(button.getString("type"))){
                                    addEventClick(button);
                                }
                                buttons.add(i,button);
                            }
                            break;
                        }
                    }
                }else if("add".equals(type)){
                    if(button.getString("type") == null || "view".equals(button.getString("type"))){
                        button.put("type", "view");
                        if(StringUtils.isBlank(button.getString("url"))){
                            button.put("url", "http://woqunianmailegebiao");
                        }
                    }else if("click".equals(button.getString("type"))){
                        addEventClick(button);
                    }
//                    button.put("sub_button", new JSONArray());
                    buttons.add(button);
                }else{
                    return false;
                }
            }else{
                JSONArray subButtons = null;
                int p = -1;
                for(int i = 0; i < buttons.size(); i++){
                    b = buttons.getJSONObject(i);
                    if(b.getString("name").equals(parentName)){
                        subButtons = b.getJSONArray("sub_button");
                        if(subButtons == null){
                            subButtons = new JSONArray();
                        }
                        p = i;
                        break;
                    }
                }
                if("update".equals(type) || "delete".equals(type)){
                    for(int j = 0; j < subButtons.size(); j++){
                        sub_b = subButtons.getJSONObject(j);
                        if(sub_b.getString("name").equals(button.getString("name"))){
                            subButtons.remove(j);
                            if("update".equals(type)){
                                if(button.containsKey("new_name")){
                                    button.put("name", button.getString("new_name"));
                                }
                                if(button.getString("type") == null || "view".equals(button.getString("type"))){
                                    button.put("type", "view");
                                }else if("click".equals(button.getString("type"))){
                                    addEventClick(button);
                                }
                                subButtons.add(j, button);
                            }
                            break;
                        }
                    }
                }else if("add".equals(type)){
                    if(button.getString("type") == null){
                        button.put("type", "view");
                    }else if("click".equals(button.getString("type")) || "view".equals(button.getString("type"))){
                        addEventClick(button);
                    }
                    subButtons.add(button);
                }else{
                    return false;
                }

                if(p != -1){
                    b.put("sub_button", subButtons);
                    buttons.remove(p);
                    buttons.add(p, b);
                }
            }

            if(buttons.isEmpty()){
                Response<String> execute = weiXinApi.delete().execute();
                if(execute.isSuccessful()){
                    return execute.body().startsWith("{\"errcode\":0");
                }
            }else{
                menu.put("button", buttons);
                Response<String> execute = weiXinApi.create(menu).execute();
                if(execute.isSuccessful()){
                    return execute.body().startsWith("{\"errcode\":0");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addEventClick(JSONObject button){
        String content = button.getString("content");
        String key = button.getString("key");
        weiXinDao.deleteEventClick(key);
        EventClick eventClick = new EventClick();
        eventClick.setId(Utils.uuid());
        eventClick.setContent(content);
        weiXinDao.addEventClick(eventClick);
        button.put("key", eventClick.getId());
    }

    public int insert(WeixinMenuBean obj){
        return 1;
    }

    public int update(WeixinMenuBean obj){
        return 1;
    }

    public int delete(String id){
        return 1;
    }

    public WeixinMenuBean get(String id){
        return new WeixinMenuBean();
    }

    /**
     * 新增永久素材
     * @param jsonObject
     */
    public void createMedia(JSONObject jsonObject) {
        // TODO 新增永久素材
    }

    /**
     * 查询素材列表
     * @param type
     * @param offset
     * @param count
     * @return
     */
    public GridPager queryMedias(String type, int offset, int count) {
        String jsonStr = weiXinService.queryMedias(type, offset, count);
        GridPager gp = new GridPager();
        if(StringUtils.isNotBlank(jsonStr)){
            JSONObject obj = JSON.parseObject(jsonStr);
            gp.setRows(JSON.parseArray(obj.getString("item"), MediaBean.class));
            gp.setTotal(obj.getIntValue("total_count"));
        }
        return gp;
    }

    /**
     * 查询临时素材列表
     * @param type
     * @param offset
     * @param count
     * @return
     */
    public GridPager queryTempMedias(String type, int offset, int count) {
        String jsonStr = weiXinService.queryTempMedias(type, offset, count);
        GridPager gp = new GridPager();
        if(StringUtils.isNotBlank(jsonStr)){
            JSONObject obj = JSON.parseObject(jsonStr);
            gp.setRows(JSON.parseArray(obj.getString("item"), MediaBean.class));
            gp.setTotal(obj.getIntValue("total_count"));
        }
        return gp;
    }

}
