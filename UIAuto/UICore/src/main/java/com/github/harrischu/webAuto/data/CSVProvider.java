package com.github.harrischu.webAuto.data;


import com.github.harrischu.webAuto.util.PropertyUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harris on 2014/12/22.
 * CSV 测试数据驱动
 */
public class CSVProvider implements IDataTable {
    private Logger logger = Logger.getLogger(CSVProvider.class);
    private int currentRow;
    private int TestDataRowCount;
    private int TestDataColCount;
    private Map<String, String> DataHeader;  //(column, parameter)
    private ArrayList<Map<String, String>> DataBody; //List[(column, value)]
    private boolean ISLOADDATE;

    PropertyUtil propertyUtil;
    String filepath;

    public CSVProvider(){
        ISLOADDATE = false;
        propertyUtil= new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/UICore.properties");
        filepath = propertyUtil.getPropertyValue("CSV_DataSource_Forlder");
    }

    /**
     * 读取CSV文件，文件所在路径，已经写在配置文件中，只需传入文件名
     * @param filename
     * 文件名
     */
    @Override
    public void LoadDataFile(String filename){
        filename = filepath.concat("\\".concat(filename));
        FileInputStream fileInputStream;
        BufferedReader bufferedReader;
        String testData;
        DataHeader = new HashMap<String, String>();
        DataBody = new ArrayList<Map<String, String>> ();

        try {
            fileInputStream = new FileInputStream(filename);
            bufferedReader = new BufferedReader(
                    new InputStreamReader(fileInputStream, "UTF-8"));
            String parameters = bufferedReader.readLine();
            //添加第一行参数
            setDataHeader(parameters);
            while ((testData = bufferedReader.readLine()) != null){
                DataBody.add(setDataBody(testData));
            }
            TestDataRowCount = DataBody.size();
            TestDataColCount = DataHeader.size();
            setCurrentRow(1);
            ISLOADDATE = true;
            bufferedReader.close();
            fileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将一行的内容转换成map
     * @param line
     */
    @Override
    public void setDataHeader(String line){
        String [] parameterList = line.split(",");
        for(int i=0;i<parameterList.length;i++){
            //如果使用Excel打开，会在文件头加上utf-8的BOM
            String temp = parameterList[i].trim();
            DataHeader.put(Integer.toString(i), temp.replace("\uFEFF", ""));
        }
    }

    /**
     * 设置参数的值
     * @param line
     * @return
     */
    private Map<String, String> setDataBody(String line){
        Map<String, String> RowData = new HashMap<String, String>();
        String [] dataList = line.split(",");
        for(int i=0;i<DataHeader.size();i++){
            //默认值是空
            if(i < dataList.length) {
                RowData.put(Integer.toString(i), dataList[i].trim());
            }else{
                RowData.put(Integer.toString(i), "");
            }
        }
        return RowData;
    }

    @Override
    public int getRowCount(){
        return TestDataRowCount;
    }

    @Override
    public int getCurrentRow(){
        return currentRow;
    }

    @Override
    public void setCurrentRow(int row){
        if(row > getRowCount()){
            logger.info("设置行数超出所有测试数据");
            currentRow = -1;
        }
        currentRow = row;
    }

    @Override
    public String getValue(String parameter){
        Map <String, String> ParamValue = DataBody.get(currentRow - 1);
        String column = getMapkey(DataHeader, parameter);
        if(column != null){
            return ParamValue.get(column);
        }else{
            return null;
        }
    }

    @Override
    public void setValue(String parameter, String value){
        Map <String, String> ParamValue = DataBody.get(currentRow - 1);
        String column = getMapkey(DataHeader, parameter);
        if(column != null){
            ParamValue.put(column, value);
        }else{
            //TODO capture the exception
        }
    }

    /**
     * 根据value，获取map中的key
     * @param map
     * @param value
     * @return
     */
    private String getMapkey(Map<String, String> map, String value){
        String result = null;
        for(String i:map.keySet()){
            if(DataHeader.get(i).equals(value)){
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean export(String filename){
        if (!ISLOADDATE){
            logger.error("在读取数据前不能导出");
            return false;
        }
        StringBuffer result = new StringBuffer();
        for(int i=0;i<TestDataColCount;i++){
            if(i!= 0){
                result.append(",");
            }else{
                //添加BOM，方便用Excel查看
                result.append("\uFEFF");
            }
            result.append(DataHeader.get(Integer.toString(i)));
        }
        //设置test data
        for(int i=0;i<TestDataRowCount;i++){
            Map<String, String> ParamValue = DataBody.get(i);
            result.append("\r\n");
            for(int column=0;column<TestDataColCount;column++){
                if(column!= 0){
                    result.append(",");
                }
                result.append(ParamValue.get(Integer.toString(column)));
            }
        }
        //将更新后的test data导出
        filename = filepath.concat("\\".concat(filename));
        try{
            File file = new File(filename);
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file));
            bufWriter.write(result.toString());
            bufWriter.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 自测用
     * @param args
     */
    public static void main(String [] args){
        Logger logger = Logger.getLogger(CSVProvider.class);
        CSVProvider csvProvider = new CSVProvider();
        csvProvider.LoadDataFile("CRM.csv");
        logger.info("header is " + csvProvider.DataHeader);
        logger.info("body is " + csvProvider.DataBody);
        logger.info(csvProvider.getCurrentRow());
        logger.info(csvProvider.DataBody.toArray()[0]);
        logger.info(csvProvider.getRowCount());
        logger.info(csvProvider.getValue("username"));
        csvProvider.setCurrentRow(2);
        logger.info(csvProvider.getValue("username"));
        csvProvider.setValue("username", "test");
        logger.info(csvProvider.getValue("username"));
        csvProvider.export("test.csv");
    }
}
