package org.lanjerry.admin.vo.sys;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;

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
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", example = "1", position = 20)
    private Integer userId;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址", example = "127.0.0.1", position = 30)
    private String ipAddress;

    /**
     * 是否异步请求 0.否 1.是
     */
    @ApiModelProperty(value = "是否异步请求", example = "true", position = 40)
    private Boolean ajaxFlag;

    /**
     * 请求uri
     */
    @ApiModelProperty(value = "请求uri", example = "/sys/user", position = 50)
    private String requestUri;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数", example = "[{\"roles\":\"1,2,3\",\"name\":\"管理员\",\"account\":\"admin\"}]", position = 60)
    private String requestParams;

    /**
     * http方法
     */
    @ApiModelProperty(value = "http方法", example = "POST", position = 70)
    private String httpMethod;

    /**
     * 调用类的方法
     */
    @ApiModelProperty(value = "调用类的方法", example = "org.lanjerry.admin.controller.sys.UserController.saveUser()", position = 80)
    private String classMethod;

    /**
     * 动作名称
     */
    @ApiModelProperty(value = "动作名称", example = "新增系统用户", position = 90)
    private String actionName;

    /**
     * 执行时间（秒）
     */
    @ApiModelProperty(value = "执行时间（秒）", example = "11.09", position = 100)
    private BigDecimal executionTime;

    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息", example = "账号：admin已存在", position = 110)
    private String exceptionMsg;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-22T15:57:53", position = 120)
    private LocalDateTime createdTime;
}
