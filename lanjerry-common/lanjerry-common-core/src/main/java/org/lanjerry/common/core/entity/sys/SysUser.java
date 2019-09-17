package org.lanjerry.common.core.entity.sys;

import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.UserStatusEnum;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * 系统用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;

    /**
     * 状态 1.正常 2.禁用
     */
    private UserStatusEnum status;

    /**
     * 是否删除 0.否 1.是
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleteFlag;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer creatorId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creatorName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
