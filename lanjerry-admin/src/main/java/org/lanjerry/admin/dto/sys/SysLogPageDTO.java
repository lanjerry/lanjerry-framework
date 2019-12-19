package org.lanjerry.admin.dto.sys;

import org.lanjerry.common.core.bean.SplitPage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志分页查询DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLogPageDTO extends SplitPage {

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", example = "1", position = 10)
    private Integer userId;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址", example = "127.0.0.1", position = 20)
    private String ipAddress;

    /**
     * 请求uri
     */
    @ApiModelProperty(value = "请求uri", example = "/sys/user", position = 30)
    private String requestUri;

    /**
     * 动作名称
     */
    @ApiModelProperty(value = "动作名称", example = "新增系统用户", position = 40)
    private String actionName;
}
