package com.rmkj.microcap.common.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.SysMenuBean;
import com.rmkj.microcap.common.modules.sys.bean.SysMenuTreeBean;
import com.rmkj.microcap.common.modules.sys.dao.ISysMenuDao;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbowen on 2015-9-8.
 */
@Service
@Transactional
public class SysMenuService implements IBaseService<SysMenuBean> {
    @Autowired
    private ISysMenuDao menuDao;

    @Override
    public SysMenuBean get(String id) {
        return menuDao.get(id);
    }

    @Override
    public int delete(String id) {
        // 删除菜单与角色的关系
        List<String> menuIds = this.getDelMenus(id);
        if (menuIds.size() > 0) {
            menuDao.deleteRoleMenu(menuIds);
            menuDao.deleteMenuList(menuIds);
        }
        UserUtils.clearCache();
        return 1;
    }

    private List<String> getDelMenus(String menuId) {
        List<SysMenuTreeBean> menuTreeBeanList = menuDao.findAllMenu();
        List<String> menuIds = new ArrayList<>();
        menuIds.add(menuId);
        findMenuByMenuId(menuId, menuTreeBeanList, menuIds);
        return menuIds;
    }

    private void findMenuByMenuId(String menuId,
                                  List<SysMenuTreeBean> menuTreeBeanList, List<String> menuIds) {
        for (SysMenuTreeBean sysMenuBean : menuTreeBeanList) {
            if (menuId.equals(sysMenuBean.getParentId())) {
                findMenuByMenuId(sysMenuBean.getId(), menuTreeBeanList, menuIds);
                menuIds.add(sysMenuBean.getId());
            }
        }
    }

    @Override
    public int insert(SysMenuBean sysMenuBean) {
        if ("0".equals(sysMenuBean.getParentId())) {
            sysMenuBean.setParentIds("0");
        } else {
            SysMenuBean parent = menuDao.get(sysMenuBean.getParentId());
            sysMenuBean.setParentIds(parent.getParentIds() + "_"
                    + parent.getId());
        }
        sysMenuBean.preInsert();
        menuDao.insert(sysMenuBean);
        UserUtils.clearCache();
        return 1;
    }

    @Override
    public int update(SysMenuBean sysMenuBean) {
        sysMenuBean.preUpdate();
        menuDao.update(sysMenuBean);
        UserUtils.clearCache();
        return 1;
    }

    @Override
    public List<SysMenuBean> queryList(SysMenuBean sysMenuBean) {
        return menuDao.queryList(sysMenuBean);
    }

    public List<SysMenuTreeBean> findMenuTree() {
        List<SysMenuTreeBean> allMenus = menuDao.findAllMenu();
        System.out.println("---------------菜单个数"+allMenus.size());
        List<SysMenuTreeBean> treeBeanList = new ArrayList<>();
        Map<String, SysMenuTreeBean> map = new HashMap<>();
        SysMenuTreeBean sysMenuBean = new SysMenuTreeBean();
        sysMenuBean.setId("0");
        sysMenuBean.setText("菜单列表");
        map.put(sysMenuBean.getId(), sysMenuBean);
        for (SysMenuTreeBean treeBean : allMenus) {
            map.put(treeBean.getId(), treeBean);
            if (treeBean.getParentId().equals("0")) {// 说明是二级目录
                System.out.println("---------------二级目录"+treeBean.getParentId());
                treeBeanList.add(treeBean);
            } else {
                String parentId = treeBean.getParentId();
                System.out.println("父id---------------"+parentId);
                SysMenuTreeBean bean2 = map.get(parentId);
                System.out.println("---------------"+bean2.getText());
                bean2.addChildren(treeBean);
                // map.get(treeBean.getParentId()).addChildren(treeBean);
            }
        }
        return treeBeanList;
    }
}
