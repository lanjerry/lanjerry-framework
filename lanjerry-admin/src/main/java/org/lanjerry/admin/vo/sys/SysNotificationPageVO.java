package org.lanjerry.admin.vo.sys;

import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统消息查询信息
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统消息查询信息")
public class SysNotificationPageVO extends BaseEntity {

    @ApiModelProperty(value = "编号", position = 10)
    private Integer id;

    @ApiModelProperty(value = "用户账号", position = 20)
    private String userAccount;

    @ApiModelProperty(value = "消息内容", position = 30)
    private String content;

    @ApiModelProperty(value = "是否已读 0.否 1.是", position = 40)
    private Boolean readFlag;

    @ApiModelProperty(value = "创建时间", position = 50)
    private LocalDateTime createdTime;
}
