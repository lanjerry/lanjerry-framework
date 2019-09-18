package org.lanjerry.common.auth.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * JwtToken
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-10
 */
@Data
@Builder
public class JwtToken implements AuthenticationToken {

    /**
     * token
     */
    private String token;

    /**
     * id
     */
    private Integer id;

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
