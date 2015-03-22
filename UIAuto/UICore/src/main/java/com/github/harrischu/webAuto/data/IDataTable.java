package com.github.harrischu.webAuto.data;

import java.io.File;
import java.util.Map;

/**
 * Created by Harris on 2014/12/22.
 * 测试数据驱动接口
 */
public interface IDataTable {
    public void LoadDataFile(String file);
    public int getRowCount();
    public int getCurrentRow();
    public void setCurrentRow(int row);
    public void setDataHeader(String line);
    public String getValue(String parameter);
    public boolean export(String filename);
    public void setValue(String parameter, String value);

}
