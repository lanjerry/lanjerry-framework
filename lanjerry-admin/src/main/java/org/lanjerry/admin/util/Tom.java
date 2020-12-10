package org.lanjerry.admin.util;

import java.util.function.Predicate;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lanjerry
 * @since 2020-11-18
 */
public class Tom {

    public static void tomSay(Person person) {
        person.say("tom say3");
        //System.out.println(say);
    }

    public static void main(String[] args) {
//        Person tom = () -> System.out.println("tom say1");
//        tom.say();

//        Person tom = (msg) -> System.out.println(msg);
//        tom.say("tom say2");

        Tom.tomSay((msg) -> {
            String result = msg + ":hello1";
            System.out.println(result);
        });

        Tom.tomSay(new Person() {
            @Override
            public void say(String msg) {
                String result = msg + ":hello2";
                System.out.println(result);
            }
        });

//        Jerry jerry1 = new Jerry("zhangsan", 14);
//        Jerry jerry2 = new Jerry("lisi", 20);
//        Jerry jerry3 = new Jerry("wangwu", 5);
//        List<Jerry> jerries = Arrays.asList(jerry1, jerry2, jerry3);
//        List<String> collect = jerries.stream().filter(Jerry.ageThan15).map(j -> j.getName()).collect(Collectors.toList());
//        collect.forEach(System.out::println);

//        List<String> words = Arrays.asList("hello", "world");
//        words.stream().map(w -> w.split("")).forEach(System.out::println);
//        System.out.println("----------------------------------------------");
//
//        words.stream().map(w -> w.split("")).collect(Collectors.toList()).forEach(s-> Arrays.stream(s).forEach(System.out::println));
//        System.out.println("----------------------------------------------");
//
//        words.stream().flatMap(w -> Arrays.stream(w.split(""))).forEach(System.out::println);

        //words.parallelStream().filter()
//        words.stream().parallel().filter()
    }

    static class Jerry {

        Jerry(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        static Predicate<Jerry> ageThan10 = jerry -> jerry.getAge() > 10;

        static Predicate<Jerry> ageThan15 = new Predicate<Jerry>() {
            @Override
            public boolean test(Jerry jerry) {
                return jerry.getAge() > 15;
            }
        };

        public String name;

        public Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
