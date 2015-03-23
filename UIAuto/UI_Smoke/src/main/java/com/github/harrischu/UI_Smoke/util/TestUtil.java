package  com.github.harrischu.UI_Smoke.util;

import com.github.harrischu.webAuto.util.TestCaseUtil;

import java.util.*;

/**
 * 工具类，获取所有的testcase类，和类里面以Test开头的method
 * Created by Harris on 2015/3/20.
 */
public class TestUtil {
    public static void main(String[] args){
        String packageStr = "com.github.harrischu.UI_Smoke.testcase";
        Set<Map<String, String>> methodList = TestCaseUtil.getTestMethod(packageStr);
        for(Map<String, String> methodGroup:methodList){
            String testcase = methodGroup.keySet().toArray()[0].toString();
            System.out.println(testcase + "\t" + methodGroup.get(testcase));
        }
    }

}
