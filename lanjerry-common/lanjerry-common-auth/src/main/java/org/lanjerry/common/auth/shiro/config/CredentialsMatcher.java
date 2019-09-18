package org.lanjerry.common.auth.shiro.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.lanjerry.common.auth.shiro.jwt.JwtToken;
import org.lanjerry.common.core.util.JwtUtil;
import org.lanjerry.common.core.util.Md5Util;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 自定义密码匹配器
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-09
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JwtToken jwtToken = (JwtToken) token;
        Object accountCredentials = getCredentials(info);
        if (StrUtil.isNotBlank(jwtToken.getPassword())) {
            Object tokenCredentials = Md5Util.encode(jwtToken.getPassword(), jwtToken.getId().toString());
            if (!accountCredentials.equals(tokenCredentials)) {
                throw new DisabledAccountException("密码不正确");
            }
        } else {
            if (!JwtUtil.verify(jwtToken.getToken(), accountCredentials.toString())) {
                throw new DisabledAccountException("身份认证已失效，请重新登录");
            }
        }
        return true;
    }
}