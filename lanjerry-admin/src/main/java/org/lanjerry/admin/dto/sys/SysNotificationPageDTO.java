package org.lanjerry.admin.dto.sys;

import org.lanjerry.common.core.bean.SplitPage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统消息查询参数
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统消息查询参数")
public class SysNotificationPageDTO extends SplitPage {

    @ApiModelProperty(value = "创建时间始", position = 10)
    private String createdTimeStart;

    @ApiModelProperty(value = "创建时间止", position = 20)
    private String createdTimeEnd;
}
