package org.lanjerry.common.core.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 对象复制工具类
 *
 * @author chenhu
 * @since 2018/8/1
 */
public class BeanCopyUtil {

    /**
     * 分页对象复制
     *
     * @param sourcePage  原始Page对象
     * @param sourceClass 资源Class对象
     * @param targetClass 目标Class对象
     * @param <T>         目标对象类型
     * @return Page<T>目标对象的分页对象
     */

    public static <T> Page<T> pageCopy(IPage sourcePage, Class sourceClass, Class targetClass) {
        Page<T> page = new Page<>(sourcePage.getCurrent(), sourcePage.getSize(), sourcePage.getTotal());
        BeanCopier beanCopier = BeanCopier.create(sourceClass, targetClass, false);
        List<T> records = new ArrayList<>();
        sourcePage.getRecords().forEach(item -> {
            T targetObj = null;
            try {
                targetObj = (T) targetClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            beanCopier.copy(item, targetObj, null);
            records.add(targetObj);
        });
        page.setRecords(records);
        return page;
    }

    /**
     * 对象属性复制
     *
     * @param sourceObj   资源对象
     * @param targetClass 目标Class对象
     * @param <T>         目标对象类型
     * @return 目标对象
     */
    public static <T> T beanCopy(Object sourceObj, Class targetClass) {
        if (sourceObj == null) {
            return null;
        }
        BeanCopier beanCopier = BeanCopier.create(sourceObj.getClass(), targetClass, false);
        T targetObj = null;
        try {
            targetObj = (T) targetClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        beanCopier.copy(sourceObj, targetObj, null);
        return targetObj;
    }

    /**
     * 对象集合属性复制
     *
     * @param sourceList  资源对象集合
     * @param sourceClass 资源Class字节码对象
     * @param targetClass 目标Class字节码对象
     * @param <T>         目标对象类型
     * @return List<T> 目标对象集合
     */
    public static <T> List<T> listCopy(List sourceList, Class sourceClass, Class targetClass) {
        if (sourceList == null) {
            return null;
        }
        BeanCopier beanCopier = BeanCopier.create(sourceClass, targetClass, false);
        List<T> results = new ArrayList<>();
        sourceList.forEach(item -> {
            T targetObj = null;
            try {
                targetObj = (T) targetClass.newInstance();

            } catch (Exception e) {
                e.printStackTrace();
            }
            beanCopier.copy(item, targetObj, null);
            results.add(targetObj);
        });
        return results;
    }
}
