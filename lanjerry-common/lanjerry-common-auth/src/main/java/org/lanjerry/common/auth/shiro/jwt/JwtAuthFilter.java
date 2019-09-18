package org.lanjerry.common.auth.shiro.jwt;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.lanjerry.common.core.bean.R;
import org.lanjerry.common.core.enums.REnum;
import org.lanjerry.common.core.exception.ApiException;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * jwt鉴权
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-09
 */
@Log4j2
public class JwtAuthFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        if (!isLoginAttempt(servletRequest, servletResponse)) {
            writerResponse(response, REnum.NOT_SING_IN.val, "无身份认证权限标示");
            return false;
        }
        try {
            return executeLogin(servletRequest, servletResponse);
        } catch (ApiException e) {
            writerResponse(response, e.getCode(), e.getMsg());
            return false;
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        JwtToken token = JwtToken.builder().token(this.getAuthorization(request)).build();
        try {
            subject.login(token);
        } catch (DisabledAccountException e) {
            throw ApiException.builder().code(REnum.NOT_SING_IN.val).msg(e.getMessage()).build();
        } catch (Exception e) {
            throw ApiException.systemError(e.getMessage());
        }
        return true;
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return StrUtil.isNotBlank(this.getAuthorization(request));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        return false;
    }

    /**
     * 获取token
     */
    private String getAuthorization(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getHeader("Authorization");
    }

    /**
     * 输出
     */
    private void writerResponse(HttpServletResponse response, long code, String msg) {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        try {
            response.getWriter().write(JSONUtil.toJsonStr(new R().setCode(code).setMsg(msg)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
