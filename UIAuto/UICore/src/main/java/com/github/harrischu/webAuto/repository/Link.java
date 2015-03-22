package com.github.harrischu.webAuto.repository;

/**
 * Created by Harris on 2014/12/16.
 */
public class Link extends BaseElement {
    public String xpath;

    public Link(String xpath){
        this.xpath = xpath;
    }

    public boolean existed(){
        return existed(xpath);
    }

    public boolean existed(int second){
        return existed(xpath, second);
    }

    public void click(){
        this.instance = driver.waitForElementClickable(xpath);
        this.instance.click();
    }

    public String getText(){
        this.instance = getInstance(xpath);
        return this.instance.getText();
    }

    public Link get(String item){
        return super.get(Link.class, xpath, item);
    }
}
