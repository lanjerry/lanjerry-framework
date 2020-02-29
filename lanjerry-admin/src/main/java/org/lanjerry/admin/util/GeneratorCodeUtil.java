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
import org.lanjerry.common.core.entity.tool.ToolGen;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
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

        // infoVO导入包
        velocityContext.put("infoVOImports", getDTOorVOImports(formColumns));

        // pageDTO导入包
        velocityContext.put("pageDTOImports", getDTOorVOImports(queryColumns));

        // pageVO导入包
        velocityContext.put("pageVOImports", getDTOorVOImports(listColumns));
        return velocityContext;
    }

    /**
     * 获取模板列表
     *
     * @author lanjerry
     * @since 2020/2/24 1:40
     */
    public static List<String> getTemplates(String tplFunctions) {
        List<String> result = new ArrayList<>();
        result.add("vm/java/controller.java.vm");
        result.add("vm/java/mapper.java.vm");
        result.add("vm/java/service.java.vm");
        result.add("vm/java/serviceImpl.java.vm");
        result.add("vm/java/entity.java.vm");
        result.add("vm/java/pageDTO.java.vm");
        result.add("vm/java/infoVO.java.vm");
        result.add("vm/java/pageVO.java.vm");
        result.add("vm/xml/mapper.xml.vm");
        return result;
    }

    /**
     * 获取文件名
     *
     * @author lanjerry
     * @since 2020/2/24 1:40
     */
    public static String getFileName(String template, ToolGen gen) {
        String result = "";
        String javaPath = AdminConsts.GEN_PROJECT_PATH + "/" + StrUtil.replace(gen.getPackageName(), ".", "/");
        switch (template) {
            case "vm/java/controller.java.vm":
                result = StrUtil.format("{}/controller/{}Controller.java", javaPath, gen.getClassName());
                break;
            default:
                result = "";
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
                result.add(StrUtil.format("{}.dto.{}.{}PageDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
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
                result.add(StrUtil.format("{}.dto.{}.{}PageDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
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
            if (gen.getTplFunctions().indexOf("pageList") != -1) {
                result.add(StrUtil.format("{}.dto.{}.{}PageDTO;", gen.getPackageName(), gen.getModuleName(), gen.getClassName()));
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

    private static List<String> getDTOorVOImports(List<ToolGenCodeColumnVO> columns) {
        List<String> result = new ArrayList<>();
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("BigDecimal"))) {
            result.add("java.math.BigDecimal;");
        }
        if (columns.stream().anyMatch(c -> c.getJavaType().equals("LocalDateTime"))) {
            result.add("java.time.LocalDateTime;");
        }
        return result;
    }
}
