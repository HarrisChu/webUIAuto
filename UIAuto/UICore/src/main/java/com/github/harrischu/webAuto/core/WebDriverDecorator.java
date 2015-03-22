package com.github.harrischu.webAuto.core;


import com.github.harrischu.webAuto.util.PropertyUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.testng.Reporter;

/**
 * Provide basic methods for web driver operations.
 * 
 * @author Shin Feng
 * @date 2014-07-24
 */
public class WebDriverDecorator implements WebDriver {
	private static Logger logger = Logger.getLogger(WebDriverDecorator.class);

	private static WebDriverFactory webDriverFactory = new WebDriverFactory();
	private static WebDriver driver;
	private String driverType;
	private static WebDriverDecorator webDriverDecorator;
	private static int DEFAULT_WAIT_FOR_TIMEOUT = 10;

	private boolean CLOSED=false;
	private boolean QUIT=false;

	public enum EventType{
		mouseover,
		mouseout,
	}

	/**
	 * Default constructor
	 * 根据配置文件的不同值，启动相应的webdriver
	 */
	public WebDriverDecorator() {
		LoadProperty();
		if (driverType.toUpperCase().equals("CHROME")) {
			driver = webDriverFactory.setDriver(WebDriverFactory.DriverType.chrome);
		} else if (driverType.toUpperCase().equals("IE")) {
			driver = webDriverFactory.setDriver(WebDriverFactory.DriverType.ie);
		} else if (driverType.toUpperCase().equals("FIREFOX")) {
			driver = webDriverFactory.setDriver(WebDriverFactory.DriverType.firefox);
		}
	}

	private void LoadProperty(){
		PropertyUtil propertyUtil = new PropertyUtil();
		propertyUtil.loadPropertiesFromFile("/UICore.properties");
		if(propertyUtil.getPropertyValue("DEFAULT_WAIT_FOR_TIMEOUT")!=null){
			DEFAULT_WAIT_FOR_TIMEOUT = Integer.parseInt(
					propertyUtil.getPropertyValue("DEFAULT_WAIT_FOR_TIMEOUT"));
		}
		driverType = propertyUtil.getPropertyValue("WebDriver");
	}

	/**
	 * Constructor with the specific type of the driver.
	 * 
	 * @param driverType
	 *            type of the driver
	 */
	public WebDriverDecorator(WebDriverFactory.DriverType driverType) {
		logger.info("Start the " + driverType.toString() + " browser.");
		driver = webDriverFactory.setDriver(driverType);
	}

	public static WebDriver getDriver() {
		// if (driver == null) {
		// driver = webDriverFactory.setFirefoxDriver();
		// }
		return driver;
	}

	/**
	 * Wait for the element displayed in specific duration via locator.
	 * 
	 * @param by
	 *            locator of the element
	 * @param second
	 *            duration for waiting
	 * @return WebElement
	 */
	public WebElement waitForElementDisplayed(By by, int second) {
		WebDriverWait wait = new WebDriverWait(driver, second);
		try{
			return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		}catch (Exception e){
			logger.info(e.getMessage());
			return null;
		}
	}

