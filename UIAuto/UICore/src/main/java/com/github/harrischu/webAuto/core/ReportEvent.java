package com.github.harrischu.webAuto.core;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Created by Harris on 2015/1/20.
 * 将reporter进行封装，如果以后需要，可以添加css
 */
public class ReportEvent {
    private static Logger logger = Logger.getLogger(ReportEvent.class);
    private enum EventType{
        FAIL,
        PASS,
        DONE
    }
    public static void fail(String message){
        WriteReport(message, EventType.FAIL);
        Assert.fail(message);
    }

    public static void pass(String message){
        WriteReport(message, EventType.PASS);
    }
    public static void done(String message){
        WriteReport(message, EventType.DONE);
    }

    private static void WriteReport(String message, EventType type){
        switch (type){
            case DONE:{
            }
            default:{
                logger.info(message);
                Reporter.log(message);
            }
        }

    }

    /**
     * 格式化并统一report中的message，
     * @param beginStr
     * 起始的message
     * @param expectedStr
     * 预期的值
     * @param actualStr
     * 实际的值
     * @return
     */
    public static String genMessage(String beginStr, String expectedStr, String actualStr){
        String result = "";
        result = result.concat(beginStr);
        result = result.concat(" 期望结果为： <" + expectedStr + ">" );
        result = result.concat(" 实际结果为： <" + actualStr + ">" );
        return  result;
    }
}
