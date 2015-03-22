package com.github.harrischu.webAuto.repository;

/**
 * Created by Harris on 2014/12/23.
 */
public class IFrame extends BaseElement  {
    public String xpath;

    public IFrame(String xpath){
        this.xpath = xpath;
    }

    public boolean existed(){
        return existed(xpath);
    }

    public boolean existed(int second){
        return existed(xpath, second);
    }

}
