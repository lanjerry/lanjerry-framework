package org.lanjerry.common.core.exception;

import java.io.Serializable;

import org.lanjerry.common.core.enums.global.ApiResultCodeEnum;

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

    public static ApiException argError(String msg) {
        return restException(ApiResultCodeEnum.ARG_ERROR.value, msg);
    }

    public static ApiException systemError(String msg) {
        return restException(ApiResultCodeEnum.SYSTEM_ERROR.value, msg);
    }

    private static ApiException restException(long code, String msg) {
        return ApiException.builder()
                .code(code)
                .msg(msg)
                .build();
    }
}
