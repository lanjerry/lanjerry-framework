package org.lanjerry.admin.vo.sys;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysNotificationTypeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 通知消息信息
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-21
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ApiModel("通知消息信息")
public class SysNotificationVO extends BaseEntity {

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型", position = 10)
    private SysNotificationTypeEnum type;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容", position = 20)
    private String message;
}
