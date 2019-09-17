package org.lanjerry.common.core.entity.sys;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_log")
public class SysLog extends BaseEntity {

    /**
     * 日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 是否异步请求 0.否 1.是
     */
    private Boolean ajaxFlag;

    /**
     * 请求uri
     */
    private String requestUri;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * http方法
     */
    private String httpMethod;

    /**
     * 调用类的方法
     */
    private String classMethod;

    /**
     * 动作名称
     */
    private String actionName;

    /**
     * 执行时间（秒）
     */
    private BigDecimal executionTime;

    /**
     * 异常信息
     */
    private String exceptionMsg;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
