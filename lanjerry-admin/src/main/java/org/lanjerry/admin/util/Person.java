package org.lanjerry.admin.util;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lanjerry
 * @since 2020-11-18
 */
public interface Person {

    //void say();

    void say(String msg);

    static void eat(){
        System.out.println("eat");
    }

    default void play(){
        System.out.println("play");
    }
}
