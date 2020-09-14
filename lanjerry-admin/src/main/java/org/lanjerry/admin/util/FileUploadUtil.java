package org.lanjerry.admin.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.lanjerry.common.core.util.ApiAssert;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 上传工具类
 * </p>
 *
 * @author lanjerry
 * @since 2020-06-01
 */
public class FileUploadUtil {

    /**
     * 上传路径
     */
    public static final String UPLOAD_PATH = "D:/upload";

    /**
     * 资源路径
     */
    public static final String RESOURCE_PREFIX = "/upload";

    public static String upload(MultipartFile file) throws IOException {
        try {
            // 文件长度和文件后缀名
            int fileLength = file.getOriginalFilename().length();
            String fileSuffix = getFileSuffix(file);
            // 验证
            ApiAssert.isTrue(fileLength <= MimeTypeUtils.DEFAULT_FILE_NAME_LENGTH,
                    StrUtil.format("文件名最大长度只能到：{}", MimeTypeUtils.DEFAULT_FILE_NAME_LENGTH));
            ApiAssert.isTrue(file.getSize() <= MimeTypeUtils.DEFAULT_MAX_SIZE,
                    StrUtil.format("文件名最大只能为：{}", "50M"));
            ApiAssert.isTrue(Arrays.asList(MimeTypeUtils.ALL_FORMAT).contains(fileSuffix),
                    "上传文件格式有误");
            // 生成文件名
            String fileName = extractFilename(fileSuffix);
            // 文件类型
            String fileType = Arrays.asList(MimeTypeUtils.IMAGE_FORMAT).contains(fileSuffix) ? MimeTypeUtils.IMAGE_TYPE : MimeTypeUtils.FILE_TYPE;
            // 上传文件
            File desc = getAbsoluteFile(FileUploadUtil.UPLOAD_PATH.concat(fileType), fileName);
            file.transferTo(desc);
            // 返回文件路径
            String pathFileName = getPathFileName(FileUploadUtil.RESOURCE_PREFIX.concat(fileType), fileName);
            return pathFileName;
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 编码文件名
     */
    public static String extractFilename(String suffix) {
        String fileName = datePath() + "/" + UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        return fileName;
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 上传文件
     */
    private static File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 获取文件路径
     */
    private static String getPathFileName(String resourcePrefix, String fileName) {
        return resourcePrefix + "/" + fileName;
    }

    /**
     * 获取文件后缀
     */
    public static String getFileSuffix(MultipartFile file) {
        String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StrUtil.isBlank(suffix)) {
            suffix = MimeTypeUtils.getExtension(file.getContentType());
        }
        return suffix;
    }
}