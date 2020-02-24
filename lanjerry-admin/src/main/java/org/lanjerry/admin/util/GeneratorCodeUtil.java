package org.lanjerry.admin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.lanjerry.admin.vo.tool.ToolGenCodeVO;
import org.lanjerry.common.core.entity.tool.ToolGen;

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
        velocityContext.put("functionName", StrUtil.isNotBlank(gen.getFunctionName()) ? gen.getFunctionName() : "【请填写功能名称】");
        velocityContext.put("pkComment", gen.getPkComment());
        velocityContext.put("pkJavaType", gen.getPkJavaType());
        velocityContext.put("pkJavaField", gen.getPkJavaField());
        velocityContext.put("ClassName", gen.getClassName());
        velocityContext.put("className", StrUtil.lowerFirst(gen.getClassName()));
        velocityContext.put("moduleName", gen.getModuleName());
        velocityContext.put("BusinessName", StrUtil.upperFirst(gen.getBusinessName()));
        velocityContext.put("businessName", gen.getBusinessName());
        //velocityContext.put("basePackage", StrUtil.subBefore(gen.getPackageName(), ".", true));
        velocityContext.put("packageName", gen.getPackageName());
        velocityContext.put("author", gen.getFunctionAuthor());
        velocityContext.put("datetime", DateUtil.today());
        velocityContext.put("permissionPrefix", String.format("%s:%s", gen.getModuleName(), gen.getBusinessName()));
        return velocityContext;
    }

    /**
     * 获取模板列表
     *
     * @author lanjerry
     * @since 2020/2/24 1:40
     */
    public static List<String> getTemplates(String tplFunction) {
        List<String> result = new ArrayList<>();
        result.add("vm/java/controller.java.vm");
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
                result = String.format("%s/controller/%sController.java", javaPath, gen.getClassName());
                break;
            default:
                result = "";
        }
        return result;
    }
}
