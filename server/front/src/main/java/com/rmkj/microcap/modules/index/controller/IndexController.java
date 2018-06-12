package com.rmkj.microcap.modules.index.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.modules.index.entity.Broadcast;
import com.rmkj.microcap.modules.index.service.BroadcastService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by renwp on 2016/10/18.
 */
@RestController
@RequestMapping("${v1}/index")
public class IndexController extends BaseController {

    @Autowired
    private BroadcastService broadcastService;

    @RequestMapping(value = "/broadcastList", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity broadcastList(){
        List<Broadcast> list = broadcastService.findList();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/winProfit", method = RequestMethod.GET)
    public ResponseEntity queryWinProfitRecord(Map<String, Object> result){

        result.put("winProfitList", broadcastService.queryWinProfitRecord());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/broadcast", method = RequestMethod.GET)
    public ResponseEntity queryBroadcast(Broadcast broadcast){
        Map<String, Object> result = new HashedMap();
        result.put("broadcastList", broadcastService.queryBroadcastList(broadcast));
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
