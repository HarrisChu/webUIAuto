package com.github.harrischu.webAuto.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Harris on 2015/1/21.
 */
public class FortuneDemo {
    private WebDriver driver;
    public FortuneDemo(){
        driver = new ChromeDriver();
    }

    public void demo(){
        driver.get("http://172.16.230.112:8080/fortune/account/user/main");
        driver.findElement(By.id("username")).sendKeys("liubx");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.className("btn")).click();
        driver.switchTo().frame(driver.findElement(By.id("leftFrame")));
        driver.findElement(By.xpath("//dt[contains(.,'业务管理'])")).click();
        driver.findElement(By.xpath("//a[contains(.,'投资管理')]")).click();
    }

    public static void main(String[] args){
        FortuneDemo fortuneDemo = new FortuneDemo();
        fortuneDemo.demo();
    }
}
