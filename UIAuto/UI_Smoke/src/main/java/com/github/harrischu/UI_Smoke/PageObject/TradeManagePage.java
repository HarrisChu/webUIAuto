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
public class TradeManagePage extends PageObject {
    public Record record;

    public Edit InvestID;        //投资编号
    public Button BatchPayBtn;  //批量付款
    public Button QueryBtn;       //查询按钮
    public WebList TradeStatus;   //交易状态


    public Button SubmitBtn;       //确认按钮
    public Button DialogSubmit;    //弹出框，确认按钮
    public Element DialogResult;

    public PropertyUtil propertyUtil;

    public TradeManagePage(WebDriverDecorator webDriverDecorator){
        driver = webDriverDecorator;
        propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/TradeManage.properties");
        reflect(this, TradeManagePage.class, propertyUtil);
        record = new Record(propertyUtil.getPropertyValue("record"));
        DialogSubmit = new Button(propertyUtil.getPropertyValueAsUTF8("DialogSubmit"));
        DialogResult = new Element(propertyUtil.getPropertyValueAsUTF8("DialogResult"));
    }

    public class Record extends PageObject {
        public Edit CheckboxItem;
        public Link Payment;      //付款
        public Link Collection;      //收款
        public String xpath;

        public Record(String xpath){
            this.xpath = xpath;
            CheckboxItem = new Edit(xpath.concat(TradeManagePage.this
                    .propertyUtil.getPropertyValue("record.CheckboxItem")));

            Payment = new Link(xpath.concat(TradeManagePage.this
                    .propertyUtil.getPropertyValue("record.Payment")));
            Collection = new Link(xpath.concat(TradeManagePage.this
                    .propertyUtil.getPropertyValue("record.Collection")));
        }

    }

    public void BatchPay(String InvestNO){
        InvestID.setValue(InvestNO);
        QueryBtn.click();

        if(driver.findElements(record.xpath).size()==0){
            driver.saveScreenShot();
            ReportEvent.fail("查询不到这条记录");
        }
        record.CheckboxItem.click();
        BatchPayBtn.click();
        driver.saveScreenShot("批量付款");
        DialogSubmit.click();
        Assertion.assertXpathExist(driver, DialogResult.xpath, "检查结果");
    }

    public void Collection(String InvestNO){
        InvestID.setValue(InvestNO);
        TradeStatus.select("请选择");
        QueryBtn.click();
        if(driver.findElements(record.xpath).size()==0){
            driver.saveScreenShot();
            ReportEvent.fail("查询不到这条记录");
        }
        record.Collection.click();


    }

    /**
     * 当有多条记录时，可以通过反射，动态改变xpath
     */
//    public void BatchPay(String InvestNO){
//        InvestID.setValue(InvestNO);
//        QueryBtn.click();
//        super.runSubItem(this, record, Record.class, InvestNO, new ISubItem() {
//            @Override
//            public void workflow(Object object) {
//                Record newrecord = (Record)object;
//                newrecord.CheckboxItem.click();
//            }
//        });
//        BatchPayBtn.click();
//        driver.saveScreenShot("批量付款");
//        DialogSubmit.click();
//        Assertion.assertXpathExist(driver, DialogResult.xpath, "检查结果");
//    }
}
