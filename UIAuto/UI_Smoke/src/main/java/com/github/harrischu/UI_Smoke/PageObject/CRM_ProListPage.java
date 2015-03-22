package com.github.harrischu.UI_Smoke.PageObject;

import com.github.harrischu.webAuto.core.Assertion;
import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.ReportEvent;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.repository.BaseElement;
import com.github.harrischu.webAuto.repository.Element;
import com.github.harrischu.webAuto.repository.IFrame;
import com.github.harrischu.webAuto.repository.Link;
import com.github.harrischu.webAuto.util.PropertyUtil;

/**
 * Created by Harris on 2015/1/26.
 */
public class CRM_ProListPage extends PageObject {
    public IFrame iFrame;
    public Link ApplyButton;    //申请按钮
    public Link FortuneApply;   //理财申请
    private PropertyUtil propertyUtil;
    public Record record;

    private WebDriverDecorator driver;

    public CRM_ProListPage(WebDriverDecorator webDriverDecorator){
        propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/CRM_ProList.properties");
        driver = webDriverDecorator;
        iFrame = new IFrame(propertyUtil.getPropertyValue("iFrame"));
        ApplyButton = new Link(propertyUtil.getPropertyValueAsUTF8("ApplyButton"));
        FortuneApply = new Link(propertyUtil.getPropertyValueAsUTF8("FortuneApply"));
        record = this.new Record(propertyUtil.getPropertyValue("record"));
    }

    /**
     * 产品列表中的单条数据
     */
    public class Record extends BaseElement{
        public Element ContractNo;      //合同编号
        public Link Show;               //查看
        public Link Review;       //质检
        public Link CustomerName;       //客户姓名
        public String xpath;
        public Element State;           //处理状态
        public Element BusiState;       //申请处理结果

        public Record(String xpath){
            this.xpath = xpath;
            ContractNo = new Element(xpath.concat(CRM_ProListPage.this.propertyUtil.
                    getPropertyValue("record.ContractNo")));
            Show = new Link(xpath.concat(CRM_ProListPage.this.propertyUtil.
                    getPropertyValueAsUTF8("record.Show")));
            Review = new Link(xpath.concat(CRM_ProListPage.this.propertyUtil.
                    getPropertyValueAsUTF8("record.Review")));
            CustomerName = new Link(xpath.concat(CRM_ProListPage.this.propertyUtil.
                    getPropertyValue("record.CustomerName")));
            State = new Element(xpath.concat(CRM_ProListPage.this.propertyUtil.
                    getPropertyValue("record.State")));
            BusiState = new Element(xpath.concat(CRM_ProListPage.this.propertyUtil.
                    getPropertyValue("record.BusiState")));
        }
    }


    public void verifyRecordState(String contract, String state, String busiState, boolean reviewLink){
        driver.switchToFrame(iFrame.xpath);
        driver.saveScreenShot("检查单子状态， 合同编号为： " + contract + "");
        String actualState = record.State.getValue();
        String actualbusiState = record.BusiState.getValue();

        Assertion.assertTrue(actualState.equals(state),
                ReportEvent.genMessage("检查处理状态", state, actualState));
        Assertion.assertTrue(actualbusiState.equals(busiState),
                ReportEvent.genMessage("检查申请处理结果", busiState, actualbusiState));
//        boolean actualreviewLink = record.Review.existed(3);
//        Assertion.assertTrue(actualreviewLink==reviewLink, ReportEvent.genMessage("检查质检链接是否存在",
//                actualreviewLink?"存在":"不存在", reviewLink?"存在":"不存在"));
    }
}
