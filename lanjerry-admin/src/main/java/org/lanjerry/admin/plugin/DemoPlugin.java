package org.lanjerry.admin.plugin;

import java.lang.reflect.InvocationTargetException;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.lanjerry.common.core.exception.ApiException;

/**
 * <p>
 * 拦截演示模式下的update操作
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-27
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class})})
public class DemoPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        if (((MappedStatement) invocation.getArgs()[0]).getId().equals("org.lanjerry.admin.mapper.sys.SysUserMapper.updateLoginInfo")) {
            return invocation.proceed();
        }
        throw ApiException.systemError("演示模式，不允许操作");
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }
}