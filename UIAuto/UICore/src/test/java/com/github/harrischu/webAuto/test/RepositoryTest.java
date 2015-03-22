package com.github.harrischu.webAuto.test;

import com.github.harrischu.webAuto.repository.Edit;
import com.github.harrischu.webAuto.core.TestCase;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.core.WebDriverFactory;
import com.github.harrischu.webAuto.repository.Link;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

/**
 * Created by Harris on 2014/12/16.
 */
public class RepositoryTest extends TestCase{
    WebDriverDecorator webDriverDecorator;
    private Logger logger = Logger.getLogger(BaseElementTest.class);

    @BeforeClass
    public void init(){
        webDriverDecorator = new WebDriverDecorator(WebDriverFactory.DriverType.chrome);
    }

    @Test
    public void TestLink(){
        WebDriver driver = webDriverDecorator.getDriver();

        driver.get("http://www.douban.com");
        Link link = new Link("//*[@id=\"anony-nav\"]/div[1]/ul/li[2]/a");
        logger.info(link.getText());
        link.click();
        logger.info(driver.getWindowHandles().size());
    }

//    @AfterTest
//    public void afterTest(){
//        try{
//            Driver.getDriver().quit();
//        }catch (Exception e){
//        }
//    }

    @Test(groups = "Test")
    public void ZDLogin(){
        WebDriver driver = webDriverDecorator.getDriver();
        driver.get("http://test.ezendai.com:7081/zdsys");
        Edit edit = new Edit("//*[@id=\"usercode\"]");
        edit.setValue("admin");
        webDriverDecorator.wait(3);
    }

    @Test
    public void TestUTF(){
        WebDriver driver = webDriverDecorator.getDriver();
        driver.get("http://www.douban.com");
        Link link = new Link("//a[contains(text(), '豆瓣读书')]");
        logger.info("\nxpath is " + link.xpath);
        logger.info(link.existed());
    }

//    @AfterGroups(groups = "Test")
    @AfterClass
    @AfterTest
    public void AfterClass(){
        tearDown(webDriverDecorator);
    }

}
