package com.github.harrischu.webAuto.repository;

/**
 * Created by Harris on 2014/12/16.
 */
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class Table extends BaseElement {
    public String xpath;
    private int RowCount;

    public Table(String xpath){
        this.xpath = xpath;
    }

    public boolean existed(){
        return existed(xpath);
    }

    public boolean existed(int second){
        return existed(xpath, second);
    }

    public WebElement getCellObject(int row, int column) throws NoSuchElementException{
        this.instance = getInstance(xpath);
        By by = By.xpath("//tr[" + row + "]/td[" + column + "]");
        try{
            return this.instance.findElement(by);
        }catch (NoSuchElementException e){
            logger.info("不能获取table的子对象, row = " + row + ", column = " + column);
        }
        return null;
    }

    public int getRowCount(){
        this.instance = getInstance(xpath);
        RowCount = this.instance.findElements(
                By.xpath(".//tr")).size();

        return RowCount;
    }
}
