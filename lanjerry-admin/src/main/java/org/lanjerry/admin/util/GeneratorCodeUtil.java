package org.lanjerry.admin.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.lanjerry.admin.vo.tool.ToolGenCodeColumnVO;
import org.lanjerry.admin.vo.tool.ToolGenCodeVO;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 代码生成工具类
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-23
 */
public class GeneratorCodeUtil {

    /**
     * 初始化vm模板
     *
     * @author lanjerry
     * @since 2020/2/23 1:10
     */
    public static void initVelocity() {
        Properties properties = new Properties();
        // 加载classpath目录下的vm文件
        properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        // 定义字符集
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        // 初始化Velocity引擎，指定配置Properties
        Velocity.init(properties);
    }

    /**
     * 设置模板变量信息
     *
     * @author lanjerry
     * @since 2020/2/23 13:22
     */
    public static VelocityContext setVelocityContext(ToolGenCodeVO gen) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplFunctions", gen.getTplFunctions());
        velocityContext.put("tableName", gen.getTableName());
        velocityContext.put("tableComment", gen.getTableComment());
        velocityContext.put("functionName", gen.getFunctionName());
        velocityContext.put("pkComment", gen.getPkComment());
        velocityContext.put("pkJavaType", gen.getPkJavaType());
        velocityContext.put("pkJavaField", gen.getPkJavaField());
        velocityContext.put("PkJavaField", StrUtil.upperFirst(gen.getPkJavaField()));
        velocityContext.put("ClassName", gen.getClassName());
        velocityContext.put("className", StrUtil.lowerFirst(gen.getClassName()));
        velocityContext.put("moduleName", gen.getModuleName());
        velocityContext.put("BusinessName", StrUtil.upperFirst(gen.getBusinessName()));
        velocityContext.put("businessName", gen.getBusinessName());
        velocityContext.put("basePackage", gen.getBasePackage());
        velocityContext.put("packageName", gen.getPackageName());
        velocityContext.put("author", gen.getFunctionAuthor());
        velocityContext.put("date", DateUtil.today());
        velocityContext.put("datetime", DateUtil.now());
        velocityContext.put("permissionPrefix", String.format("%s:%s", gen.getModuleName(), gen.getBusinessName()));
        velocityContext.put("permissionParentId", gen.getPermissionParentId());
        velocityContext.put("permissionSort", gen.getPermissionSort());
        velocityContext.put("columns", gen.getColumns());

        // 列表字段
        List<ToolGenCodeColumnVO> listColumns = gen.getColumns().stream().filter(ToolGenCodeColumnVO::getListFlag).collect(Collectors.toList());
        velocityContext.put("listColumns", listColumns);

        // 表单字段
        List<ToolGenCodeColumnVO> formColumns = gen.getColumns().stream().filter(ToolGenCodeColumnVO::getFormFlag).collect(Collectors.toList());
        velocityContext.put("formColumns", formColumns);

        // 查询字段
        List<ToolGenCodeColumnVO> queryColumns = gen.getColumns().stream().filter(ToolGenCodeColumnVO::getQueryFlag).collect(Collectors.toList());
        velocityContext.put("queryColumns", queryColumns);

        // 导出字段
        List<ToolGenCodeColumnVO> exportColumns = gen.getColumns().stream().filter(ToolGenCodeColumnVO::getExportFlag).collect(Collectors.toList());
        velocityContext.put("exportColumns", exportColumns);

        // 唯一字段
        List<ToolGenCodeColumnVO> onlyColumns = gen.getColumns().stream().filter(ToolGenCodeColumnVO::getOnlyFlag).collect(Collectors.toList());
        velocityContext.put("onlyColumns", onlyColumns);

        // controller导入包
        velocityContext.put("controllerImports", getControllerImports(gen));

        // service导入包
        velocityContext.put("serviceImports", getServiceImports(gen));

        // serviceImpl导入包
        velocityContext.put("serviceImplImports", getServiceImplImports(gen));

        // entity导入包
        velocityContext.put("entityImports", getEntityImports(gen.getColumns()));

        // selectVO导入包
        velocityContext.put("selectVOImports", getVOorSelectDTOImports(listColumns));

