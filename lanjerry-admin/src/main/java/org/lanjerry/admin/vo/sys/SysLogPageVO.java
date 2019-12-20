package org.lanjerry.admin.vo.sys;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysLogStatusEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志分页查询VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLogPageVO extends BaseEntity {

    /**
     * 日志编号
     */
    @ApiModelProperty(value = "日志编号", example = "1", position = 10)
    private Integer id;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", example = "1", position = 20)
    private String userAccount;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址", example = "127.0.0.1", position = 30)
    private String ipAddress;

    /**
     * 请求uri
     */
    @ApiModelProperty(value = "请求uri", example = "/sys/user", position = 50)
    private String requestUri;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", example = "POST", position = 70)
    private String requestMethod;

    /**
     * 操作方法
     */
    @ApiModelProperty(value = "操作方法", example = "org.lanjerry.admin.controller.sys.UserController.saveUser()", position = 80)
    private String classMethod;

    /**
     * 动作名称
     */
    @ApiModelProperty(value = "动作名称", example = "新增系统用户", position = 90)
    private String actionName;

    /**
     * 状态 1.成功 2.失败
     */
    private SysLogStatusEnum status;

    /**
     * 执行时间（秒）
     */
    @ApiModelProperty(value = "执行时间（秒）", example = "11.09", position = 100)
    private BigDecimal executionTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-22T15:57:53", position = 120)
    private LocalDateTime createdTime;
}
