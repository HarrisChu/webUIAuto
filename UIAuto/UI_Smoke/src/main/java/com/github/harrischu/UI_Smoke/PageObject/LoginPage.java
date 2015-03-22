package com.github.harrischu.UI_Smoke.PageObject;

import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.ReportEvent;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.util.PropertyUtil;
import org.apache.log4j.Logger;
import com.github.harrischu.webAuto.repository.*;

/**
 * Created by Harris on 2015/1/22.
 */
public class LoginPage extends PageObject{
    private Logger logger = Logger.getLogger(LoginPage.class);

    public Edit UserName;
    public Edit Password;
    public Link Login;
    public IFrame iFrame;
    public Button CRMLogoutBtn;
    public Button CRMLogoutSubmit;
    public Button FortuneLogoutBtn;

    private String CrmURL;
    private String FortuneURL;
    private WebDriverDecorator driver;

    //检验是否登陆CRM成功
    public Element VerifyCrmLogin;
    //检验是否登陆Fortune成功
    public Element VerifyFortuneLogin;

    public boolean IsFortuneLoggin;

    public LoginPage(WebDriverDecorator webDriverDecorator){
        driver = webDriverDecorator;
        PropertyUtil propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/LoginPage.properties");

        reflect(this, LoginPage.class, propertyUtil);
        Login = new Link(propertyUtil.getPropertyValueAsUTF8("Login"));
        VerifyCrmLogin = new Element(propertyUtil.getPropertyValueAsUTF8("VerifyCrmLogin"));
        VerifyFortuneLogin = new Element(propertyUtil.getPropertyValueAsUTF8("VerifyFortuneLogin"));
        CRMLogoutSubmit = new Button(propertyUtil.getPropertyValueAsUTF8("CRMLogoutSubmit"));
        FortuneLogoutBtn = new Button(propertyUtil.getPropertyValueAsUTF8("FortuneLogoutBtn"));

        propertyUtil.loadPropertiesFromFile("/UICore.properties");
        CrmURL = propertyUtil.getPropertyValue("CrmURL");
        FortuneURL = propertyUtil.getPropertyValue("FortuneURL");
    }

    /**
     * 登陆CRM
     * @param username
     * @param password
     */
    public void LoginCRM(String username, String password){
        driver.get(CrmURL);
        UserName.setValue(username);
        Password.setValue(password);
        Login.click();
        if(VerifyCrmLogin.existed()){
            driver.saveScreenShot();
            ReportEvent.pass("登陆CRM成功");
        }else{
            ReportEvent.fail("登陆CRM失败");
        }
    }

    public void LogoutCRM(){
        driver.switchTo().defaultContent();
        CRMLogoutBtn.click();
        CRMLogoutSubmit.click();
    }

    public void LoginFortune(String username, String password){
        driver.get(FortuneURL);
        UserName.setValue(username);
        Password.setValue(password);
        Login.click();
        driver.switchToFrame(iFrame.xpath);
        if(VerifyFortuneLogin.existed()){
            driver.saveScreenShot();
            ReportEvent.pass("登陆Fortune成功");
            IsFortuneLoggin = true;
        }else{
            ReportEvent.fail("登陆Fortune失败");
        }
    }

    public void LogoutFortune(){
        driver.switchTo().defaultContent();
        //切换到top的frame
        driver.switchToFrame("//*[@id=\"topFrame\"]");
        FortuneLogoutBtn.click();
    }
}
