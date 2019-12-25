package org.lanjerry.admin.vo.global;

import java.time.LocalDateTime;
import java.util.Set;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysUserSexEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户基本资料VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentUserProfileVO extends BaseEntity {

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址", example = "image/touxiang.png", position = 10)
    private String avatar;

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
     * 性别 1.男 2.女 3.未知
     */
    @ApiModelProperty(value = "性别 1.男 2.女 3.未知", example = "{\"value\": 1,\"text\": \"男\",\"name\": \"MALE\"}", position = 60)
    private SysUserSexEnum sex;

    /**
     * 角色集
     */
    @ApiModelProperty(value = "角色集", example = "[\"采购\", \"销售\", \"管理员\"]", position = 70)
    private Set<String> roles;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-22 15:57:53", position = 80)
    private LocalDateTime createdTime;
}
