package com.github.harrischu.UI_Smoke.util;

import com.github.harrischu.webAuto.core.WebDriverDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * Created by Harris on 2015/2/2.
 */
public class PageUtil {
    private static Logger logger = Logger.getLogger(PageUtil.class);
    private static WebDriverDecorator driver = WebDriverDecorator.getInstance();

    /**
     * fortune中，页面是多层嵌套，需要两次switchto
     * @param firstXpath
     * @param secondXpath
     */
    public static void switchTo(String firstXpath, String secondXpath){
        //第一层frame
        logger.info("Switch to the first frame: " + firstXpath );
        driver.switchToFrame(firstXpath);
        //第二层frame
        logger.info("Switch to the second frame: " + secondXpath );
        WebElement frame = driver.findElementByXpath(secondXpath);
        driver.switchTo().frame(frame);
    }

    /**
     * fortune中，main frame的id是固定的，可以将xpath写死
     * 方法适用于主页面
     */
    public static void switchToMain(){
        String FirstFrame = "//*[@id=\"mainFrame\"]";
        String SecondFrame="//*[@id=\"ifmRight\"]";
        switchTo(FirstFrame, SecondFrame);
    }
}
