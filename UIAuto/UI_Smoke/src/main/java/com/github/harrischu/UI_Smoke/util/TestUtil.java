package com.github.harrischu.UI_Smoke.util;

import com.github.harrischu.webAuto.core.TestCase;
import com.github.harrischu.webAuto.util.TestCaseUtil;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 工具类，获取所有的testcase类，和类里面以Test开头的method
 * Created by Harris on 2015/3/20.
 */
public class TestUtil {
    public static Set<Class<?>> getTestName(){
        Set<Class<?>> testcaseList = new HashSet<Class<?>>();
        Set<Class<?>> clazzes = TestCaseUtil.getTestClass(
                "com.zendaimoney.autotest.fortunesmoke.testcase");
        for(Class<?> clazz:clazzes){
            if(TestCase.class.isAssignableFrom(clazz)){
                testcaseList.add(clazz);
            }
        }
        return testcaseList;
    }

    /**
     * 所有的测试类和方法，以Map<类,方法>放在集合里。
     * @return
     */
    public static Set<Map<String, String>> getTestMethod(){
        Set<Map<String, String>> result = new HashSet<>();
        for(Class clazz:getTestName()){
            for(Method method:clazz.getMethods()){
                String testname = method.getName();
                if(testname.startsWith("Test")){
                    Map<String, String> record = new HashMap<>();
                    record.put(clazz.getSimpleName(), testname);
                    result.add(record);
                }
            }
        }
        return result;
    }

    public static void main(String[] args){
        Set<Map<String, String>> methodList = TestUtil.getTestMethod();
        for(Map<String, String> methodGroup:methodList){
            String testcase = methodGroup.keySet().toArray()[0].toString();
            System.out.println(testcase + "\t" + methodGroup.get(testcase));
        }
    }
}
