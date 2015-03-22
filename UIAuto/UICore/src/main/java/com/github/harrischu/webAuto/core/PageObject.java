package com.github.harrischu.webAuto.core;

import com.github.harrischu.webAuto.repository.BaseElement;
import com.github.harrischu.webAuto.util.PropertyUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by Harris on 2014/12/17.
 */
public abstract class PageObject {
    protected Logger logger = Logger.getLogger(getClass());
    protected WebDriverDecorator driver;

    /**
     *
     * 通过反射，实例化所有页面元素对象。
     * properties中，key的值等于对象名字。
     * @param page
     *            PageObject的实例，会动态改变此实例的页面元素对象
     * @param clazz
     *            PageObject的class
     * @param propertyUtil
     *            配置文件对象，用来取出xpath
     */
    protected void reflect(Object page, Class<?extends PageObject> clazz, PropertyUtil propertyUtil){
        Field fieldList[] = clazz.getDeclaredFields();
        for(Field element:fieldList){
            Class elementclazz = element.getType();
            if(BaseElement.class.isAssignableFrom(elementclazz)){
                try{
                    Constructor cons = elementclazz.getConstructor(String.class);
                    String property = propertyUtil.getPropertyValue(element.getName());
                    if(null != property) {
                        Object obj = cons.newInstance(property);
                        element.set(page, obj);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 动态改变PageObject中子类的实例
     * 保留，暂时不需要
     * @param page
     * @param record
     * @param clazz
     * @param changeValue
     * @param callback
     */
    protected void runSubItem(Object page, Object record, Class clazz,String changeValue, ISubItem callback){
        String tmp="";
        try{
            for(Field xpathField:clazz.getDeclaredFields()){
                if (xpathField.getName().toUpperCase().equals("XPATH")){
                    tmp = xpathField.get(record).toString();
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String newXpath = "[contains(.,\"" + changeValue + "\")]";
        newXpath = tmp.concat(newXpath);
        record = changeSubItem(page, record, clazz, newXpath);
        callback.workflow(record);
    }

    /**
     * 动态改变PageObject中，子类的对象
     * @param page              Page    外部对象
     * @param record            record  内部对象
     * @param clazz             record的class
     * @param xpath             新record的xpath
     */
    private Object changeSubItem(Object page, Object record, Class clazz, String xpath){
        try{
            Constructor cons = clazz.getConstructor(page.getClass(), String.class);
            record = cons.newInstance(page, xpath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return record;
    }
}
