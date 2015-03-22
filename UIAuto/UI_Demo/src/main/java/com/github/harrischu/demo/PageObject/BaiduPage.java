package com.github.harrischu.demo.PageObject;

import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.repository.Button;
import com.github.harrischu.webAuto.repository.Edit;
import com.github.harrischu.webAuto.util.PropertyUtil;

/**
 * Created by Harris on 2015/3/6.
 */
public class BaiduPage extends PageObject{
    public Edit QueryInput;
    public Button Submit;
    public String URL = "http://www.baidu.com";

    public BaiduPage(WebDriverDecorator webDriverDecorator){
        PropertyUtil propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/Baidu.properties");
        reflect(this, BaiduPage.class, propertyUtil);
    }
}
