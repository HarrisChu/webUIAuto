package com.github.harrischu.webAuto.util;

import com.github.harrischu.webAuto.core.TestCase;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 通过反射获取所有的Test类，和类里面，所以的Test
 * Created by Harris on 2015/3/20.
 */
public class TestCaseUtil {
    public static Logger logger = Logger.getLogger(TestCaseUtil.class);

    public TestCaseUtil(){

    }

    /**
     * 获取指定包中的所有class
     * @param pak
     * @return
     */
    public static Set<Class<?>> getTestClass(String pak) {
        Set<Class<?>> clazzes = new HashSet<Class<?>>();
        // 获取包的名字 并进行替换
        String packageName = pak.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().
                    getResources(packageName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                // 得到协议的名称
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                putClass(pak, filePath, clazzes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clazzes;
    }

    /**
     * 将所有的class放在指定Set中
     * @param packageName
     * @param packagePath
     * @param clazzes
     */
    private static void putClass(String packageName, String packagePath,
                                 Set<Class<?>> clazzes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.error("文件不存在");
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            //如果是java类文件 去掉后面的.class 只留下类名
            String className = file.getName().substring(0, file.getName().length() - 6);
            try {
                clazzes.add(Thread.currentThread().getContextClassLoader()
                        .loadClass(packageName + '.' + className));
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static Set<Class<?>> getTestName(String packageStr){
        Set<Class<?>> testcaseList = new HashSet<Class<?>>();
        Set<Class<?>> clazzes = TestCaseUtil.getTestClass(packageStr);
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
    public static Set<Map<String, String>> getTestMethod(String packageStr){
        Set<Map<String, String>> result = new HashSet<>();
        for(Class clazz:getTestName(packageStr)){
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

    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args){
        Set<Class<?>> classList = TestCaseUtil.getTestClass(
                "com.github.harrischu.webAuto.util");
        for(Class<?> clazz:classList){
            System.out.println(clazz.getSimpleName());
        }

        Set<Map<String, String>> testMethodList = TestCaseUtil.getTestMethod("com.zendaimoney.autotest.UItest.util");
        for(Map <String, String> method:testMethodList){
            System.out.println(method.keySet().toArray()[0] + "\t" +
                    method.values().toArray()[0]);
        }
    }
}
