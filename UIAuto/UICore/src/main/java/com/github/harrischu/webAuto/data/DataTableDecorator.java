package com.github.harrischu.webAuto.data;

import com.github.harrischu.webAuto.util.PropertyUtil;

/**
 * Created by Harris on 2014/12/22.
 */
public class DataTableDecorator {
    public IDataTable DataTable;
    public enum  ProvideType{
        csv
    }

    public static IDataTable getDataTable(String param){
        PropertyUtil propertyUtil = new PropertyUtil();
        propertyUtil.loadPropertiesFromFile("/UICore.properties");
        String provideType = propertyUtil.getPropertyValue("DataType");
        if(provideType.toUpperCase().equals("CSV")){
            IDataTable dataTable = getProvider(ProvideType.csv);
            dataTable.LoadDataFile(param);
            return dataTable;
        }
        return null;
    }

    private static IDataTable getProvider(ProvideType provideType){
        if(provideType == ProvideType.csv){
            return getCVSProvider();
        }
        return null;
    }

    private static IDataTable getCVSProvider(){
        return (IDataTable)new CSVProvider();
    }
}
