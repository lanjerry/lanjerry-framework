package org.lanjerry.admin.dto.sys;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统消息新增参数
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统消息新增参数")
public class SysNotificationSaveDTO extends BaseEntity {

    @NotNull(message = "用户编号不能为空")
    @ApiModelProperty(value = "用户编号", position = 10)
    private Integer userId;

    @Size(max = 500, message = "消息内容输入长度不能超过500个字符")
    @NotBlank(message = "消息内容不能为空")
    @ApiModelProperty(value = "消息内容", position = 20)
    private String content;
}
