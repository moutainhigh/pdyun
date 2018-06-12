package com.rmkj.microcap.common.base;

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.modules.sys.bean.Ml3RoleBean;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    /**
     * 日志对象
     */
    protected Logger logger = Logger.getLogger(getClass());

    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */

    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }
    /**
     * 根据页数与每页显示条数计算便宜
     *
     * @param page
     * @param rows
     * @return
     * @author
     * @since 2014年12月4日
     */
    protected int evalStart(int page, int rows) {
        return (page - 1) * rows;
    }
    /**
     * 解析异常参数返回错误信息
     *
     * @param errors
     * @return
     */
    protected ExecuteResult parseErrors(Errors errors) {
        return new ExecuteResult(StatusCode.VALIDATION_FAILED, errors.getFieldError().getField() + ":" + errors.getFieldError().getDefaultMessage());
    }

    protected void filterRole(Model model){
        List<Ml3RoleBean> roleList = UserUtils.getUser().getRoleList();
        roleList.forEach(ml3RoleBean -> {
            if("2".equals(ml3RoleBean.getId()) || "5".equals(ml3RoleBean.getId())){
                model.addAttribute("isUnits", true);
            }else if("3".equals(ml3RoleBean.getId()) || "4".equals(ml3RoleBean.getId())){
                model.addAttribute("isAgent", true);
            }
        });
    }
}
