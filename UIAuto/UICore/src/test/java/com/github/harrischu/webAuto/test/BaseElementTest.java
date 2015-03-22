package com.github.harrischu.webAuto.test;

import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.repository.Edit;
import com.github.harrischu.webAuto.core.TestCase;
import com.github.harrischu.webAuto.core.WebDriverFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

;

/**
 * Created by Harris on 2014/12/16.
 */
public class BaseElementTest extends TestCase {
    private WebDriverDecorator webDriverDecorator;
    private Logger logger = Logger.getLogger(BaseElementTest.class);

    @Test(groups = "Test")
    public void BaseElement(){
        webDriverDecorator = new WebDriverDecorator(WebDriverFactory.DriverType.chrome);
        WebDriver driver = webDriverDecorator.getDriver();

        driver.get("http://www.douban.com");
        Edit testElement = new Edit("//*[@id=\"form_email\"]");
        testElement.setValue("123");
        logger.info(testElement.getValue());
        driver.close();
    }

    @AfterTest
    public void afterTest(){
        try{
            webDriverDecorator.getDriver().close();
        }catch (Exception e){

        }
    }

    @AfterGroups(groups = "Test")
    public void AfterClass(){
        try{
            webDriverDecorator.getDriver().quit();
        }catch (Exception e){

        }
    }
}
