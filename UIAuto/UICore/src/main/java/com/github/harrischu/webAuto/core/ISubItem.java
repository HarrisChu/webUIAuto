package com.github.harrischu.webAuto.core;

/**
 * Created by Harris on 2015/2/11.
 */

/**
 * PageObject中，子对象接口，用于动态改变表格要操作的元素
 */
public interface ISubItem {
    public void workflow(Object object);
}
