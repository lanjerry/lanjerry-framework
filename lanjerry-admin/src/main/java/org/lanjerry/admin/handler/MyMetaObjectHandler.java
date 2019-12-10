package org.lanjerry.admin.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.lanjerry.common.auth.shiro.jwt.JwtToken;
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
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        if (token != null) {
            this.strictInsertFill(metaObject, "creatorId", Integer.class, token.getId());
            this.strictInsertFill(metaObject, "creatorName", String.class, token.getName());
        }
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "deleteFlag", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
    }
}