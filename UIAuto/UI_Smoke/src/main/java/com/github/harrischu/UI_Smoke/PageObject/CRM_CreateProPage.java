package com.github.harrischu.UI_Smoke.PageObject;

import com.github.harrischu.webAuto.core.PageObject;
import com.github.harrischu.webAuto.core.WebDriverDecorator;
import com.github.harrischu.webAuto.repository.*;
import com.github.harrischu.webAuto.util.PropertyUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Harris on 2015/1/27.
 */
public class CRM_CreateProPage extends PageObject {
    public IFrame iFrame;

    public Edit SearchName;  //搜索的客户姓名
    public Button SearchButton; //搜索客户
    public Edit Name;       //客户姓名，用来检测搜索是否成功
    public Edit FortunePro;   //理财产品
    public WebList FortuneProList;
    public Button FortuneProButton;
    public Edit FortuneAmt; //出借金额
    public WebList ProtocolList;   //协议版本
    public Button ProtocolButton;
    public WebList PaymentTypeList; //支付方式
    public Button PaymentTypeButton;
    public WebList PaymentToBankList; //当支付方式是自行转账时，汇入银行
    public Button PaymentToBankButton;
    public Button DeductCompany; //划扣公司

    //银行卡信息
    public Button RepayAcctButton;
    public WebList RepayAcctList;
    public Edit ContractNO;
    public Button ContinueProductButton;
    public WebList ContinueProductList;
    public Button CustomerManagerButton;
    public WebList CustomerManagerList;
    public Edit Memo;

    //按钮
    public Button SubmitBtn;
    public Button SaveBtn;
    public Button CancelBtn;



    private WebDriverDecorator driver;

    public CRM_CreateProPage(WebDriverDecorator webDriverDecorator){
        PropertyUtil propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/repository/CRM_CreatePro.properties");
        driver = webDriverDecorator;
        reflect(this, CRM_CreateProPage.class, propertyUtil);
        //重写配置文件中，带中文的xpath
        SubmitBtn = new Button(propertyUtil.getPropertyValueAsUTF8("SubmitBtn"));
        SaveBtn = new Button(propertyUtil.getPropertyValueAsUTF8("SaveBtn"));
        CancelBtn = new Button(propertyUtil.getPropertyValueAsUTF8("CancelBtn"));
    }

    /**
     * @param fortuneType           理财产品
     * @param amount                出借金额
     * @param protocolType          协议版本
     * @param riskCompensation      是否风险补偿
     * @param paymentType           支付方式
     * @param toBank                当支付为自行转账时，汇入账号
     * @param deductCompany         当支付为委托划扣时，划扣公司
     */
    public void inputProtocalInfo(String fortuneType, String amount, String protocolType,
                       boolean riskCompensation,String paymentType, String toBank, String deductCompany){
        FortuneProButton.click();
        FortuneProList.selectByXpath(fortuneType);
        FortuneAmt.setValue(amount);
        ProtocolButton.click();
        ProtocolList.selectByXpath(protocolType);
        PaymentTypeButton.click();
        PaymentTypeList.selectByXpath(paymentType);
        if(paymentType.equals("自行转账")){
            PaymentToBankButton.click();
            PaymentToBankList.selectByXpath(toBank);
        }else if(paymentType.equals("委托划扣")){
            String tmpXpath = null;
            if(deductCompany.equals("通联")){
                tmpXpath = "deductCompany1";
            }else if(deductCompany.equals("富友")){
                tmpXpath = "deductCompany2";
            }else {
                tmpXpath = "deductCompany3";
            }
            DeductCompany = new Button("//input[@id=\"" + tmpXpath + "\"]");
            DeductCompany.click();
        }
    }

    /**
     * 使用默认的客服
     * @param bankcard
     * @param continuePro
     * @param memo
     */
    public void inputBankInfo(String bankcard, String contract, String continuePro, String memo){
        RepayAcctButton.click();
        RepayAcctList.selectByXpath(bankcard);
        ContractNO.setValue(contract);
        ContinueProductButton.click();
        ContinueProductList.selectByXpath(continuePro);
        Memo.setValue(memo);
    }

    /**
     * 输入已经存在的银行卡信息，其中合同编号是根据理财产品，自动生成
     * @param bankcard                  银行卡信息
     * @param continuePro               到期处理方式
     * @param customermanager           客户经理
     * @param memo
     */
    public void inputBankInfo(String bankcard, String contract, String continuePro, String customermanager, String memo){
        inputBankInfo(bankcard, contract, continuePro, memo);
        CustomerManagerButton.click();
        CustomerManagerList.selectByXpath(customermanager);
    }

    //TODO 待补充，新建银行卡信息
    public void createBankInfo(){
    }

    /**
     * 根据理财产品，自动生成合同编号
     * @return
     */
    public String genContractNO(){
        String result="";
        String fortunetype = FortunePro.getValue();
        if(!fortunetype.equals("证大月稳盈") && !fortunetype.equals("贷里淘金")){
            //季喜规则： ZDLyyyyMMdd12345
            result = result.concat("ZDL");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            String temp = simpleDateFormat.format(now);
            temp = temp.substring(0, temp.length()-1);
            return result.concat(temp);
        }
        return null;
    }
}
