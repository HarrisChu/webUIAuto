package com.github.harrischu.webAuto.repository;

/**
 * Created by Harris on 2015/1/22.
 */
public class Element extends BaseElement{
    public String xpath;

    public Element(String xpath){
        this.xpath = xpath;
    }

    public boolean existed(){
        return existed(xpath);
    }

    public boolean existed(int second){
        return existed(xpath, second);
    }

    public String getValue(){
        return getValue(xpath);
    }

}
