package org.lanjerry.common.core.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分页类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SplitPage extends BaseEntity {

    /**
     * 当前页，默认1（从1开始）
     */
    @ApiModelProperty(value = "当前页，默认1（从1开始）", example = "1", required = true)
    private Integer pageNum = 1;

    /**
     * 每页行数，默认10
     */
    @ApiModelProperty(value = "每页行数，默认10", example = "10", required = true)
    private Integer pageSize = 10;
}
