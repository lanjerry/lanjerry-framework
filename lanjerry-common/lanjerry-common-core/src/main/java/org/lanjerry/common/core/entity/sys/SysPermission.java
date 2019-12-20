package org.lanjerry.common.core.entity.sys;

import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysPermissionStatusEnum;
import org.lanjerry.common.core.enums.sys.SysPermissionTypeEnum;

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
 * 系统权限表
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class SysPermission extends BaseEntity {

    /**
     * 权限编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 父类id，当值等于0时，代表的是一级的菜单
     */
    private Integer parentId;

    /**
     * 类型 1.菜单 2.按钮
     */
    private SysPermissionTypeEnum type;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 1.显示 2.隐藏
     */
    private SysPermissionStatusEnum status;

    /**
     * 是否外链 0.否 1.是
     */
    private Boolean frameFlag;

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
