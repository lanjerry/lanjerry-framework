package org.lanjerry.admin.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lanjerry.admin.AdminApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class TestAsync {

    @Autowired
    private TestAsyncBean testAsyncBean;

    @Test
    public void testSayHello1() throws InterruptedException {
        System.out.println("你不爱我了么?----" + Thread.currentThread().getName());
        testAsyncBean.sayHello1();
        System.out.println("回的这么慢, 你肯定不爱我了, 我们还是分手吧。。。----" + Thread.currentThread().getName());
        Thread.sleep(3 * 1000);// 不让主进程过早结束
    }

    @Test
    public void testSayHello2() throws InterruptedException{
        System.out.println("你不爱我了么?----" + Thread.currentThread().getName());
        testAsyncBean.sayHello2();
        System.out.println("你竟无话可说, 我们分手吧。。。----" + Thread.currentThread().getName());
        Thread.sleep(10 * 1000);// 不让主进程过早结束
    }

    @Test
    public void testSayHello3() throws InterruptedException, ExecutionException {
        Future<String> future = null;
        System.out.println("你不爱我了么?----" + Thread.currentThread().getName());
        future = testAsyncBean.sayHello3();
        System.out.println("你竟无话可说, 我们分手吧。。。----" + Thread.currentThread().getName());
        Thread.sleep(10 * 1000);// 不让主进程过早结束
        System.out.println(future.get());
    }
}
