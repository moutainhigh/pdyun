package com.rmkj.microcap.modules.parameterSet.service;
/**
 * Created by Administrator on 2016/12/19.
 */
import com.rmkj.microcap.modules.parameterSet.dao.ParameterSetDao;
import com.rmkj.microcap.modules.parameterSet.entity.ParameterSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author k
 * @create -12-19-14:50
 **/
@Service
public class ParameterService {

    @Autowired
    private ParameterSetDao parameterSetDao;

    /**
     * TODO 查询参数设置表
     * @return
     */
    public ParameterSet findParameterSetList(){
        return parameterSetDao.findParameterSet();
    }

    public String getQrCodeMenuUrl(){
        return parameterSetDao.getQrCodeMenuUrl();
    }
}
