package com.github.harrischu.UI_Smoke.testcase;

import com.github.harrischu.UI_Smoke.PageObject.*;
import com.github.harrischu.UI_Smoke.util.PageUtil;
import com.github.harrischu.webAuto.core.TestCase;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.data.DataTableDecorator;
import com.github.harrischu.webAuto.data.IDataTable;
import org.testng.annotations.*;

/**
 * Created by Harris on 2015/1/23.
 */
public class FortuneMainTest extends TestCase {
    public IDataTable dataTable;
    private WebDriverDecorator driver;

    private LoginPage loginPage;
    private MenuPage menuPage;
    private InvestManagePage investManagePage;
    private OrderManagePage orderManagePage;
    private TradeManagePage tradeManagePage;
    private static String InvestNO;

    public FortuneMainTest(){
        driver = WebDriverDecorator.getInstance();
        dataTable = DataTableDecorator.getDataTable("AutoMatch.csv");
        loginPage = new LoginPage(driver);
        menuPage = new MenuPage(driver);
        investManagePage = new InvestManagePage(driver);
        orderManagePage = new OrderManagePage(driver);
        tradeManagePage = new TradeManagePage(driver);
    }

    @BeforeMethod
    public void beforeMethod(){
        driver = WebDriverDecorator.getInstance();
    }

    /**
     * 自动撮合
     * 根据合同编号，自动撮合
     */

    @Test
    public void Test_01_AutoMatch(){
        CRMTest crmTest = new CRMTest();
        String contract = crmTest.CRM_Review();
        logger.info(contract);
        driver.close();
        driver = WebDriverDecorator.getInstance();
        loginPage.LoginFortune(dataTable.getValue("username"),
                dataTable.getValue("password"));

        driver.switchToFrame(menuPage.iFrame.xpath);
        menuPage.Menu.get("业务管理").click();
        menuPage.MenuList.get("投资管理").click();

        PageUtil.switchToMain();

        investManagePage.AutoMacth(contract, "16日");
    }

    /**
     * 付款
     * 已经完成自动撮合的基础上，进行批量付款
     */
    @Test
    public void Test_02_BatchPay(){
        loginPage.LoginFortune(dataTable.getValue("username"),
                dataTable.getValue("password"));

        driver.switchToFrame(menuPage.iFrame.xpath);
        menuPage.Menu.get("业务管理").click();
        menuPage.MenuList.get("投资管理").click();

        PageUtil.switchToMain();
        //根据合同编号，获取投资编号
        InvestNO = investManagePage.getInvestNO(dataTable.getValue("contract"));
        investManagePage.AutoMacth(dataTable.getValue("contract"), "16日");
        dataTable.setValue("invest_id", InvestNO);
        dataTable.export("AutoMatch_result.csv");
        this.BatchOrder(InvestNO);
        this.BatchPay(InvestNO);
    }

    /**
     * 批量订购
     * @param InvestNO
     */
    @Test
    public void BatchOrder(String InvestNO){
        driver.switchToFrame(menuPage.iFrame.xpath);
        menuPage.Menu.get("交易管理").click();
        driver.wait(1);
        menuPage.MenuList.get("订单管理").click();
        PageUtil.switchToMain();
        orderManagePage.BatchOrderExec(InvestNO);
    }

    /**
     * 批量付款
     * @param InvestNO
     */
    @Test
    public void BatchPay(String InvestNO){
        driver.switchToFrame(menuPage.iFrame.xpath);
        menuPage.Menu.get("交易管理").click();
        menuPage.MenuList.get("交易单管理").click();
        PageUtil.switchToMain();
        tradeManagePage.BatchPay(InvestNO);
    }

    /**
     * 批量收款
     */
    @Test
    public void Test_03_BatchReceive() {
    }

    /**
     * 每个method，都需要重新登陆，保证独立性
     */
    @AfterMethod
    private void logout(){
//        driver.close();
    }

    @AfterClass
    private void finish(){
//        tearDown(driver);
    }
}
