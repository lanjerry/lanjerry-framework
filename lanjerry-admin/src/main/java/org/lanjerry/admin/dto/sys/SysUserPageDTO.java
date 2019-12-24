package org.lanjerry.admin.dto.sys;

import org.lanjerry.common.core.bean.SplitPage;

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
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", example = "1", position = 10)
    private Integer id;

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号", example = "admin", position = 20)
    private String account;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "管理员", position = 30)
    private String name;

    /**
     * 状态 1.启用 2.停用
     */
    @ApiModelProperty(value = "状态 1.启用 2.停用", example = "1", position = 40)
    private Integer status;
}
