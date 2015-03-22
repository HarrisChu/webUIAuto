package com.github.harrischu.UI_Smoke.PageObject;

import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.util.PropertyUtil;
import com.github.harrischu.webAuto.repository.*;

/**
 * Created by Harris on 2015/1/28.
 */
public class CRM_ReviewPage extends PageObject {
    public IFrame iFrame;
    public WebList ReviewResult;            //评审结果
    public Button ReviewResultButton;
    public Edit Memo;                       //备注

    public Button SubmitBtn;
    public Button BackBtn;

    private WebDriverDecorator driver;

    public CRM_ReviewPage(WebDriverDecorator webDriverDecorator) {
        driver = webDriverDecorator;
        PropertyUtil propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/CRM_Review.properties");
        reflect(this, CRM_ReviewPage.class, propertyUtil);
        SubmitBtn = new Button(propertyUtil.getPropertyValueAsUTF8("SubmitBtn"));
        BackBtn = new Button(propertyUtil.getPropertyValueAsUTF8("SubmitBtn"));
    }

    public void inputReviewInfo(String reviewResult, String memo){
        driver.switchToFrame(iFrame.xpath);
        ReviewResultButton.click();
        ReviewResult.selectByXpath("质检通过");
        Memo.setValue("自动化测试");
    }
}