	public WebElement waitForElementDisplayed(String xpath){
		return waitForElementDisplayed(xpath, DEFAULT_WAIT_FOR_TIMEOUT);
	}
	/**
	 * Wait for the element displayed in specific duration via xpath.
	 * 
	 * @param xpath
	 *            xpath of the element
	 * @param second
	 *            duration for waiting
	 * @return WebElement
	 */
	private WebElement waitForElementDisplayed(String xpath, int second) {
		WebDriverWait wait = new WebDriverWait(driver, second);
		return  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public WebElement waitForElementClickable(String xpath){
		return waitForElementClickable(xpath, DEFAULT_WAIT_FOR_TIMEOUT);
	}

	private WebElement waitForElementClickable(String xpath, int second){
		WebDriverWait wait = new WebDriverWait(driver, second);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	/**
	 * Find the element via xpath.
	 * 
	 * @param xpath
	 *            xpath of the element
	 * @return WebElement
	 */
	public WebElement findElementByXpath(String xpath) {
		By by = By.xpath(xpath);
		logger.info("Find the element: " + by);
		waitForElementDisplayed(by, DEFAULT_WAIT_FOR_TIMEOUT);
		return driver.findElement(by);
	}

	/**
	 * Switch the frame via xpath.
	 * 
	 * @param xpath
	 *            xpath of the element
	 */
	public void switchToFrame(String xpath) {
		By by = By.xpath(xpath);
		logger.info("Switch to the frame: " + by);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(waitForElementDisplayed(by, 10));
	}

	/**
	 * Click the element via xpath.
	 * 
	 * @param xpath
	 *            xpath of the element
	 */
	public void findElementAndClickByXpath(String xpath) {
		By by = By.xpath(xpath);
		logger.info("Click the element: " + by);
		waitForElementDisplayed(by, DEFAULT_WAIT_FOR_TIMEOUT).click();
		wait(1);
	}

	/**
	 * Type the text into the element via xpath.
	 * 
	 * @param xpath
	 *            xpath of the element
	 * @param text
	 *            text to type
	 */
	public void findElementAndSendKeysByXpath(String xpath, String text) {
		By by = By.xpath(xpath);
		logger.info("Set the element: " + by + " with the text, \"" + text
				+ "\"");
		waitForElementDisplayed(by, DEFAULT_WAIT_FOR_TIMEOUT).sendKeys(text);
		wait(1);
	}

	/**
	 * Wait for the element to display after the specific second.
	 * 
	 * @param second
	 *            the duration for waiting
	 */
	public void wait(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void saveScreenShot(){
		saveScreenShot("");
	}

	/**
	 * 截图方法
	 * @param message
	 * 截图后，插入message到report中
	 */
	public void saveScreenShot(String message) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String mDateTime = formatter.format(new Date());
		String fileName = mDateTime;
		String filePath = null;
		try {
			//这里可以调用不同框架的截图功能
			//防止页面速度过快，强制等待
			wait(1);
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			filePath = "test-output/screenshots" + File.separator + fileName + ".png";
			File destFile = new File(filePath);
			try {
				FileUtils.copyFile(srcFile, destFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			filePath = fileName + " firefox tack screenshot Failure:" + e.getMessage();
			logger.error(filePath);
			e.printStackTrace();
		}

		if (!"".equals(filePath)) {
			Reporter.log(filePath);
			//把截图写入到Html报告中方便查看
			Reporter.log("<img src=\"../" + filePath + "\"/>");
			Reporter.log(message);
		}
	}

	/**
	 * Switch to the page with specific URL.
	 * 
	 * @param url
	 *            url of the page
	 */
	public void switchToPage(String url) {
		get(url);
	}

	/**
	 * Scroll the page to the specific element via xpath.
	 * 
	 * @param xpath
	 *            xpath of the element
	 */
	public void scrollToElement(String xpath) {
		WebElement target = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView();", target);
	}

	@Override
	public void get(String url) {
		// TODO Auto-generated method stub
		logger.info("Open the url: " + url);
		driver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		// TODO Auto-generated method stub
		return driver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return driver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
		// TODO Auto-generated method stub
		logger.info("Find the elements: " + by);
		return driver.findElements(by);
	}

	public List<WebElement> findElements(String xpath) {
		By by = By.xpath(xpath);
		logger.info("Find the elements: " + by);
		return driver.findElements(by);
	}

	/**
	 * Click the element with the specific index.
	 * 
	 * @param xpath
	 *            xpath of the element
	 * @param index
	 *            index of the element (from 0)
	 */
	public void findElementsAndClick(String xpath, int index) {
		By by = By.xpath(xpath);
		logger.info("Click the elements: " + by + " index: " + index);
		driver.findElements(by).get(index).click();
		wait(1);
	}

	@Override
	public WebElement findElement(By by) {
		// TODO Auto-generated method stub
//		logger.info("Find the element: " + by);
		waitForElementDisplayed(by, DEFAULT_WAIT_FOR_TIMEOUT);
		return driver.findElement(by);
	}

	@Override
	public String getPageSource() {
		// TODO Auto-generated method stub
		return driver.getPageSource();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		logger.info("Close the browser.");
		CLOSED=true;
		driver.close();
	}

	public boolean getClosed(){
		return CLOSED;
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		logger.info("Close the browser.");
		QUIT=true;
		driver.quit();
	}

	public boolean getQuit(){
		return QUIT;
	}

	@Override
	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return driver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return driver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return driver.switchTo();
	}

	@Override
	public Navigation navigate() {
		// TODO Auto-generated method stub
		return driver.navigate();
	}

	@Override
	public Options manage() {
		// TODO Auto-generated method stub
		return driver.manage();
	}

	/**
	 * @param xpath
	 *              检查xpath对应的页面元素是否存在
	 */
	public boolean VerifyExisted(String xpath){
		WebElement webElement = waitForElementDisplayed(By.xpath(xpath), DEFAULT_WAIT_FOR_TIMEOUT);
		return webElement != null;
	}

	public boolean VerifyExisted(String xpath, int second){
		WebElement webElement = waitForElementDisplayed(By.xpath(xpath), second);
		return webElement != null;
	}


	/**
	 * 单例模式，获取WebDriverDecorator的实例
	 * 如果已经quit或者closed，重新创建一个新的
	 * @return
	 * WebDriverDecorator的实例
	 */
	public static WebDriverDecorator getInstance(){
		if(webDriverDecorator == null){
			webDriverDecorator = new WebDriverDecorator();
		}
		if(webDriverDecorator.getClosed()||webDriverDecorator.getQuit()){
			webDriverDecorator = new WebDriverDecorator();
		}
		return webDriverDecorator;
	}

	/**
	 * 模拟事件操作
	 * @param xpath
	 * 需要模拟的页面元素的xpath
	 * @param event
	 * 事件名
	 */
	public void action(String xpath, EventType event){
		switch (event){
			case mouseover:
				Actions actions = new Actions(driver);
				logger.info("移动鼠标到对象： xpath= " + xpath);
				actions.moveToElement(driver.findElement(By.xpath(xpath))).clickAndHold().build().perform();
		}
	}
}
