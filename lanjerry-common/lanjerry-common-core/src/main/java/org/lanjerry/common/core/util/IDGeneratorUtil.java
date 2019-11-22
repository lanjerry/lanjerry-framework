package org.lanjerry.common.core.util;

/**
 * 64位生成规则【首位是符号位，代表正副，所以实际有效是63位】：
 * <p>
 * 41位时间戳 |10位进程号 |12位计数器
 * <p>
 * 41位时间戳：
 * 注意：这里没有直接使用时间戳，因为直接使用的话只能用到2039-09-07 23:47:35
 * 这里采用（当前时间戳-初始时间戳）。最多这样可以使用69年【(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69】
 * 10位机器：最多1023台机器
 * 12位计数器：每毫秒最多生成4095条记录
 * <p>
 * 这里可以根据项目中实际情况，调整每个位置的长度，比如分布式集群机器数量比较少，可以缩减机器的位数，增加计数器位数
 * <p>
 * <p>
 * 使用注意事项：1、必须关闭时钟同步；2、currentTimeMillis一经定义，不可修改；3、并发量太高的时候，超过了4095，未做处理；4、最大不超过64位
 *<p>
 * 自测性能：一秒能有三四十万的数据产生。
 *<p>
 * 无锁没写出来......
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public class IDGeneratorUtil {
    /**
     * 初始时间戳：2017-1-1 0:0:0
     * 一经定义，不可修改
     */
    private static final long initTimeMillis = 1483200000929L;
    /**
     * 进程。
     * 这里也可以手动指定每台实例的ID号；或者通过ZK的临时递增节点自动获取。
     * 固定值
     */
    private static final int pid = 5;
    /**
     * 计数器
     * 需要保证线程安全
     */
    private static volatile long counter;
    private static volatile long currentTimeMillis = System.currentTimeMillis() - initTimeMillis;
    private static volatile long lastTimeMillis = currentTimeMillis;


    /**
     * 无锁模式暂时没有写出来
     *
     * @return
     */
    public static synchronized long nextId() {
        long series = counter++;
        if (series >= (1 << 12) - 1) {//单位毫秒内计时器满了，需要重新计时
            while (lastTimeMillis == currentTimeMillis) {//等待到下一秒
                currentTimeMillis = System.currentTimeMillis() - initTimeMillis;
            }

            lastTimeMillis = currentTimeMillis;
            counter = 0;
            series = counter++;
        }
        return (currentTimeMillis << 22) | (pid << 12) | series;
    }
}
