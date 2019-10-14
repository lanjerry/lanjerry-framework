package org.lanjerry.admin.synchronize;

import java.util.ArrayList;
import java.util.List;

import org.lanjerry.common.core.bean.ApiResult;

public class TestSynchronized {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(ApiResult::success);
        Thread thread2 = new Thread(ApiResult::success);
        // 并发执行两个线程，如果thread1.join()加在了thread1.start()后面，则表示线程依次执行
        thread1.start();
        thread2.start();
        // 主线程等待子线程执行完
        thread1.join();
        thread2.join();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void main1(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(ApiResult::success);
            thread.start();
            System.out.println(i);
            threadList.add(thread);

        }
        while (threadList.size() > 0) {
            Thread child = threadList.remove(0);
            child.join();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
