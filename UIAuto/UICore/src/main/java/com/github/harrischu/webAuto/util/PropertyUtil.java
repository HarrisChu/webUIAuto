package com.github.harrischu.webAuto.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provide basic methods for properties operations.
 * 
 * @author Shin Feng
 * @date 2014-07-24
 */
public class PropertyUtil {
	private Properties properties = new Properties();

	public PropertyUtil() {
	}

	/**
	 * Get the value of the specific property.
	 * 
	 * @param propertyName
	 *            name of the property
	 * @return
	 */
	public String getPropertyValue(String propertyName){
		return (String) properties.get(propertyName);
	}

	public String getPropertyValueAsUTF8(String propertyName){
		try {
			return new String(properties.getProperty(propertyName).getBytes("ISO-8859-1"), "UTF-8");
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Load the properties from properties file.
	 * 
	 * @param path
	 *            path of the property file. ("/Page/Test.properties")
	 * @return Properties
	 * @throws java.io.IOException
	 */
	public Properties loadPropertiesFromFile(String path) {

		try {
			InputStream fileInputStream = PropertyUtil.class
					.getResourceAsStream(path);
			properties.load(fileInputStream);
			fileInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}
}
