package org.lanjerry.admin.vo.sys;

import java.time.LocalDateTime;
import java.util.Set;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户分页查询VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPageVO extends BaseEntity {

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
    @ApiModelProperty(value = "状态 1.启用 2.停用", example = "{\"value\": 1,\"text\": \"启用\",\"name\": \"ENABLE\"}", position = 40)
    private SysUserStatusEnum status;

    /**
     * 角色集
     */
    @ApiModelProperty(value = "角色集", example = "[\"采购\", \"销售\", \"管理员\"]", position = 50)
    private Set<String> roles;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "管理员", position = 60)
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-22 15:57:53", position = 70)
    private LocalDateTime createdTime;
}