        // infoVO导入包
        velocityContext.put("infoVOImports", getVOorSelectDTOImports(formColumns));

        // selectDTO导入包
        velocityContext.put("selectDTOImports", getVOorSelectDTOImports(queryColumns));

        // exportVO导入包
        velocityContext.put("exportVOImports", getVOorSelectDTOImports(exportColumns));

        // saveOrUpdateDTO导入包
        velocityContext.put("saveOrUpdateDTOImports", getSaveOrUpdateDTOImports(formColumns));

        // 编辑页面api方法名
        velocityContext.put("apiModifyFunctions", getApiModifyFunctions(gen));

        // 主页api方法名
        velocityContext.put("apiIndexFunctions", getApiIndexFunctions(gen));

        // 模板功能
        velocityContext.put("showPageListFlag", CollUtil.isNotEmpty(gen.getTplFunctions()) && gen.getTplFunctions().indexOf("pageList") != -1);
        velocityContext.put("showListFlag", CollUtil.isNotEmpty(gen.getTplFunctions()) && gen.getTplFunctions().indexOf("list") != -1);
        velocityContext.put("showAddFlag", CollUtil.isNotEmpty(gen.getTplFunctions()) && gen.getTplFunctions().indexOf("add") != -1);
        velocityContext.put("showUpdateFlag", CollUtil.isNotEmpty(gen.getTplFunctions()) && gen.getTplFunctions().indexOf("update") != -1);
        velocityContext.put("showDeleteFlag", CollUtil.isNotEmpty(gen.getTplFunctions()) && gen.getTplFunctions().indexOf("delete") != -1);
        velocityContext.put("showExportFlag", CollUtil.isNotEmpty(gen.getTplFunctions()) && gen.getTplFunctions().indexOf("export") != -1);
        return velocityContext;
    }

    /**
     * 获取模板列表
     *
     * @author lanjerry
     * @since 2020/2/24 1:40
     */
    public static List<String> getTemplates(List<String> tplFunctions) {
        List<String> result = new ArrayList<>();
        result.add("vm/java/entity.java.vm");
        result.add("vm/java/controller.java.vm");
        result.add("vm/java/service.java.vm");
        result.add("vm/java/serviceImpl.java.vm");
        result.add("vm/java/mapper.java.vm");
        result.add("vm/xml/mapper.xml.vm");
        result.add("vm/sql/sql.vm");
        if (CollectionUtil.isNotEmpty(tplFunctions)) {
            if (tplFunctions.indexOf("pageList") != -1) {
                result.add("vm/java/pageDTO.java.vm");
            }
            if (tplFunctions.indexOf("list") != -1) {
                result.add("vm/java/listDTO.java.vm");
            }
            if (tplFunctions.indexOf("pageList") != -1) {
                result.add("vm/java/pageVO.java.vm");
            }
            if (tplFunctions.indexOf("list") != -1) {
                result.add("vm/java/listVO.java.vm");
            }
            if (tplFunctions.indexOf("update") != -1) {
                result.add("vm/java/infoVO.java.vm");
            }
            if (tplFunctions.indexOf("add") != -1 && tplFunctions.indexOf("update") != -1) {
                result.add("vm/java/saveOrUpdateDTO.java.vm");
            }
            if (tplFunctions.indexOf("add") != -1 && tplFunctions.indexOf("update") == -1) {
                result.add("vm/java/saveDTO.java.vm");
            }
            if (tplFunctions.indexOf("update") != -1 && tplFunctions.indexOf("add") == -1) {
                result.add("vm/java/updateDTO.java.vm");
            }
            if (tplFunctions.indexOf("export") != -1) {
                result.add("vm/java/exportDTO.java.vm");
                result.add("vm/java/exportVO.java.vm");
            }
            result.add("vm/js/api.js.vm");
            result.add("vm/html/index.vue.vm");
            if (tplFunctions.indexOf("pageList") != -1 || tplFunctions.indexOf("list") != -1) {
                result.add("vm/html/searchForm.vue.vm");
            }
            if (tplFunctions.indexOf("add") != -1 || tplFunctions.indexOf("update") != -1) {
                result.add("vm/html/modifyDialog.vue.vm");
            }
        }
        return result;
    }

    /**
     * 获取文件名
     *
     * @author lanjerry
     * @since 2020/2/24 1:40
     */
    public static String getFileName(String template, ToolGenCodeVO gen) {
        String result = "";
        String javaPath = AdminConsts.GEN_PROJECT_PATH + "/" + StrUtil.replace(gen.getPackageName(), ".", "/");
        String vuePath = "vue/src/";
        switch (template) {
            case "vm/java/entity.java.vm":
                result = StrUtil.format("{}/common/core/entity/{}/{}.java", AdminConsts.GEN_PROJECT_PATH + "/" + StrUtil.replace(gen.getBasePackage(), ".", "/"), gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/controller.java.vm":
                result = StrUtil.format("{}/controller/{}/{}Controller.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/service.java.vm":
                result = StrUtil.format("{}/service/{}/{}Service.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/serviceImpl.java.vm":
                result = StrUtil.format("{}/service/{}/impl/{}ServiceImpl.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/mapper.java.vm":
                result = StrUtil.format("{}/mapper/{}/{}Mapper.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/xml/mapper.xml.vm":
                result = StrUtil.format("main/resources/mapper/{}/{}Mapper.xml", gen.getModuleName(), gen.getClassName());
                break;
            case "vm/sql/sql.vm":
                result = StrUtil.format("sql/{}.sql", gen.getTableName());
                break;
            case "vm/java/pageDTO.java.vm":
                result = StrUtil.format("{}/dto/{}/{}PageDTO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/pageVO.java.vm":
                result = StrUtil.format("{}/vo/{}/{}PageVO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/listDTO.java.vm":
                result = StrUtil.format("{}/dto/{}/{}ListDTO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/listVO.java.vm":
                result = StrUtil.format("{}/vo/{}/{}ListVO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/infoVO.java.vm":
                result = StrUtil.format("{}/vo/{}/{}InfoVO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/saveDTO.java.vm":
                result = StrUtil.format("{}/dto/{}/{}SaveDTO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/updateDTO.java.vm":
                result = StrUtil.format("{}/dto/{}/{}UpdateDTO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/saveOrUpdateDTO.java.vm":
                result = StrUtil.format("{}/dto/{}/{}SaveOrUpdateDTO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/exportDTO.java.vm":
                result = StrUtil.format("{}/dto/{}/{}ExportDTO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/java/exportVO.java.vm":
                result = StrUtil.format("{}/vo/{}/{}ExportVO.java", javaPath, gen.getModuleName(), gen.getClassName());
                break;
            case "vm/js/api.js.vm":
                result = StrUtil.format("{}/api/{}/{}.js", vuePath, gen.getModuleName(), gen.getBusinessName());
                break;
            case "vm/html/index.vue.vm":
                result = StrUtil.format("{}/views/{}/{}.vue", vuePath, gen.getModuleName(), gen.getBusinessName());
                break;
            case "vm/html/searchForm.vue.vm":
                result = StrUtil.format("{}/components/{}/{}/{}SearchForm.vue", vuePath, gen.getModuleName(), gen.getBusinessName(), gen.getClassName());
                break;
            case "vm/html/modifyDialog.vue.vm":
                result = StrUtil.format("{}/components/{}/{}/{}ModifyDialog.vue", vuePath, gen.getModuleName(), gen.getBusinessName(), gen.getClassName());
                break;
        }
        return result;
    }

    /**
     * 根据字段类型转换成java类型
     *
     * @author lanjerry
     * @since 2020/2/23 0:56
     * @param columnType 字段类型
     */
    public static String getJavaType(String columnType) {
        String dbType = StrUtil.subBefore(columnType, "(", false);
        if (ArrayUtil.contains(AdminConsts.GEN_COLUMNTYPE_STR, dbType)) {
            return AdminConsts.GEN_TYPE_STRING;
        }
        if (ArrayUtil.contains(AdminConsts.GEN_COLUMNTYPE_TIME, dbType)) {
            return AdminConsts.GEN_TYPE_DATE;
        }
        if (ArrayUtil.contains(AdminConsts.GEN_COLUMNTYPE_NUMBER, dbType)) {
            String result;
            String[] dbLength = StrUtil.subBetween(columnType, "(", ")").split(",");
            switch (dbType) {
                case "tinyint":
                    result = "1".equals(dbLength[0]) ? AdminConsts.GEN_TYPE_BOOLEAN : AdminConsts.GEN_TYPE_INTEGER;
                    break;
                case "bigint":
                    result = AdminConsts.GEN_TYPE_LONG;
                    break;
                case "float":
                    result = AdminConsts.GEN_TYPE_FLOAT;
                    break;
                case "double":
                    result = AdminConsts.GEN_TYPE_DOUBLE;
                    break;
                case "decimal":
                    result = AdminConsts.GEN_TYPE_BIGDECIMAL;
                    break;
                default:
                    result = AdminConsts.GEN_TYPE_INTEGER;
                    break;
            }
            return result;
        }
        return dbType;
    }

    /**
     * 根据java类型设置字段例子值
     *
     * @author lanjerry
     * @since 2020/3/1 1:12
     */
    public static String getColumnExample(ToolGenCodeColumnVO column) {
        String result = column.getColumnComment();
        switch (column.getJavaType()) {
            case "String":
                result = "测试" + column.getColumnComment();
                break;
            case "LocalDateTime":
                result = DateUtil.date().toString();
                break;
            case "Integer":
                result = "1";
                break;
            case "Long":
                result = "1L";
                break;
            case "Float":
                result = "1.18";
                break;
            case "BigDecimal":
                result = "1.18";
                break;
            case "Boolean":
                result = "true";
                break;
        }
        return result;
    }

    public static String getColumnQueryInitValue(ToolGenCodeColumnVO column) {
        String result = "undefined";
        if (CollectionUtil.contains(Arrays.asList("Integer", "BigDecimal", "LocalDateTime"), column.getJavaType())) {
            result = "null";
        }
        return result;
    }

    public static String getColumnFormInitValue(ToolGenCodeColumnVO column) {
        String result = "''";
        if (CollectionUtil.contains(Arrays.asList("Integer", "BigDecimal", "LocalDateTime"), column.getJavaType())) {
            result = "null";
        }
        return result;
    }

    private static List<String> getControllerImports(ToolGenCodeVO gen) {
        List<String> result = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(gen.getTplFunctions())) {
            result.add(StrUtil.format("{}.service.{}.{}Service;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            result.add(StrUtil.format("{}.common.core.bean.ApiResult;", gen.getBasePackage()));
            result.add(StrUtil.format("{}.common.log.annotation.SysLog;", gen.getBasePackage()));
            if (gen.getTplFunctions().indexOf("pageList") != -1) {
                result.add("com.baomidou.mybatisplus.core.metadata.IPage;");
                result.add(StrUtil.format("{}.dto.{}.{}PageDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("list") != -1) {
                result.add("java.util.List;");
                result.add(StrUtil.format("{}.dto.{}.{}ListDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("add") != -1 && gen.getTplFunctions().indexOf("update") != -1) {
                result.add(StrUtil.format(" {}.dto.{}.{}SaveOrUpdateDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("add") != -1 && gen.getTplFunctions().indexOf("update") == -1) {
                result.add(StrUtil.format("{}.dto.{}.{}SaveDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("update") != -1 && gen.getTplFunctions().indexOf("add") == -1) {
                result.add(StrUtil.format("{}.dto.{}.{}UpdateDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("update") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}InfoVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("pageList") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}PageVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("list") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}ListVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("delete") != -1) {
                result.add("org.springframework.web.bind.annotation.DeleteMapping;");
            }
            if (gen.getTplFunctions().indexOf("pageList") != -1 || gen.getTplFunctions().indexOf("update") != -1) {
                result.add("org.springframework.web.bind.annotation.GetMapping;");
            }
            if (gen.getTplFunctions().indexOf("update") != -1 || gen.getTplFunctions().indexOf("delete") != -1) {
                result.add("org.springframework.web.bind.annotation.PathVariable;");
            }
            if (gen.getTplFunctions().indexOf("add") != -1) {
                result.add("org.springframework.web.bind.annotation.PostMapping;");
            }
            if (gen.getTplFunctions().indexOf("update") != -1) {
                result.add("org.springframework.web.bind.annotation.PutMapping;");
            }
        }
        return result;
    }

    private static List<String> getServiceImports(ToolGenCodeVO gen) {
        List<String> result = new ArrayList<>();
        result.add(StrUtil.format("{}.common.core.entity.{}.{};", gen.getBasePackage(), gen.getModuleName(), gen.getClassName()));
        if (CollectionUtil.isNotEmpty(gen.getTplFunctions())) {
            if (gen.getTplFunctions().indexOf("pageList") != -1) {
                result.add("com.baomidou.mybatisplus.core.metadata.IPage;");
                result.add(StrUtil.format("{}.dto.{}.{}PageDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("list") != -1) {
                result.add("java.util.List;");
                result.add(StrUtil.format("{}.dto.{}.{}ListDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("add") != -1 && gen.getTplFunctions().indexOf("update") != -1) {
                result.add(StrUtil.format(" {}.dto.{}.{}SaveOrUpdateDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("add") != -1 && gen.getTplFunctions().indexOf("update") == -1) {
                result.add(StrUtil.format("{}.dto.{}.{}SaveDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("update") != -1 && gen.getTplFunctions().indexOf("add") == -1) {
                result.add(StrUtil.format("{}.dto.{}.{}UpdateDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("update") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}InfoVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("pageList") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}PageVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("list") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}ListVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("export") != -1) {
                result.add("javax.servlet.http.HttpServletResponse;");
                result.add(StrUtil.format("{}.dto.{}.{}ExportDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
        }
        return result;
    }

    private static List<String> getServiceImplImports(ToolGenCodeVO gen) {
        List<String> result = new ArrayList<>();
        result.add(StrUtil.format("{}.mapper.{}.{}Mapper;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
        result.add(StrUtil.format("{}.common.core.entity.{}.{};", gen.getBasePackage(), gen.getModuleName(), gen.getClassName()));
        result.add(StrUtil.format("{}.service.{}.{}Service;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
        if (CollectionUtil.isNotEmpty(gen.getTplFunctions())) {
            result.add(StrUtil.format("{}.util.AdminConsts;", gen.getPackageName()));
            result.add(StrUtil.format("{}.common.core.util.ApiAssert;", gen.getBasePackage()));
            result.add(StrUtil.format("{}.common.core.util.BeanCopyUtil;", gen.getBasePackage()));
            result.add("com.baomidou.mybatisplus.core.toolkit.Wrappers;");
            result.add("cn.hutool.core.util.StrUtil;");
            if (gen.getTplFunctions().indexOf("pageList") != -1) {
                result.add("com.baomidou.mybatisplus.core.metadata.IPage;");
                result.add("com.baomidou.mybatisplus.extension.plugins.pagination.Page;");
                result.add(StrUtil.format("{}.dto.{}.{}PageDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("list") != -1) {
                result.add("java.util.List;");
                result.add(StrUtil.format("{}.dto.{}.{}ListDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("add") != -1 && gen.getTplFunctions().indexOf("update") != -1) {
                result.add(StrUtil.format(" {}.dto.{}.{}SaveOrUpdateDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("add") != -1 && gen.getTplFunctions().indexOf("update") == -1) {
                result.add(StrUtil.format("{}.dto.{}.{}SaveDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("update") != -1 && gen.getTplFunctions().indexOf("add") == -1) {
                result.add(StrUtil.format("{}.dto.{}.{}UpdateDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("update") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}InfoVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("pageList") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}PageVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("list") != -1) {
                result.add(StrUtil.format("{}.vo.{}.{}ListVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
            if (gen.getTplFunctions().indexOf("export") != -1) {
                result.add("java.io.IOException;");
                result.add("java.net.URLEncoder;");
                result.add("javax.servlet.http.HttpServletResponse;");
                result.add(StrUtil.format("{}.dto.{}.{}ExportDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
                result.add(StrUtil.format("{}.vo.{}.{}ExportVO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
            }
        }
        return result;
    }

    private static List<String> getEntityImports(List<ToolGenCodeColumnVO> columns) {
        List<String> result = new ArrayList<>();
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("BigDecimal"))) {
            result.add("java.math.BigDecimal;");
        }
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("LocalDateTime"))) {
            result.add("java.time.LocalDateTime;");
        }
        if (columns.stream().anyMatch(c -> CollectionUtil.contains(Arrays.asList("deleteFlag", "creatorId", "creatorName", "createdTime", "updatedTime"), c.getJavaField()))) {
            result.add("com.baomidou.mybatisplus.annotation.FieldFill;");
            result.add("com.baomidou.mybatisplus.annotation.TableField;");
        }
        if (columns.stream().anyMatch(ToolGenCodeColumnVO::getPkFlag)) {
            result.add("com.baomidou.mybatisplus.annotation.IdType;");
            result.add("com.baomidou.mybatisplus.annotation.TableId;");
        }
        if (columns.stream().anyMatch(c -> c.getJavaField().equals("deleteFlag"))) {
            result.add("com.baomidou.mybatisplus.annotation.TableLogic;");
        }
        return result;
    }

    private static List<String> getVOorSelectDTOImports(List<ToolGenCodeColumnVO> columns) {
        List<String> result = new ArrayList<>();
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("BigDecimal"))) {
            result.add("java.math.BigDecimal;");
        }
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("LocalDateTime"))) {
            result.add("java.time.LocalDateTime;");
        }
        return result;
    }

    private static List<String> getSaveOrUpdateDTOImports(List<ToolGenCodeColumnVO> columns) {
        List<String> result = new ArrayList<>();
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("BigDecimal"))) {
            result.add("java.math.BigDecimal;");
        }
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("LocalDateTime"))) {
            result.add("java.time.LocalDateTime;");
        }
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("String"))) {
            result.add("javax.validation.constraints.Size;");
        }
        if (columns.stream().anyMatch(c -> c.getJavaField().contains("mail") || c.getJavaField().contains("Mail"))) {
            result.add("javax.validation.constraints.Email;");
        }
        if (columns.stream().anyMatch(c -> c.getJavaField().contains("phone") || c.getJavaField().contains("Phone"))) {
            result.add("javax.validation.constraints.Pattern;");
        }
        if (columns.stream().anyMatch(c -> c.getRequiredFlag() && c.getJavaType().equals("String"))) {
            result.add("javax.validation.constraints.NotBlank;");
        }
        if (columns.stream().anyMatch(c -> c.getRequiredFlag() && !c.getJavaType().equals("String"))) {
            result.add("javax.validation.constraints.NotNull;");
        }
        return result;
    }

    private static List<String> getApiModifyFunctions(ToolGenCodeVO gen) {
        List<String> result = new ArrayList<>();
        if (CollUtil.isNotEmpty(gen.getTplFunctions())) {
            if (gen.getTplFunctions().indexOf("update") != -1) {
                result.add(StrUtil.format("get{}", StrUtil.upperFirst(gen.getBusinessName())));
            }
            if (gen.getTplFunctions().indexOf("add") != -1) {
                result.add(StrUtil.format("save{}", StrUtil.upperFirst(gen.getBusinessName())));
            }
            if (gen.getTplFunctions().indexOf("update") != -1) {
                result.add(StrUtil.format("update{}", StrUtil.upperFirst(gen.getBusinessName())));
            }
        }
        return result;
    }

    private static List<String> getApiIndexFunctions(ToolGenCodeVO gen) {
        List<String> result = new ArrayList<>();
        if (CollUtil.isNotEmpty(gen.getTplFunctions())) {
            if (gen.getTplFunctions().indexOf("pageList") != -1) {
                result.add(StrUtil.format("page{}s", StrUtil.upperFirst(gen.getBusinessName())));
            }
            if (gen.getTplFunctions().indexOf("list") != -1) {
                result.add(StrUtil.format("list{}s", StrUtil.upperFirst(gen.getBusinessName())));
            }
            if (gen.getTplFunctions().indexOf("delete") != -1) {
                result.add(StrUtil.format("remove{}s", StrUtil.upperFirst(gen.getBusinessName())));
            }
            if (gen.getTplFunctions().indexOf("export") != -1) {
                result.add(StrUtil.format("export{}s", StrUtil.upperFirst(gen.getBusinessName())));
            }
        }
        return result;
    }
}
