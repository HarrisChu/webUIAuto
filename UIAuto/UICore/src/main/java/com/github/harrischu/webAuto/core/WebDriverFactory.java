package com.github.harrischu.webAuto.core;

import com.github.harrischu.webAuto.util.PropertyUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * Provide the factory method for web driver.
 * 
 * @author Shin Feng
 * @date 2014-07-24
 */
public class WebDriverFactory {
	private static int DEFAULT_WAIT_FOR_TIMEOUT = 15;
	private static int DEFAULT_WAIT_FOR_PAGELOAD = 30;
	private static WebDriver driver;
	public enum DriverType{
		chrome,
		ie,
		firefox
	}

	/**
	 * Default constructor
	 */
	public WebDriverFactory() {
	}

	/**
	 * Constructor with the specific type of the driver.
	 * 
	 * @param driverType
	 *            type of the driver
	 * @return
	 */
	public WebDriver setDriver(DriverType driverType) {
		if (driverType == DriverType.firefox) {
			driver = setFirefoxDriver();
		} else if (driverType == DriverType.ie) {
			driver = setInternetExplorerDriver();
		} else if (driverType == DriverType.chrome) {
			driver = setChromeDriver();
		}
		getConfigure();

		driver.manage().timeouts()
				.implicitlyWait(DEFAULT_WAIT_FOR_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts()
				.pageLoadTimeout(DEFAULT_WAIT_FOR_PAGELOAD, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * Set the driver as Firefox.
	 * 
	 * @return WebDriver
	 */
	public WebDriver setFirefoxDriver() {
		System.setProperty("webdriver.firefox.bin",
				"D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		return driver;
	}

	/**
	 * Set the driver as Internet Explorer.
	 * 
	 * @return WebDriver
	 */
	public WebDriver setInternetExplorerDriver() {
		System.setProperty("webdriver.ie.driver",
				getClass().getResource("/driver/IEDriverServer.exe").getPath());
		DesiredCapabilities ieCapability = DesiredCapabilities
				.internetExplorer();
		ieCapability
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		driver = new InternetExplorerDriver(ieCapability);
		return driver;
	}

	/**
	 * Set the driver as Chrome.
	 * 
	 * @return WebDriver
	 */
	public WebDriver setChromeDriver() {
		System.setProperty("webdriver.chrome.driver",
				getClass().getResource("/driver/chromedriver.exe").getPath());
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		DesiredCapabilities chromeCapability = new DesiredCapabilities();
		chromeCapability.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver();
		return driver;
	}

	/**
	 * 读取配置文件
	 */
	private void getConfigure(){
		PropertyUtil propertyUtil = new PropertyUtil();
		propertyUtil.loadPropertiesFromFile("/UICore.properties");
		if(propertyUtil.getPropertyValue("DEFAULT_WAIT_FOR_TIMEOUT")!=null){
			DEFAULT_WAIT_FOR_TIMEOUT = Integer.parseInt(
					propertyUtil.getPropertyValue("DEFAULT_WAIT_FOR_TIMEOUT"));
		}
		if(propertyUtil.getPropertyValue("DEFAULT_WAIT_FOR_PAGELOAD")!=null) {
			DEFAULT_WAIT_FOR_PAGELOAD = Integer.parseInt(
					propertyUtil.getPropertyValue("DEFAULT_WAIT_FOR_PAGELOAD"));
		}
	}
}
