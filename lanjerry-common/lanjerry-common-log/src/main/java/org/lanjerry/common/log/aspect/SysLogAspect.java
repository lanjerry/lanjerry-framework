package org.lanjerry.common.log.aspect;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.lanjerry.common.core.constant.CommonConsts;
import org.lanjerry.common.core.constant.JsonConsts;
import org.lanjerry.common.core.exception.ApiException;
import org.lanjerry.common.core.util.BigDecimalUtil;
import org.lanjerry.common.log.annotation.SysLog;
import org.lanjerry.common.log.bean.SysLogSaveDTO;
import org.lanjerry.common.log.event.SysLogEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * 系统日志切面，使用spring event异步处理
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-04
 */
@Log4j2
@Aspect
//@Component
@AllArgsConstructor
public class SysLogAspect {

    private final ApplicationEventPublisher publisher;

    @Pointcut("@annotation(org.lanjerry.common.log.annotation.SysLog)")
    public void log() {
    }

    @SneakyThrows
    @Around("log()")
    public Object handleLog(ProceedingJoinPoint joinPoint) {
        // 执行目标方法，并且计算运行时间
        Long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long endTime = System.currentTimeMillis();

        // 异步保存系统日志
        SysLogSaveDTO log = setLog(joinPoint);
        log.setExecutionTime(BigDecimalUtil.div(new BigDecimal(endTime - startTime), new BigDecimal("1000"), 2));
        publisher.publishEvent(new SysLogEvent(log));

        // 返回结果
        return result;
    }

    @AfterThrowing(throwing = "ex", pointcut = "log()")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        // 异步保存系统日志
        SysLogSaveDTO log = setLog(joinPoint);
        log.setExceptionMsg(ex instanceof ApiException ? ((ApiException) ex).getMsg() : ex.getMessage());
        publisher.publishEvent(new SysLogEvent(log));
    }

    private SysLogSaveDTO setLog(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLogSaveDTO log = new SysLogSaveDTO();
        log.setUserId(1);

        // 获取IP地址
        String ipAddress = ServletUtil.getClientIP(request);
        log.setIpAddress(CommonConsts.LOCAL_HOST_IPs.contains(ipAddress) ? CommonConsts.LOCAL_HOST_IP : ipAddress);

        log.setAjaxFlag(ajax(request));
        log.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        log.setHttpMethod(request.getMethod());
        log.setClassMethod(joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName() + "()");
        log.setActionName(getMethodSysLogsAnnotationValue(joinPoint));

        // 过滤请求参数
        String params = this.paramFilter(JSONUtil.toJsonStr(joinPoint.getArgs()));
        log.setRequestParams(params.length() > 500 ? params.substring(0, 500) : params);
        return log;
    }

    /**
     * 请求参数过滤
     *
     * @author lanjerry
     * @since 2019/9/4 14:44
     */
    private String paramFilter(String jsonParams) {
        if (StrUtil.isNotBlank(jsonParams)) {
            JSONArray result = JSONUtil.createArray();
            JSONArray params = JSONUtil.parseArray(jsonParams);
            params.forEach(param -> {
                JSONObject jsonParam = JSONUtil.parseObj(param);
                for (String key : JsonConsts.REMOVE_KEYS) {
                    jsonParam.remove(key);
                }
                result.add(jsonParam);
            });
            return JSONUtil.toJsonStr(result);
        }
        return "";
    }

    /**
     * 判断请求是否是Ajax
     *
     * @author lanjerry
     * @since 2019/9/4 10:59
     */
    private static boolean ajax(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        return accept != null && accept.contains("application/json") || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }

    /**
     * 获取注解上面的值
     *
     * @author lanjerry
     * @since 2019/9/4 11:06
     */
    private String getMethodSysLogsAnnotationValue(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(SysLog.class)) {
            //获取方法上注解中表明的权限
            SysLog sysLog = method.getAnnotation(SysLog.class);
            return sysLog.value();
        }
        return "未知";
    }
}