package org.lanjerry.common.core.bean;

import org.lanjerry.common.core.enums.global.ApiResultCodeEnum;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 返回结果
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> extends BaseEntity {

    /**
     * 响应状态回执码
     */
    @ApiModelProperty(value = "响应状态回执码", example = "200", position = 10)
    protected long code;

    /**
     * 响应回执消息
     */
    @ApiModelProperty(value = "响应回执消息", example = "成功", position = 20)
    protected String msg;

    /**
     * 数据体
     */
    @ApiModelProperty(value = "数据体", position = 30)
    protected T data;

    /**
     * 响应时间戳
     */
    @ApiModelProperty(value = "响应时间戳", example = "1577256960660", position = 40)
    protected final long timestamps = System.currentTimeMillis();

    public static ApiResult success() {
        return restResult(ApiResultCodeEnum.SUCCESS, null);
    }

    public static <T> ApiResult<T> success(T data) {
        return restResult(ApiResultCodeEnum.SUCCESS, data);
    }

    public static ApiResult systemError(String message) {
        return restResult(ApiResultCodeEnum.SYSTEM_ERROR.value, message, null);
    }

    public static ApiResult argError(String message) {
        return restResult(ApiResultCodeEnum.ARG_ERROR.value, message, null);
    }

    public static <T> ApiResult<T> restResult(ApiResultCodeEnum codeEnum, T data) {
        return restResult(codeEnum.value, codeEnum.text, data);
    }

    private static <T> ApiResult<T> restResult(long code, String msg, T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }
}