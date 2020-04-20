package org.lanjerry.admin.vo.tool;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成预览信息
 * </p>
 *
 * @author lanjerry
 * @since 2020-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成预览信息")
public class ToolGenPreviewVO extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
}
