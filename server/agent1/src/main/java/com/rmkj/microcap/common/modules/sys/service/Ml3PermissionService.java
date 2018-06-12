package com.rmkj.microcap.common.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.MlPermissionTreeBean;
import com.rmkj.microcap.common.modules.sys.dao.IMl3PermissionDao;
import com.rmkj.microcap.common.modules.sys.bean.Ml3PermissionBean;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* Created by Administrator on 2016-11-15.
*/
@Service
@Transactional
public class Ml3PermissionService implements IBaseService<Ml3PermissionBean> {
    @Autowired
    private IMl3PermissionDao ml3PermissionDao;

    @Override
    public Ml3PermissionBean get(String id){
        return ml3PermissionDao.get(id);
    }
    @Override
    public int update(Ml3PermissionBean ml3PermissionBean){
        ml3PermissionBean.preUpdate();
        ml3PermissionDao.update(ml3PermissionBean);
        UserUtils.clearCache();
        return 1;
    }
    @Override
    public int delete(String id)
    {
        // 删除菜单与角色的关系
        List<String> menuIds = this.getDelMenus(id);
        if (menuIds.size() > 0) {
            ml3PermissionDao.deleteRoleMenu(menuIds);
            ml3PermissionDao.deleteMenuList(menuIds);
        }
        UserUtils.clearCache();
        return 1;
    }
    private List<String> getDelMenus(String menuId) {
        List<MlPermissionTreeBean> menuTreeBeanList = ml3PermissionDao.findAllMenu();
        List<String> menuIds = new ArrayList<>();
        menuIds.add(menuId);
        findMenuByMenuId(menuId, menuTreeBeanList, menuIds);
        return menuIds;
    }
    private void findMenuByMenuId(String menuId,
                                  List<MlPermissionTreeBean> menuTreeBeanList, List<String> menuIds) {
        for (MlPermissionTreeBean sysMenuBean : menuTreeBeanList) {
            if (menuId.equals(sysMenuBean.getParentId())) {
                findMenuByMenuId(sysMenuBean.getId(), menuTreeBeanList, menuIds);
                menuIds.add(sysMenuBean.getId());
            }
        }
    }
    @Override
    public int insert(Ml3PermissionBean ml3PermissionBean){
        ml3PermissionBean.preInsert();
        if ("0".equals(ml3PermissionBean.getParentId())) {
            ml3PermissionBean.setParentIds("0");
        } else {
            Ml3PermissionBean parent = ml3PermissionDao.get(ml3PermissionBean.getParentId());
            ml3PermissionBean.setParentIds(parent.getParentIds() + "_"
                    + parent.getId());
        }
        ml3PermissionDao.insert(ml3PermissionBean);
        UserUtils.clearCache();
        return 1;
    }
    @Override
    public List<Ml3PermissionBean> queryList(Ml3PermissionBean ml3PermissionBean){
        return ml3PermissionDao.queryList(ml3PermissionBean);
    }
    public List<MlPermissionTreeBean> findMenuTree() {
        List<MlPermissionTreeBean> allMenus = ml3PermissionDao.findAllMenu();
        List<MlPermissionTreeBean> treeBeanList = new ArrayList<>();
        Map<String, MlPermissionTreeBean> map = new HashMap<>();
        MlPermissionTreeBean sysMenuBean = new MlPermissionTreeBean();
        sysMenuBean.setId("0");
        sysMenuBean.setText("菜单列表");
        map.put(sysMenuBean.getId(), sysMenuBean);
        for (MlPermissionTreeBean treeBean : allMenus) {
            map.put(treeBean.getId(), treeBean);
            if (treeBean.getParentId().equals("0")) {// 说明是二级目录
                treeBeanList.add(treeBean);
            } else {
                String parentId = treeBean.getParentId();
                MlPermissionTreeBean bean2 = map.get(parentId);
                bean2.addChildren(treeBean);
                // map.get(treeBean.getParentId()).addChildren(treeBean);
            }
        }
        return treeBeanList;
    }
    public void updateHref(Ml3PermissionBean ml3PermissionBean){
        ml3PermissionDao.updateHref(ml3PermissionBean);
    }
    public void updateHref1(Ml3PermissionBean ml3PermissionBean){
        ml3PermissionDao.updateHref1(ml3PermissionBean);
    }
}
