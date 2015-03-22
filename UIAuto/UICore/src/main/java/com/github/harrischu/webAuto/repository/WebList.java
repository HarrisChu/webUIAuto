package com.github.harrischu.webAuto.repository;

import com.github.harrischu.webAuto.core.ReportEvent;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Harris on 2015/1/27.
 */
public class WebList extends BaseElement {
    public String xpath;

    public WebList(String xpath){
        this.xpath = xpath;
    }

    public boolean existed(){
        return existed(xpath);
    }

    public boolean existed(int second){
        return existed(xpath, second);
    }

    public void selectByXpath(String item){
        String tmpXpath = xpath + "[text()=\"" + item + "\"]";
        if(existed(tmpXpath)){
            driver.waitForElementClickable(tmpXpath).click();
        }else {
            logger.error("找不到所要选取的元素");
            ReportEvent.fail("找不到所要选取的元素");
        }
    }

    public void select(String item){
        Select sel = new Select(driver.findElementByXpath(this.xpath));
        sel.selectByVisibleText(item);
    }
}
