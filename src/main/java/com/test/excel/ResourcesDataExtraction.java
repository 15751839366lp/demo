package com.test.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcesDataExtraction {

    public static void main(String[] args) {
        String filePath = "D:\\Test\\jlja\\resources\\originalResourcesData.xlsx";
        String sheetName = "Sheet1";
        XSSFWorkbook sheets = getSheets(filePath);
        Map<String, Object> dataMap = getResourcesExcelData(sheets, sheetName);
    }

    //获取需要的数据和列数的关系
    public static Map<Integer,String> getDataNum(XSSFRow row) {
        Map<Integer,String> dataNumMap = new HashMap<>();
        int columns = row.getPhysicalNumberOfCells();
        for (int j = 0; j < columns; j++) {
            String cell = row.getCell(j).toString().trim();
            if(cell.equals("材料编号")){
                dataNumMap.put(j,"材料编号");
            }else if(cell.equals("记帐月份")){
                dataNumMap.put(j,"记帐月份");
            }else if(cell.equals("领料单位")){
                dataNumMap.put(j,"领料单位");
            }else if(cell.equals("出库金额")){
                dataNumMap.put(j,"出库金额");
            }
        }
        return dataNumMap;
    }

    //获取Excel数据流
    public static XSSFWorkbook getSheets(String filePath) {
        FileInputStream fileInputStream = null;
        XSSFWorkbook sheets = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            sheets = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheets;
    }

    //获取并解析材料数据
    public static Map<String, Object> getResourcesExcelData(XSSFWorkbook sheets, String sheetName) {
        Map<String, Object> dataMap = null;
        Map<Integer,String> dataNumMap = null;
        List<Map<String,String>> tempDataList = new ArrayList<>();
//        Map<String,Map<String,Map<String,Integer>>> rowMap = new HashMap<>();
        try {
            XSSFSheet sheet = sheets.getSheet(sheetName);
            int rows = sheet.getPhysicalNumberOfRows();

            //循环每一行
            for (int i = 0; i < rows; i++) {
                if (i == 0) {
                    XSSFRow row = sheet.getRow(i);
                    dataNumMap = getDataNum(row);
                } else {
                    //获取列数
                    XSSFRow row = sheet.getRow(i);
                    int columns = row.getPhysicalNumberOfCells();
                    Map<String,String> rowMap = new HashMap<>();

                    //获取所需数据
                    dataNumMap.forEach((key,value) -> {
                        String cell = null;
                        if(value.equals("材料编号")){
                            cell = row.getCell(key).toString().trim().substring(0,2);
                            rowMap.put("材料编号",cell);
                        }else if(value.equals("记帐月份")){
                            cell = row.getCell(key).toString().trim();
                            rowMap.put("记帐月份",cell);
                        }else if(value.equals("领料单位")){
                            cell = row.getCell(key).toString().trim();
                            rowMap.put("领料单位",cell);
                        }else if(value.equals("出库金额")){
                            cell = row.getCell(key).toString().trim();
                            rowMap.put("出库金额",cell);
                        }
                    });
                    if(rowMap != null && !rowMap.isEmpty()){
                        tempDataList.add(rowMap);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}

