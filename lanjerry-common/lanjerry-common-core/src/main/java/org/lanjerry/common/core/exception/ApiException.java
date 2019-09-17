package org.lanjerry.common.core.exception;

import java.io.Serializable;

import org.lanjerry.common.core.enums.REnum;

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

    public synchronized static ApiException argError(String msg) {
        return ApiException.builder()
                .code(REnum.ARG_ERROR.val)
                .msg(msg)
                .build();
    }

    public synchronized static ApiException systemError(String msg) {
        return ApiException.builder()
                .code(REnum.SYSTEM_ERROR.val)
                .msg(msg)
                .build();
    }
}
