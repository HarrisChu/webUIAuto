package com.github.harrischu.UI_Smoke.PageObject;

import com.github.harrischu.webAuto.core.Assertion;
import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.ReportEvent;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.util.PropertyUtil;
import com.github.harrischu.webAuto.repository.*;

import java.util.List;

/**
 * Created by Harris on 2015/2/2.
 */
public class InvestManagePage extends PageObject {
    public Edit InvestID;   //投资编号
    public WebList InvestStatus; //投资状态
    public IFrame FirstFrame;
    public IFrame SecondFrame;
    public Record record;
    public Button AutoMatchBtn;  //自动撮合按钮
    public Edit Edt_page;
    public Button Btn_go;
    public WebList DialogRepayDay;        //债权还款日
    public Button DialogSubmit;
    public Element DialogResult;
    public Element PageNo;

    public PropertyUtil propertyUtil;

    public InvestManagePage(WebDriverDecorator webDriverDecorator){
        driver = webDriverDecorator;
        propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/InvestManage.properties");
        reflect(this, InvestManagePage.class, propertyUtil);
        record = new Record(propertyUtil.getPropertyValue("record"));
        DialogSubmit = new Button(propertyUtil.getPropertyValueAsUTF8("DialogSubmit"));
        DialogResult = new Element(propertyUtil.getPropertyValueAsUTF8("DialogResult"));
    }

    public class Record extends PageObject{
        public Edit CheckboxItem;
        public Element InvestNO;

        public String xpath;

        public Record(String xpath){
            this.xpath = xpath;
            CheckboxItem = new Edit(xpath.concat(InvestManagePage.this
                    .propertyUtil.getPropertyValue("record.CheckboxItem")));
            InvestNO = new Element(xpath.concat(InvestManagePage.this
                    .propertyUtil.getPropertyValue("record.InvestNO")));
        }
    }

    public void AutoMacth(String contract, String day) {
        String temp = record.xpath;
        String newXpath = "[contains(.,\"" + contract + "\")]";
        record = new Record(record.xpath.concat(newXpath));

        //edit by liubx**********************************************************
        //获取页码
        //String pageno=PageNo.getValue();
        //System.out.println(pageno);
        //pageno=pageno.substring(pageno.length()-1,pageno.length());
        //int no=Integer.parseInt(pageno);
        //System.out.print(pageno);
        int i=2;
        while (!record.CheckboxItem.existed(5))
        {
            Edt_page.setValue(Integer.toString(i));
            Btn_go.click();
            i++;
        }
        //判断i不大于页码
        //************************************************************************
        record.CheckboxItem.click();
        driver.saveScreenShot("选取合同编号为： <b>" + contract + "</b> 的数据");
        AutoMatchBtn.click();
        DialogRepayDay.select(day);
        driver.saveScreenShot("选择还款日是： <b>" + day + "</b>");
        DialogSubmit.click();
        Assertion.assertXpathExist(driver, DialogResult.xpath, "检查结果");
        DialogSubmit.click();

        record = new Record(temp);
    }

    /**
     * 多个合同编号，同时自动撮合
     * @param contractList
     * @param day
     */
    public void AutoMulMacth(List<String> contractList, String day) {

    }

    public String getInvestNO(String contract){
        String temp = record.xpath;
        String newXpath = "[contains(.,\"" + contract + "\")]";
        record = new Record(record.xpath.concat(newXpath));
        return record.InvestNO.getValue();
    }
}
