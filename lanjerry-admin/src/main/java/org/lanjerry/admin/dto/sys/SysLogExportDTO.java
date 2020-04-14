package org.lanjerry.admin.dto.sys;

import java.util.List;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志导出查询DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLogExportDTO extends BaseEntity {

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", example = "1", position = 10)
    private String userAccount;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "IP地址", example = "127.0.0.1", position = 20)
    private String ipAddress;

    /**
     * 请求uri
     */
    @ApiModelProperty(value = "请求地址", example = "/sys/user", position = 30)
    private String requestUri;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", example = "POST", position = 40)
    private String requestMethod;

    /**
     * 动作名称
     */
    @ApiModelProperty(value = "动作名称", example = "新增系统用户", position = 50)
    private String actionName;

    /**
     * 状态 1.成功 2.失败
     */
    @ApiModelProperty(value = "状态 1.成功 2.失败", example = "1", position = 60)
    private Integer status;

    /**
     * 创建时间始
     */
    @ApiModelProperty(value = "创建时间始", example = "2018-11-23", position = 70)
    private String createdTimeStart;

    /**
     * 创建时间止
     */
    @ApiModelProperty(value = "创建时间止", example = "2018-11-23", position = 80)
    private String createdTimeEnd;

    /**
     * 日志编号数组
     */
    @ApiModelProperty(value = "日志编号数组", example = "[1,2,3]", position = 90)
    private List<Integer> ids;
}
