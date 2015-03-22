package com.github.harrischu.webAuto.core;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Created by Harris on 2015/1/16.
 */
public class Assertion extends Assert {
    private static Logger logger = Logger.getLogger(Assertion.class);

    /**
     *检查指定xpath的页面元素是否存在
     *默认，在检查时，无论是否成功，都会截图
     * @param driver
     * @param xpath
     * @param message
     */
    public static void assertXpathExist(WebDriverDecorator driver, String xpath, String message){
        assertXpathExist(driver, xpath, message, true);
    }

    /**
     *
     * @param driver
     * @param xpath
     * @param message
     * @param capture
     */
    public static void assertXpathExist(WebDriverDecorator driver, String xpath, String message,
                                        boolean capture){
        boolean result = driver.VerifyExisted(xpath);
        if(capture){
            driver.saveScreenShot();
        }
        if(result){
            logger.info("<检查通过> " + message);
            Reporter.log("<检查通过> " + message);
        }else{
            logger.info("<检查通过> " + message);
            Reporter.log("<检查失败> " + message);
        }
    }

    public static void assertTrue(boolean condition, String message){
        logger.info(message);
        Reporter.log(message);
        Assert.assertTrue(condition);
    }
}
