package org.lanjerry.admin.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.lanjerry.common.core.bean.ApiResult;
import org.lanjerry.common.core.enums.global.ApiResultCodeEnum;
import org.lanjerry.common.core.exception.ApiException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获请求异常
     *
     * @author lanjerry
     * @since 2019/9/3 16:43
     */
    @ExceptionHandler(ApiException.class)
    public ApiResult handleApiException(HttpServletRequest request, ApiException ex) {
        log.error("ErrorUrl：" + request.getRequestURI());
        log.error(errInfo(ex));
        return new ApiResult().setCode(ex.getCode()).setMsg(ex.getMsg());
    }

    /**
     * 捕获参数验证异常
     *
     * @author lanjerry
     * @since 2019/9/3 16:43
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handleParamValidException(Exception ex) {
        log.error(errInfo(ex));
        String msg = "参数验证失败";
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgument = (MethodArgumentNotValidException) ex;
            BindingResult result = methodArgument.getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                msg = errors.get(0).getDefaultMessage();
            }
        }
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;
            List<String> msgList = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : constraintViolationException.getConstraintViolations()) {
                msgList.add(constraintViolation.getMessage());
            }
            msg = msgList.get(0);
        }
        return ApiResult.argError(msg);
    }

    /**
     * 捕获shiro权限不足的异常
     *
     * @author lanjerry
     * @since 2019/9/16 16:49
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ApiResult handleUnauthorizedException(UnauthorizedException ex) {
        log.error(errInfo(ex));
        return new ApiResult().setCode(ApiResultCodeEnum.UN_AUTHORIZED.value).setMsg(ApiResultCodeEnum.UN_AUTHORIZED.text);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ApiResult handleUnauthenticatedException(UnauthenticatedException ex) {
        // 应用场景：shiro放行了该方法，但是方法却使用了@RequiresPermissions
        log.error(errInfo(ex));
        return new ApiResult().setCode(ApiResultCodeEnum.UN_AUTHORIZED.value).setMsg(ApiResultCodeEnum.UN_AUTHORIZED.text);
    }

    /**
     * 捕获其他所有异常
     *
     * @author lanjerry
     * @since 2019/9/3 16:43
     */
    @ExceptionHandler(Exception.class)
    public ApiResult handleGlobalException(HttpServletRequest request, Exception ex) {
        log.error("ErrorUrl：" + request.getRequestURI());
        log.error(errInfo(ex));
        return ApiResult.systemError(String.format("url：%s，ex：%s", request.getRequestURI(), ex.getMessage()));
    }

    /**
     * 异常堆栈信息
     *
     */
    public static String errInfo(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException ex) {
                    log.error("Exception：", ex);
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
