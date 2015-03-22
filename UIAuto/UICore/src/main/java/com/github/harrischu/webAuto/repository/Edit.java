package com.github.harrischu.webAuto.repository;

/**
 * Created by Harris on 2014/12/16.
 */
public class Edit extends BaseElement {
    public String xpath;
    public Edit(String xpath){
        this.xpath = xpath;
    }

    public void setValue(String value){
        this.instance = getInstance(xpath);
        this.instance.sendKeys(value);
    }

    /**
     * Input获取页面的值，和其他标签不一样
     */
    public String getValue(){
        logger.info("获取页面元素的值，xpath为: <" + xpath + ">");
        return getInstance(xpath).getAttribute("value");
    }

    public void click(){
        this.instance = driver.waitForElementClickable(xpath);
        this.instance.click();
    }

    public boolean existed(){
        return existed(xpath);
    }

    public boolean existed(int second){
        return existed(xpath, second);
    }
}
