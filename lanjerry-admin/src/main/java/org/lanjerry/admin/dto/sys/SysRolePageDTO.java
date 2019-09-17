package org.lanjerry.admin.dto.sys;

import org.lanjerry.common.core.bean.SplitPage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色分页查询DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRolePageDTO extends SplitPage {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "id", example = "1", position = 10)
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "销售", position = 20)
    private String name;
}
