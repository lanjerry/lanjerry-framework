# lanjerry-framework

## 简介
&emsp;基于 SpringBoot2、JWT和Shiro实现的前后端分离开发框架，接口遵循RESTful风格，文档使用swagger。

## 在线演示

[https://xxxx(待完善)](https://xxxx) 

- 账号：admin
- 密码：admin 

## 特性
1. 使用MyBatis-Plus作为持久层框架，代码优雅、简化开发、提高效率
2. 使用Shiro+JWT作为sso，抛弃传统session的做法 
3. 抽离日志模块，实现可插拔配置，使用EnableLog注解开启
4. 抽离权限模块，实现可插拔配置，使用EnableShiro注解开启

>更多特性持续更新 

## 技术选型

描述 | 框架 | 版本 
:---|:---|:---
核心框架 | Spring Boot | 2.1.7.RELEASE
持久层框架 | MyBatis-Plus | 3.3.0
连接池 | HikariCP | 3.2.0
SQL分析打印 | P6spy | 3.8.5
权限框架 | Shiro | 1.4.1
身份认证 | JWT | 3.3.0
数据校验 | HibernateValidator | 6.0.17.Final
工具包| HuTool | 4.5.16
接口文档 | Swagger | 2.9.2
接口文档UI | SwaggerBootstrapUI | 1.9.3
前端框架 | 待完善 | 1.0.0 

## 导入项目

**环境准备：**
1. JDK1.8+
2. MySQL5.6+
3. Maven3.6+

**部署：**
1. 克隆到本地：https://gitee.com/lanjerry/lanjerry-framework.git
2. 使用 IDEA 选择 Open 导入项目；
2. 导入数据库到MySQL中，sql文件位于根目录的sql文件夹里面；
3. 确认application-dev.properties 配置是否正确；
4. 启动项目，浏览器访问 [http://localhost:8088/](http://localhost:8088/) 

> 后端已经配置了允许跨域访问，无跨域问题。

## 项目结构

```
|-lanjerry-framework
|   |-pom.xml                                  // 管理jar包版本信息
|   |-lanjerry-admin                           // admin模块
|   |    |-config 
|   |    |    |-global
|   |    |    |    |-AsyncConfig.java          // 异步线程池配置类
|   |    |    |    |-CorsConfig.java           // 跨域支持配置类
|   |    |    |-mybatis
|   |    |    |    |-MybatisPlusConfig.java    // Mybatis-Plus配置类
|   |    |    |-redis
|   |    |    |    |-RedisConfig.java          // Redis配置类
|   |    |        
|   |    |-controller                          // 控制层
|   |    |-handler
|   |    |    |-GlobalExceptionHandler.java    // 全局异常处理类
|   |    |    |-GlobalExceptionHandler.java    // Mybatis-Plus自动填充处理类
|   |    |-listen
|   |    |   |-SysLogListener.java             // 异步监听系统日志事件
|   |    |-dto                                 // 业务层数据传输
|   |    |-vo                                  // 视图层数据传输
|   |-lanjerry-common 
|   |    |-lanjerry-common-auth                // 权限模块
|   |    |-lanjerry-common-core                // 公共模块
|   |    |-lanjerry-common-log                 // 日志模块
|   |    |-lanjerry-common-redis               // redis模块
```