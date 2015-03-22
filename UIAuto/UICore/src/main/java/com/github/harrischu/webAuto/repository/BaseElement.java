package com.github.harrischu.webAuto.repository;

import com.github.harrischu.webAuto.core.WebDriverDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.NoSuchElementException;

import java.lang.reflect.Constructor;


/**
 * Created by Harris on 2014/12/16.
 */
public class BaseElement{
    protected Logger logger = Logger.getLogger(getClass());
    protected WebDriverDecorator driver = WebDriverDecorator.getInstance();
    public String xpath;
    protected WebElement instance;
    public enum EventType{
        mouseover,
        mouseout
    }

    public BaseElement(){
    }

    public BaseElement(String xpath){
        this.xpath = xpath;
    }

    protected WebElement getInstance(String xpath) throws NoSuchElementException{
        try {
            return driver.findElement(By.xpath(xpath));
        }catch (NoSuchElementException e){
            logger.info("Cannot find the instance for <" + xpath + ">");
            throw e;
        }catch (Exception e){
            logger.info("Occur an error for <" + xpath + ">");
            return null;
        }
    }

    protected boolean existed(String xpath){
        logger.info("检查元素是否存在，xpath: <" + xpath + ">");
        return driver.VerifyExisted(xpath);
    }


    protected boolean existed(String xpath, int second){
        logger.info("检查元素是否存在，xpath: <" + xpath + ">");
        return driver.VerifyExisted(xpath, second);
    }

    protected String getValue(String xpath){
        logger.info("获取页面元素的值，xpath为: <" + xpath + ">");
        return getInstance(xpath).getText();
    }

    /**
     * 主要用于Link对象，预先设置一个主xpath，然后根据contains来定位
     * 如： //ul/li/a[contains(.,"确认")]
     *     //ul/li/a[contains(.,"取消")]
     * @param item
     * @param <T>
     * @return
     */
    protected  <T extends BaseElement> T get(Class clazz, String xpath, String item){
        logger.info(xpath);
        xpath = xpath.concat("[contains(.,\"" + item + "\")]");
        logger.info(xpath);
        try{
            Constructor con = clazz.getConstructor(String.class);
            return (T) con.newInstance(xpath);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
