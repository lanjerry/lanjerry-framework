package org.lanjerry.admin.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.lanjerry.common.core.bean.R;
import org.lanjerry.common.core.enums.REnum;
import org.lanjerry.common.core.exception.ApiException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

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
    public R handleApiException(HttpServletRequest request, ApiException ex) {
        log.error("BusinessExceptionHandler:" + ex.getMessage());
        log.error("ErrorUrl：" + request.getRequestURI());
        log.error("Msg：" + ex.getMsg());
        return new R().setCode(ex.getCode()).setMsg(ex.getMsg());
    }

    /**
     * 捕获参数验证异常
     *
     * @author lanjerry
     * @since 2019/9/3 16:43
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleParamValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String msg = "参数验证失败";
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            msg = errors.get(0).getDefaultMessage();
        }
        return R.argError(msg);
    }

    /**
     * 捕获shiro权限不足的异常
     *
     * @author lanjerry
     * @since 2019/9/16 16:49
     */
    @ExceptionHandler(UnauthorizedException.class)
    public R handleUnauthorizedException(UnauthorizedException ex) {
        return new R().setCode(REnum.UN_AUTHORIZED.val).setMsg(REnum.UN_AUTHORIZED.desc);
    }

    /**
     * 捕获其他所有异常
     *
     * @author lanjerry
     * @since 2019/9/3 16:43
     */
    @ExceptionHandler(Exception.class)
    public R handleGlobalException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.error("BusinessExceptionHandler:" + ex.getMessage());
        log.error("ErrorUrl：" + request.getRequestURI());
        log.error("Msg：" + ex.getMessage());
        return R.systemError(String.format("url：%s，ex：%s", request.getRequestURI(), ex.getMessage()));
    }
}
