package com.github.harrischu.UI_Smoke.PageObject;

import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.repository.BaseElement;
import com.github.harrischu.webAuto.repository.Link;
import com.github.harrischu.webAuto.util.PropertyUtil;

/**
 * Created by Harris on 2015/1/23.
 */
public class CRM_MenuPage extends PageObject {
    public Link BusiManage;  //业务管理
    public Link ApplyPro;    //产品申请

    private WebDriverDecorator driver;

    public CRM_MenuPage(WebDriverDecorator webDriverDecorator){
        driver = webDriverDecorator;
        PropertyUtil propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/CRM_Memu.properties");

        BusiManage = new Link(propertyUtil.getPropertyValueAsUTF8("BusiMange"));
        ApplyPro = new Link(propertyUtil.getPropertyValueAsUTF8("ApplyPro"));

    }
}
