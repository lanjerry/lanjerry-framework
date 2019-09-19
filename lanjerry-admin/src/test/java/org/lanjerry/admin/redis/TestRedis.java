package org.lanjerry.admin.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lanjerry.admin.AdminApplication;
import org.lanjerry.admin.util.RedisUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.hutool.json.JSONUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class TestRedis {

    @Test
    public void testString() {
        RedisUtil.setFromString("test:test01", "118");
        System.out.println("test01的值：" + RedisUtil.get("test:test01"));
        RedisUtil.setFromString("test:test02", "测试test022");
        System.out.println("test02的值：" + RedisUtil.get("test:test02"));
    }

    @Test
    public void testIncr() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(RedisUtil.incr("test:test03", 2));
        }
    }

    @Test
    public void testDecr() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(RedisUtil.decr("test:test04", 2));
        }
    }

    @Test
    public void testListByRight() {
        for (int i = 1; i <= 10; i++) {
            RedisUtil.setFromListByRight("test:test05", String.format("第%s个值：%s", i, i));
        }
        List<String> list = (List<String>) RedisUtil.getList("test:test05");
        System.out.println(list);
    }

    @Test
    public void testBean() {
        TestRedisBean bean = TestRedisBean.builder().id(1).name("小明").build();
        RedisUtil.setFromString("test:test06", JSONUtil.toJsonStr(bean));
        TestRedisBean result = JSONUtil.toBean(RedisUtil.get("test:test06"), TestRedisBean.class);
        System.out.println(result);
        //JSONUtil.formatJsonStr(RedisUtil.get("test:test06"));
    }

    @Test
    public void testList() {
        List<TestRedisBean> list = new ArrayList<>();
        list.add(TestRedisBean.builder().id(1).name("小明").build());
        list.add(TestRedisBean.builder().id(2).name("小暗").build());
        RedisUtil.setFromString("test:test07", JSONUtil.toJsonStr(list));
        List<TestRedisBean> result = JSONUtil.toList(JSONUtil.parseArray(RedisUtil.get("test:test07")), TestRedisBean.class);
        System.out.println(result);
    }

    @Test
    public void testRemove() {
        Set<String> keys = RedisUtil.keys("test:*");
        RedisUtil.remove(new ArrayList<>(keys));
    }

//    @Test
//    public void testRedis(){
//        nbRedis.setFromString("hello","liangzai123");
//    }
}
