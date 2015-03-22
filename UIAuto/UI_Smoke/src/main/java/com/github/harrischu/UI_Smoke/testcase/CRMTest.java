package com.github.harrischu.UI_Smoke.testcase;

import com.github.harrischu.UI_Smoke.PageObject.*;
import com.github.harrischu.webAuto.core.Assertion;
import com.github.harrischu.webAuto.core.TestCase;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.data.DataTableDecorator;
import com.github.harrischu.webAuto.data.IDataTable;
import org.testng.annotations.*;

/**
 * Created by Harris on 2015/1/23.
 */
public class CRMTest extends TestCase {
    public IDataTable dataTable;
    private WebDriverDecorator driver;

    private LoginPage loginPage;
    private CRM_MenuPage crm_menuPage;
    private CRM_ReviewPage crm_reviewPage;
    private CRM_ProListPage crm_proListPage;
    private CRM_CreateProPage crm_createProPage;

    private String contract;   //合同编号

    public CRMTest(){
        this.init();
    }

    @BeforeMethod
    private void init(){
        dataTable = DataTableDecorator.getDataTable("CRM.csv");
        driver = WebDriverDecorator.getInstance();
        //初始化各个page
        crm_menuPage = new CRM_MenuPage(driver);
        crm_reviewPage = new CRM_ReviewPage(driver);
        crm_proListPage = new CRM_ProListPage(driver);
        crm_createProPage = new CRM_CreateProPage(driver);
        loginPage = new LoginPage(driver);
    }

    /**
     *
     */
    @Test
    public void Test_01_CRM_CreateProduct(){
        loginPage.LoginCRM(dataTable.getValue("username"),
                dataTable.getValue("password"));
        crm_menuPage.BusiManage.click();
        driver.wait(1);
        crm_menuPage.ApplyPro.click();

        driver.switchToFrame(crm_proListPage.iFrame.xpath);

        //mouseover
        driver.action(crm_proListPage.ApplyButton.xpath,
                    WebDriverDecorator.EventType.mouseover);

        crm_proListPage.FortuneApply.click();
        //申请新的理财产品
        driver.switchToFrame(crm_createProPage.iFrame.xpath);
        crm_createProPage.SearchName.setValue(dataTable.getValue("CRM_customerName"));
        crm_createProPage.SearchButton.click();
        logger.info(crm_createProPage.Name.getValue());

        //输入协议信息
        crm_createProPage.inputProtocalInfo(dataTable.getValue("CRM_product"),
                dataTable.getValue("CRM_amount"),dataTable.getValue("CRM_Protrol"), true,
                dataTable.getValue("CRM_PaymentType"), dataTable.getValue("To_Bank"), null);

        contract = crm_createProPage.genContractNO();
        //输入银行信息
        crm_createProPage.inputBankInfo(dataTable.getValue("Repay_Bank"),contract,
                dataTable.getValue("ContinueProduct"), dataTable.getValue("memo"));

        driver.saveScreenShot("检查输入数据");
        crm_createProPage.SubmitBtn.click();
        //根据合同编号，定位新生成的理财产品
        String tmpXpath = "[contains(.,\"" + contract + "\")]";
        crm_proListPage = new CRM_ProListPage(driver);
        crm_proListPage.record = crm_proListPage.new Record(
                crm_proListPage.record.xpath.concat(tmpXpath));

        driver.switchToFrame(crm_proListPage.iFrame.xpath);
        Assertion.assertXpathExist(driver, crm_proListPage.record.ContractNo.xpath,
                "检查是否创建成功，合同编号为： <b>" + contract + "</b>");
        dataTable.setValue("Out_Contract", contract);
    }

    /**
     * 质检,返回合同编号
     */
    @Test
    public void Test_02_CRM_ReviewTest(){
        for(int i=0;i<dataTable.getRowCount();i++){
            logger.info("执行第" + Integer.toString(i+1) + "次 测试数据");
            dataTable.setCurrentRow(i + 1);
            this.CRM_Review();
            loginPage.LogoutCRM();
        }
        dataTable.export("CRM_result.csv");
    }

    public String CRM_Review(){
        this.Test_01_CRM_CreateProduct();
        crm_proListPage.record.Review.click();
        crm_reviewPage.inputReviewInfo("质检通过", "自动化测试");
        driver.saveScreenShot("截图输入数据");
        crm_reviewPage.SubmitBtn.click();
        //检查是否质检成功
        crm_proListPage.verifyRecordState(contract, "质检通过", "通过", false);
        return contract;
    }



    /**
     * 每个method，都需要重新登陆，保证独立性
     */
    @AfterMethod
    private void logout(){
        driver.close();
    }

    @AfterClass
    private void finish(){
        tearDown(driver);
    }
}
