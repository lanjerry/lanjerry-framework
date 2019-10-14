package org.lanjerry.common.core.exception;

import java.io.Serializable;

import org.lanjerry.common.core.enums.ApiResultCodeEnum;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 请求异常类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应状态回执码
     */
    protected long code;

    /**
     * 响应回执消息
     */
    protected String msg;

    /**
     * 异常信息
     */
    private Exception ex;

    public static ApiException argError(String msg) {
        return ApiException.builder()
                .code(ApiResultCodeEnum.ARG_ERROR.val)
                .msg(msg)
                .build();
    }

    public static ApiException systemError(String msg) {
        return ApiException.builder()
                .code(ApiResultCodeEnum.SYSTEM_ERROR.val)
                .msg(msg)
                .build();
    }
}
