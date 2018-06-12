//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rmkj.microcap.common.ext.freemarker;

import com.rmkj.microcap.common.handler.SpringContextHolder;
import com.rmkj.microcap.modules.index.service.ScheduleService;
import com.rmkj.microcap.modules.user.entity.Ml3MemberUnits;
import freemarker.template.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 重写内置request对象
 */
public class FreeMarkerView2 extends FreeMarkerView {

    private ScheduleService scheduleService;

    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        SimpleHash templateModel = super.buildTemplateModel(model, request, response);
        templateModel.put("request", request);
        String serverName = request.getServerName();
        String[] twoLevelDomain = new String[]{null};

        if(Pattern.matches("^[a-zA-Z0-9]+(.[a-zA-Z0-9]+){1,3}$", serverName) && serverName.indexOf(".") != -1){
            twoLevelDomain[0] = serverName.substring(0, serverName.indexOf("."));
        }

        templateModel.put("_title", null);
        if(twoLevelDomain[0] != null){
            if(scheduleService == null){
                scheduleService = SpringContextHolder.getBean(ScheduleService.class);
            }

            List<Ml3MemberUnits> ml3MemberUnitses = scheduleService.ml3MemberUnits();
            ml3MemberUnitses.forEach(ml3MemberUnits -> {
                if(twoLevelDomain[0].equals(ml3MemberUnits.getTwoLevelDomain())){
                    templateModel.put("_title", ml3MemberUnits.getName());
                }
            });
        }
        return templateModel;
    }

}
