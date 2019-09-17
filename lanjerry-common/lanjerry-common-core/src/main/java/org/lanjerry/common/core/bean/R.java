package org.lanjerry.common.core.bean;

import org.lanjerry.common.core.enums.REnum;

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
public class R<T> extends BaseEntity {

    /**
     * 响应状态回执码
     */
    protected long code;

    /**
     * 响应回执消息
     */
    protected String msg;

    /**
     * 数据体
     */
    protected T data;

    /**
     * 响应时间戳
     */
    protected final long timestamps = System.currentTimeMillis();

    public synchronized static R ok() {
        return restResult(REnum.SUCCESS, null);
    }

    public synchronized static <T> R<T> ok(T data) {
        return restResult(REnum.SUCCESS, data);
    }

    public synchronized static R systemError(String message) {
        return restResult(REnum.SYSTEM_ERROR.val, message, null);
    }

    public synchronized static R argError(String message) {
        return restResult(REnum.ARG_ERROR.val, message, null);
    }

    public static <T> R<T> restResult(REnum rEnum, T data) {
        return restResult(rEnum.val, rEnum.desc, data);
    }

    private static <T> R<T> restResult(long code, String msg, T data) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }
}