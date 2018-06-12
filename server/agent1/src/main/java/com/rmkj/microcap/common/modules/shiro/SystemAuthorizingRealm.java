package com.rmkj.microcap.common.modules.shiro;

import com.rmkj.microcap.common.handler.SpringContextHolder;
import com.rmkj.microcap.common.modules.shiro.constants.Constants;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean;
import com.rmkj.microcap.common.modules.sys.bean.Ml3PermissionBean;
import com.rmkj.microcap.common.modules.sys.bean.Ml3RoleBean;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.Encodes;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统安全认证实现类
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

    private SystemService systemService;

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws ShiroException{
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 校验用户名密码
        Ml3AgentBean user = getSystemService().getUserByLoginName(token.getUsername(), false);
        if (user != null) {
            //验证用户状态是否可以登陆
            validateLoginStatusAuth(user);
            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
            return new SimpleAuthenticationInfo(new Principal(user),
                    user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) getAvailablePrincipal(principals);
        // 获取当前已登录的用户
        Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
        if (sessions.size() > 0) {
            Subject s = UserUtils.getSubject();//获得当前正在登陆的连接
            String curId = principal.getId();//当前登录者的id
            //如果当前在线中包含该用户，进行处理
            for (Session session : sessions) {
                //登录者id
                Object objId = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                //如果当前用户已登录
                if (objId != null && curId.equals(objId.toString())) {
                    // 如果是登录进来的，则踢出已在线用户
                    if (s.isAuthenticated()) {
                        getSystemService().getSessionDao().delete(session);
                    } else {// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
                        s.logout();
                        throw new AuthenticationException(Constants.LOGIN_OTHER);
                    }
                    break;
                }
            }
        }
        Ml3AgentBean user = getSystemService().getUserByLoginName(principal.getLoginName());
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Ml3PermissionBean> list = UserUtils.getMenuList();
            for (Ml3PermissionBean menu : list) {
                if (StringUtils.isNotBlank(menu.getPermission())) {
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(), ",")) {
                        info.addStringPermission(permission);
                    }
                }
            }
            // 添加用户权限
            info.addStringPermission("user");
            // 添加用户角色信息
            for (Ml3RoleBean role : user.getRoleList()) {
                info.addRole(role.getEnname());
            }
            // 更新登录IP和时间
            getSystemService().updateUserLoginInfo(user);
            return info;
        } else {
            return null;
        }
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        authorizationValidate(permission);
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

    /**
     * 授权验证方法
     *
     * @param permission
     */
    private void authorizationValidate(Permission permission) {
        // 模块授权预留接口
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
        matcher.setHashIterations(SystemService.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    /**
     * 获取系统业务对象
     */
    public SystemService getSystemService() {
        if (systemService == null) {
            systemService = SpringContextHolder.getBean(SystemService.class);
        }
        return systemService;
    }


    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String loginName; // 登录名
        private String name; // 姓名
        public Principal(Ml3AgentBean user) {
            this.id = user.getId();
            this.loginName = user.getLoginName();
            this.name = user.getRealName();
        }
//
        public String getId() {
            return id;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return id;
        }

    }

    public void validateLoginStatusAuth(Ml3AgentBean user) throws ShiroException{
        if(!"0".equals(user.getCenterStatus())){
            throw  new AuthenticationException("该市场管理部已停用");
        }
        if(!"0".equals(user.getUnitsStatus()) && null != user.getUnitsStatus()){
            throw  new AuthenticationException("该会员单位已停用");
        }
        if(0 != user.getStatus() && null != user.getAgentInviteCode()){
            throw  new AuthenticationException("该代理商已停用");
        }
        if(0 != user.getStatus()){
            throw  new AuthenticationException("该账户已停用");
        }
    }
}
