package org.lanjerry.admin.vo.sys;

import java.util.Set;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysUserSexEnum;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户信息
 * </p>
 *
 * @author lanjerry
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统用户信息")
public class SysUserInfoVO extends BaseEntity {

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
     * 性别 1.男 2.女 3.未知
     */
    @ApiModelProperty(value = "性别 1.男 2.女 3.未知", example = "{\"value\": 1,\"text\": \"男\",\"name\": \"MALE\"}", position = 30)
    private SysUserSexEnum sex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", example = "38046851@qq.com", position = 40)
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "13623012336", position = 50)
    private String phone;

    /**
     * 状态 1.启用 2.停用
     */
    @ApiModelProperty(value = "状态 1.启用 2.停用", example = "{\"value\": 1,\"text\": \"启用\",\"name\": \"ENABLE\"}", position = 60)
    private SysUserStatusEnum status;

    /**
     * 角色编号集
     */
    @ApiModelProperty(value = "角色编号集", example = "[1, 2, 7]", position = 70)
    private Set<Integer> roleIds;
}
