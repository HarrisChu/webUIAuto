package com.github.harrischu.demo.testcase;

import com.github.harrischu.demo.PageObject.BaiduPage;
import com.github.harrischu.webAuto.core.TestCase;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * Created by Harris on 2015/3/5.
 */
public class TestBaidu extends TestCase {
    private Logger logger = Logger.getLogger(TestBaidu.class);
    private WebDriverDecorator driver = WebDriverDecorator.getInstance();
    public BaiduPage baiduPage;

    public TestBaidu(){
        baiduPage = new BaiduPage(driver);
    }


    @Test
    public void Test_01_QueryTest(){
        driver.get(baiduPage.URL);
        baiduPage.QueryInput.setValue("git os china");
        baiduPage.Submit.click();
        driver.saveScreenShot();
    }

    @AfterClass
    public void finish(){
        super.tearDown(driver);
    }
}
