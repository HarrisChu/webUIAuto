package com.github.harrischu.webAuto.test;

import com.github.harrischu.webAuto.repository.BaseElement;
import com.github.harrischu.webAuto.repository.Link;

import java.lang.reflect.Constructor;

/**
 * Created by Harris on 2015/1/22.
 */
public class DriverDecoratorTest {
    public static void main(String[] args){
        DriverDecoratorTest driverDecoratorTest = new DriverDecoratorTest();
        Link t = driverDecoratorTest.test(Link.class, "//Test");
        System.out.println(t.xpath);

    }

    public <T extends BaseElement> T test(Class <T> clazz, String xpath){
        try {
            Constructor<T> constructor = clazz.getConstructor(String.class);
            return constructor.newInstance(xpath);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
