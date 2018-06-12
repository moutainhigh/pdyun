package com.rmkj.microcap.common.modules.sys.service;

import com.rmkj.microcap.common.modules.shiro.session.SessionDAO;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.dao.ISysUserDao;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.Digests;
import com.rmkj.microcap.common.utils.Encodes;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.syslog.dao.ISysLogDao;
import com.rmkj.microcap.modules.syslog.entity.SysLogBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Autowired
    private ISysUserDao userDao;
    @Autowired
    private SessionDAO sessionDao;
    @Autowired
    private ISysLogDao sysLogDao;

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    public SessionDAO getSessionDao() {
        return sessionDao;
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return
     */
    public SysUserBean getUserByLoginName(String loginName) {
        return UserUtils.getByLoginName(loginName, true);
    }

    public SysUserBean getUserByLoginName(String loginName, boolean useCash) {
        return UserUtils.getByLoginName(loginName, useCash);
    }

    @Transactional(readOnly = false)
    public void updateUserLoginInfo(SysUserBean user) {
        // 更新本次登录信息
        user.setLoginIp(UserUtils.getSession().getHost());
        user.setLoginDate(new Date());
        SysLogBean sysLogBean = new SysLogBean();
        sysLogBean.setId(Utils.uuid());
        sysLogBean.setmId(user.getId());
        sysLogBean.setlContent(user.getName()+"登录成功\t"+"IP:"+user.getLoginIp());
        sysLogBean.setlType(0);
        sysLogBean.setlCreateTime(new Date());
        sysLogDao.insert(sysLogBean);
        userDao.updateLoginInfo(UserUtils.getSession().getHost(), new Date(), user.getId());
    }

}
