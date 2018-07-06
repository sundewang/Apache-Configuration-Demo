package xyz.sun;

import java.lang.reflect.Field;

/**
 * @author sundw
 * @date 2018/7/6
 */
public class People {

    private String name;
    private static String age;

    public String sex;

    public People() {
    }

    public People(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        Field[] declaredFields = People.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            System.out.println(declaredField);
            System.out.println(declaredField.getName());
        }
    }
}
