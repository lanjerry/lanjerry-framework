package org.lanjerry.admin.enums.tool;

import org.lanjerry.common.core.bean.BaseEnum;

/**
 * <p>
 * 代码生成模板功能枚举
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-24
 */
public enum ToolGenTplFunctionEnum implements BaseEnum<String> {
    PAGE_LIST("pageList", "分页列表"),
    LIST("list", "不分页列表"),
    ADD("add", "新增"),
    UPDATE("update", "修改"),
    DELETE("delete", "删除"),
    EXPORT("export", "导出"),
    IMPORT("import", "导入");

    private final String value;
    private final String text;

    ToolGenTplFunctionEnum(final String value, final String text) {
        this.value = value;
        this.text = text;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getName() {
        return name();
    }
}
