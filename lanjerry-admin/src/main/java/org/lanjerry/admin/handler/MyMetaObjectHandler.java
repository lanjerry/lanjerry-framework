package org.lanjerry.admin.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * <p>
 * 自定义填充处理器
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("新增方法实体填充");
        this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("deleteFlag", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("更新方法实体填充");
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }
}