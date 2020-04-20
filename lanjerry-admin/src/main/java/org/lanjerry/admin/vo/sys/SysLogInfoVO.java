package org.lanjerry.admin.vo.sys;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysLogStatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志信息
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统日志信息")
public class SysLogInfoVO extends BaseEntity {

    /**
     * 动作名称
     */
    @ApiModelProperty(value = "动作名称", example = "新增系统用户", position = 10)
    private String actionName;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", example = "admin", position = 20)
    private String userAccount;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址", example = "127.0.0.1", position = 30)
    private String ipAddress;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", example = "POST", position = 40)
    private String requestMethod;

    /**
     * 是否异步请求 0.否 1.是
     */
    @ApiModelProperty(value = "是否异步请求 0.否 1.是", example = "true", position = 50)
    private Boolean ajaxFlag;

    /**
     * 请求uri
     */
    @ApiModelProperty(value = "请求地址", example = "/sys/user", position = 60)
    private String requestUri;

    /**
     * 操作方法
     */
    @ApiModelProperty(value = "操作方法", example = "org.lanjerry.admin.controller.sys.UserController.saveUser()", position = 70)
    private String classMethod;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数", example = "[{}]", position = 80)
    private String requestParams;

    /**
     * 状态 1.成功 2.失败
     */
    @ApiModelProperty(value = "状态 1.成功 2.失败", example = "{\"value\": 1,\"text\": \"成功\",\"name\": \"SUCCESS\"}", position = 90)
    private SysLogStatusEnum status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "操作时间", example = "2018-11-22 15:57:53", position = 100)
    private LocalDateTime createdTime;

    /**
     * 执行时间（秒）
     */
    @ApiModelProperty(value = "执行时间（秒）", example = "11.09", position = 110)
    private BigDecimal executionTime;

    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息", example = "权限：系统管理存在子菜单，不允许删除", position = 120)
    private String exceptionMsg;
}