package com.github.harrischu.webAuto.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Harris on 2015/1/7.
 */
public class CreditDemo {
    private WebDriver driver;
    public CreditDemo(){
        driver = new ChromeDriver();
    }
    public void loginDemo(){
        driver.get("http://test.ezendai.com:7081/zdsys/#");
        driver.findElement(By.id("usercode")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin123");
        driver.findElement(By.className("button")).click();
    }

    public void getInfoDemo(){
        loginDemo();
//        try {
//            driver.wait(3000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        WebElement auditCount = driver.findElement(By.xpath("//br[@contains(text(), '中心经理审批']"));
        WebElement InfoDeo = driver.findElement(
                By.xpath("//div[@class='top_nav_c']/span[contains(.,'您好')]"));
        System.out.println("test");
        System.out.println(InfoDeo.getText());

    }

    public void SwithFrameDemo(){
        getInfoDemo();
        WebElement iframe = driver.findElement(By.xpath("//iframe[@id='mFrame']"));
        driver.switchTo().frame(iframe);
        iframe = driver.findElement(By.xpath("//div[@class='task']/iframe"));
        driver.switchTo().frame(iframe);
        driver.findElement(By.xpath("//a[contains(@href,'loanContractList')]")).click();

    }

    public static void main(String[] args){
        CreditDemo creditDemo = new CreditDemo();
        creditDemo.loginDemo();
        creditDemo.getInfoDemo();
//        creditDemo.SwithFrameDemo();


    }
}
