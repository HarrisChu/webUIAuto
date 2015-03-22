package com.github.harrischu.webAuto.core;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 * Created by Harris on 2014/12/16.
 */
public class TestCase extends Assert{
    protected Logger logger = Logger.getLogger(getClass());

    protected void tearDown(WebDriverDecorator webDriverDecorator) {
        try {
            webDriverDecorator.close();
            webDriverDecorator.quit();
        } catch (Exception e) {
            //TODO
        }
    }
}