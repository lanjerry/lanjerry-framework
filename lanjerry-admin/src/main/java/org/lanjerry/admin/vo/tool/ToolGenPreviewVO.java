package org.lanjerry.admin.vo.tool;

import org.lanjerry.common.core.bean.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成预览VO
 * </p>
 *
 * @author lanjerry
 * @since 2020-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
