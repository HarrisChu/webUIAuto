package com.github.harrischu.webAuto.repository;

import org.testng.Assert;

/**
 * Created by Harris on 2014/12/17.
 */
public class Button extends BaseElement{
    public String xpath;

    public Button(String xpath){
        this.xpath = xpath;
    }

    public boolean existed(){
        return existed(xpath);
    }

    public boolean existed(int second){
        return existed(xpath, second);
    }

    public void click(){
        this.instance = getInstance(xpath);
        logger.info("Click the button, xpath is <" + xpath + ">");
        if(this.instance == null){
            String message = "找不到此元素， xpath: <" + xpath + ">";
            logger.error(message);
            Assert.fail(message);
        }
        this.instance.click();
    }

}
