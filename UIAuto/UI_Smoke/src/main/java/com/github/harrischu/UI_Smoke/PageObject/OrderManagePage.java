package com.github.harrischu.UI_Smoke.PageObject;

import com.github.harrischu.webAuto.core.Assertion;
import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.ReportEvent;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.util.PropertyUtil;
import com.github.harrischu.webAuto.repository.*;

/**
 * Created by Harris on 2015/2/10.
 */
public class OrderManagePage extends PageObject {
    public IFrame FirstFrame;
    public IFrame SecondFrame;
    public Record record;

    public Edit InvestID;   //投资编号
    public Button BatchOrderBtn;  //批量订购
    public Button QueryBtn;       //查询按钮

    public Button DialogSubmit;


    public PropertyUtil propertyUtil;

    public OrderManagePage(WebDriverDecorator webDriverDecorator){
        driver = webDriverDecorator;
        propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/OrderManage.properties");
        reflect(this, OrderManagePage.class, propertyUtil);
        record = new Record(propertyUtil.getPropertyValue("record"));
        DialogSubmit = new Button(propertyUtil.getPropertyValueAsUTF8("DialogSubmit"));
    }


    public class Record extends PageObject{
        public Edit CheckboxItem;
        public Link OrderExec;      //订购
        public String xpath;

        public Record(String xpath){
            this.xpath = xpath;
            CheckboxItem = new Edit(xpath.concat(OrderManagePage.this
                    .propertyUtil.getPropertyValue("record.CheckboxItem")));

            OrderExec = new Link(xpath.concat(OrderManagePage.this
                    .propertyUtil.getPropertyValue("record.OrderExec")));
        }
    }



    public void BatchOrderExec(String InvestNO){
        InvestID.setValue(InvestNO);
        QueryBtn.click();
        if(driver.findElements(record.xpath).size()==0){
            driver.saveScreenShot();
            ReportEvent.fail("查询不到这条记录");
        }

        record.CheckboxItem.click();
        BatchOrderBtn.click();
        driver.saveScreenShot("批量订购");
        DialogSubmit.click();
    }
}
