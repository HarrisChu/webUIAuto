package com.github.harrischu.UI_Smoke.PageObject;

import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.repository.IFrame;
import com.github.harrischu.webAuto.repository.Link;
import com.github.harrischu.webAuto.util.PropertyUtil;
import com.github.harrischu.webAuto.repository.*;

/**
 * Created by Harris on 2015/2/2.
 */
public class MenuPage extends PageObject {
    public IFrame iFrame;

    public Link Menu;   //一级菜单
    public Link MenuList;   //二级菜单

    public MenuPage(WebDriverDecorator webDriverDecorator) {
        driver = webDriverDecorator;
        PropertyUtil propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/Menu.properties");
        reflect(this, MenuPage.class, propertyUtil);
    }
}
