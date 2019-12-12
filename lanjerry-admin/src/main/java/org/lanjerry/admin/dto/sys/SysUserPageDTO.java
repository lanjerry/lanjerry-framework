package org.lanjerry.admin.dto.sys;

import org.lanjerry.common.core.bean.SplitPage;
import org.lanjerry.common.core.enums.UserStatusEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户分页查询DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPageDTO extends SplitPage {

    /**
     * 系统用户id
     */
    @ApiModelProperty(value = "id", example = "1", position = 10)
    private Integer id;

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号", example = "admin", position = 10)
    private String account;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "管理员", position = 20)
    private String name;

    /**
     * 状态 1.正常 2.禁用
     */
    @ApiModelProperty(value = "状态 1.正常（NORMAL） 2.禁用（LOCKING）", example = "NORMAL", position = 30)
    private UserStatusEnum status;
}
