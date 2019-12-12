package org.lanjerry.common.auth.shiro.jwt;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.lanjerry.common.auth.shiro.service.ShiroService;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.enums.ApiResultCodeEnum;
import org.lanjerry.common.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * JWT验证Realm
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-09
 */
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("reallm 鉴权");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 设置角色
        authorizationInfo.setRoles(shiroService.getRoles());
        // 设置权限
        authorizationInfo.setStringPermissions(shiroService.getPermissions());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authcToken;
        String account = jwtToken.getAccount() != null ? jwtToken.getAccount() : JwtUtil.getAttribute(jwtToken.getToken(), "account");
        Object user = shiroService.getLoginByAccount(account);
        if (user == null) {
            throw new DisabledAccountException(ApiResultCodeEnum.NOT_SING_IN.desc);
        }

        if (user instanceof SysUser) {
            SysUser sysUser = (SysUser) user;
            jwtToken.setId(sysUser.getId());
            if (jwtToken.getAccount() == null) {
                jwtToken.setAccount(sysUser.getAccount());
            }
            if (jwtToken.getName() == null) {
                jwtToken.setName(sysUser.getName());
            }
            if (jwtToken.getToken() == null) {
                jwtToken.setToken(JwtUtil.sign(sysUser.getId(), sysUser.getAccount(), sysUser.getPassword()));
            }
            return new SimpleAuthenticationInfo(jwtToken, sysUser.getPassword(), getName());
        }
        return null;
    }
}
