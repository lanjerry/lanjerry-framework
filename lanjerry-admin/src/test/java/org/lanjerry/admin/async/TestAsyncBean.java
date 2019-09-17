package org.lanjerry.admin.async;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class TestAsyncBean {

    public void sayHello1() throws InterruptedException {
        Thread.sleep(5 * 1000);//网络连接中 。。。消息发送中。。。
        System.out.println("我爱你啊!----" + Thread.currentThread().getName());
    }

    @Async
    public void sayHello2() throws InterruptedException {
        Thread.sleep(5 * 1000);//网络连接中 。。。消息发送中。。。
        System.out.println("我爱你啊!----" + Thread.currentThread().getName());
    }

    @Async
    public Future<String> sayHello3() throws InterruptedException {
        int thinking = 5;
        Thread.sleep(thinking * 1000);//网络连接中 。。。消息发送中。。。
        return new AsyncResult<String>("我爱你啊!发送消息用了" + thinking + "秒----" + Thread.currentThread().getName());
    }
}
