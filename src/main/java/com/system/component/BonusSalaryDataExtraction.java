package com.jlja.excel.hightech.utils;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BonusSalaryDataExtraction {

    static String importPath = "C:\\lipeng\\财务\\高新\\调账\\工资奖金\\工资表\\2021";

    static String exportPath = "C:\\lipeng\\财务\\高新\\调账\\工资奖金\\result";

    static String changeFileName = "2021-1.xls";

    static String changeSheetName = "20201月工资";

    static int sectionNum = 6;

    public static void main(String[] args) throws IOException {


        batchExcelDealData(importPath);
//        XSSFWorkbook xssfSheets = getXSSFSheets(importPath, dataFileName);
//        ArrayList<ArrayList<Object>> salaryExcelData = getData(xssfSheets);
//        HSSFWorkbook newSheets = changeData(salaryExcelData);
//        exportData("ht" + changeFileName, newSheets);
//        ArrayList<ArrayList<Object>> salaryExcelData = getSalaryExcelData(xssfSheets);
//        HSSFWorkbook hssfSheets = getHSSFSheets(importPath, changeFileName);
//        HSSFWorkbook newSheets = changeExcelData(salaryExcelData, hssfSheets);
//        exportData("ht" + changeFileName, newSheets);
    }

    //获取Excel数据流
    public static HSSFWorkbook getHSSFSheets(String path, String name) {
        FileInputStream fileInputStream = null;
        HSSFWorkbook sheets = null;
        try {
            fileInputStream = new FileInputStream(path + "\\" + name);
            sheets = new HSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheets;
    }

    public static XSSFWorkbook getXSSFSheets(String path, String name) {
        FileInputStream fileInputStream = null;
        XSSFWorkbook sheets = null;
        try {
            fileInputStream = new FileInputStream(path + "\\" + name);
            sheets = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheets;
    }

    /**
     * 获取并解析材料数据
     *
     * @param sheets
     * @return 0-单位
     * 1-人数
     * 2-高新建安人员工资
     * 3-基本养老保险
     * 4-失业保险
     * 5-医疗保险
     * 6-公积金
     * 7-房租
     * 8-税金
     * 9-风险金
     * 10-暖气费
     * 11-年金
     * 12-实发
     * 13-带薪休假补贴
     * 14-误餐补贴
     * 15-午餐补贴总额部分
     * 16-午餐补贴进福利费
     * 17-新职工工资
     * 18-住房补贴
     * 19-工资总额
     */
    public static ArrayList<ArrayList<Object>> getSalaryExcelData(XSSFWorkbook sheets,String dataSheetName) {
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        try {
            XSSFSheet sheet = sheets.getSheet(dataSheetName);

            //插入研发小计初始值
            ArrayList<Object> totalList = new ArrayList<>();
            totalList.add(0, "研发小计");
            for (int n = 0; n < 19; n++) {
                totalList.add(new BigDecimal("0"));
            }

            int rowNum = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rowNum; i++) {
                if (sheet.getRow(i) != null) {
                    XSSFCell sectionName = sheet.getRow(i).getCell(1);
                    if (sectionName != null && !sectionName.toString().isEmpty() && sectionName.toString().length() >= 4) {
                        if ("研发中心".equals(sectionName.toString().trim().substring(0, 4)) || "本页合计".equals(sectionName.toString().trim())) {
                            ArrayList<Object> dataList = new ArrayList<>();
                            BigDecimal tempNum = null;
                            XSSFCell tempCell = null;
                            XSSFRow row1 = sheet.getRow(i);
                            XSSFRow row2 = sheet.getRow(i + 1);
//                            0-单位
                            if ("本页合计".equals(sectionName.toString().trim())) {
                                dataList.add(0, sectionName.toString().trim());
                            } else {
                                dataList.add(0, "研发" + sectionName.toString().substring(5, 7));
                            }

//                            1-人数
                            tempCell = row2.getCell(1);
                            if ("本页合计".equals(sectionName.toString().trim())) {
                                tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString().trim().substring(0, 4) : Double.toString(0));
                            } else {
                                tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            }
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(1, ((BigDecimal) totalList.get(1)).add(tempNum));
                            }
//                            2-高新建安人员工资
                            tempCell = row2.getCell(22);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(new BigDecimal(row1.getCell(17).toString())
                                    .subtract(tempNum));
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(2, ((BigDecimal) totalList.get(2)).add(new BigDecimal(row1.getCell(17).toString()).subtract(tempNum)));
                            }
//                            3-基本养老保险
                            tempCell = row1.getCell(18);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(3, ((BigDecimal) totalList.get(3)).add(tempNum));
                            }
//                            4-失业保险
                            tempCell = row2.getCell(18);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(4, ((BigDecimal) totalList.get(4)).add(tempNum));
                            }
//                            5-医疗保险
                            tempCell = row1.getCell(19);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(5, ((BigDecimal) totalList.get(5)).add(tempNum));
                            }
//                            6-公积金
                            tempCell = row1.getCell(22);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(6, ((BigDecimal) totalList.get(6)).add(tempNum));
                            }
//                            7-房租
                            tempCell = row1.getCell(20);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(7, ((BigDecimal) totalList.get(7)).add(tempNum));
                            }
//                            8-税金
                            tempCell = row1.getCell(21);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(8, ((BigDecimal) totalList.get(8)).add(tempNum));
                            }
//                            9-风险金
                            tempCell = row2.getCell(21);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(9, ((BigDecimal) totalList.get(8)).add(tempNum));
                            }
//                            10-暖气费
                            tempNum = new BigDecimal("0");
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(10, ((BigDecimal) totalList.get(10)).add(tempNum));
                            }
//                            11-年金
                            tempCell = row2.getCell(20);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(11, ((BigDecimal) totalList.get(11)).add(tempNum));
                            }
//                            12-实发
                            tempCell = row1.getCell(23);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(12, ((BigDecimal) totalList.get(12)).add(tempNum));
                            }
//                            13-带薪休假补贴
                            tempCell = row2.getCell(6);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(13, ((BigDecimal) totalList.get(13)).add(tempNum));
                            }
//                            14-误餐补贴
                            tempCell = row1.getCell(14);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(14, ((BigDecimal) totalList.get(14)).add(tempNum));
                            }
//                            15-午餐补贴总额部分
                            tempCell = row2.getCell(1);
                            if ("本页合计".equals(sectionName.toString().trim())) {
                                tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString().trim().substring(0, 4) : Double.toString(0));
                            } else {
                                tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            }
                            dataList.add(tempNum.multiply(new BigDecimal(Double.toString(80))));
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(15, ((BigDecimal) totalList.get(15)).add(tempNum.multiply(new BigDecimal(Double.toString(80)))));
                            }
//                            16-午餐补贴进福利费
                            BigDecimal fir = (BigDecimal) dataList.get(14);
                            BigDecimal sec = (BigDecimal) dataList.get(15);
                            tempNum = fir.subtract(sec);
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(16, ((BigDecimal) totalList.get(16)).add(tempNum));
                            }
//                            17-新职工工资
                            tempNum = new BigDecimal("0");
                            dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(17, ((BigDecimal) totalList.get(17)).add(tempNum));
                            }
//                            18-住房补贴
                                tempCell = row2.getCell(14);
                                tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                                dataList.add(tempNum);
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(18, ((BigDecimal) totalList.get(18)).add(tempNum));
                            }
//                            19-工资总额
                            tempNum = ((BigDecimal) dataList.get(2)).subtract(((BigDecimal) dataList.get(13))).subtract(((BigDecimal) dataList.get(14))).add(((BigDecimal) dataList.get(15)));
                            dataList.add(((BigDecimal) dataList.get(2)).subtract(((BigDecimal) dataList.get(13))).subtract(((BigDecimal) dataList.get(14))).add(((BigDecimal) dataList.get(15))));
                            if (!"本页合计".equals(sectionName.toString().trim())) {
                                totalList.set(19, ((BigDecimal) totalList.get(19)).add(tempNum));
                            }

                            list.add(dataList);
                        }
                    }
                }
            }

            list.add(totalList);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }

        return list;
    }

    //修改原表数据
    public static HSSFWorkbook changeExcelData(ArrayList<ArrayList<Object>> salaryExcelData, HSSFWorkbook hssfSheets) {
        HSSFSheet sheet = hssfSheets.getSheet(changeSheetName);
        hssfSheets.setSheetName(hssfSheets.getSheetIndex(changeSheetName), changeSheetName.substring(0, 6) + "高新" + changeSheetName.substring(6));
        try {
            List<Integer> emptyList = new ArrayList<>();
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i) == null || sheet.getRow(i).getCell(0) == null || "".equals(sheet.getRow(i).getCell(0).toString().trim())) {
                    emptyList.add(i);
                }
            }

            //遍历修改第一部分减少的值
            for (int i = 0; i < emptyList.get(0); i++) {
                HSSFRow row = sheet.getRow(i);
                if (row.getCell(0).toString().length() >= 2) {
                    for (ArrayList<Object> numList : salaryExcelData) {
                        if (numList.contains("研发" + row.getCell(0).toString().trim().substring(0, 2))) {
                            if (numList != null) {
                                //修改字体颜色
//                        for(int j = 0; j <= row.getLastCellNum(); j++){
//                            if(row.getCell(j) != null){
//                                HSSFCellStyle style = row.getCell(j).getCellStyle();
//                                HSSFFont font = style.getFont(hssfSheets);
//
//                                if(font.getColor() == 32767){
//                                    font.setColor(HSSFColor.RED.index);
//                                    style.setFont(font);
//                                    row.getCell(j).setCellStyle(style);
//                                }
//                            }
//                        }

                                for (int j = 1; j < 13; j++) {
                                    HSSFCell cell = row.getCell(j);
                                    BigDecimal oldNum;
                                    if (cell != null && !"".equals(cell.toString().trim())) {
                                        if (!cell.toString().contains("+") && !cell.toString().contains("-")) {
                                            oldNum = new BigDecimal(cell.toString().trim());
                                        } else {
                                            oldNum = new BigDecimal(Double.toString(cell.getNumericCellValue()));
                                        }

                                    } else {
                                        oldNum = new BigDecimal(Double.toString(0));
                                    }

                                    BigDecimal subNum = (BigDecimal) numList.get(j);
                                    cell.setCellValue(oldNum.subtract(subNum).doubleValue());
                                }
                            }
                        }
                    }
                }
            }

            //更新合计数据


            //插入研发数据
            int changeStartRow = emptyList.get(0);
            sheet.shiftRows(changeStartRow - 1, sheet.getLastRowNum(), salaryExcelData.size() + 1);

            //设置表格样式
            ArrayList<HSSFCellStyle> styleList = new ArrayList<>();
            ArrayList<Object> totalList = salaryExcelData.get(6);
            HSSFRow examRow = sheet.getRow(changeStartRow - 2);
            //设置的值
            for (int i = 0; i <= 14; i++) {
                HSSFCellStyle cellStyle = examRow.getCell(i).getCellStyle();
                styleList.add(cellStyle);
            }
            //计算小计
            HSSFRow firRow = sheet.createRow(changeStartRow - 1);
            ArrayList<Object> totalDataList = salaryExcelData.get(7);
            for (int index = 0; index < 13; index++) {
                if (index == 0) {
                    HSSFCell tempCell = firRow.createCell(index);
                    tempCell.setCellStyle(styleList.get(index));
                    tempCell.setCellValue("小计");
                } else {
                    HSSFCell tempCell = firRow.createCell(index);
                    tempCell.setCellStyle(styleList.get(index));
                    BigDecimal subNum = (BigDecimal) totalDataList.get(index);
                    BigDecimal nextNum = (BigDecimal) totalList.get(index);
                    tempCell.setCellValue(nextNum.subtract(subNum).doubleValue());
                }
            }

            for (int i = 0; i < salaryExcelData.size(); i++) {
                ArrayList<Object> dataList = salaryExcelData.get(i);
                HSSFRow tempRow = null;
                if (dataList.contains("本页合计")) {
                    continue;
                }

                if (dataList.contains("研发小计")) {
                    tempRow = sheet.createRow(changeStartRow + i - 1);
                } else {
                    tempRow = sheet.createRow(changeStartRow + i);
                }

                for (int index = 0; index < dataList.size(); index++) {
                    if (index < 13) {
                        if (index == 0) {
                            HSSFCell tempCell = tempRow.createCell(index);
                            tempCell.setCellStyle(styleList.get(index));
                            tempCell.setCellValue((String) dataList.get(index));
                        } else {
                            HSSFCell tempCell = tempRow.createCell(index);
                            tempCell.setCellStyle(styleList.get(index));
                            tempCell.setCellValue(((BigDecimal) dataList.get(index)).doubleValue());
                        }
                    }
                }

            }

            //计算本部合计
            HSSFRow secRow = sheet.createRow(changeStartRow + 7);
            HSSFRow jiananRow = sheet.getRow(changeStartRow - 4);
            HSSFRow yanfaRow = sheet.getRow(changeStartRow + 6);
            for (int index = 0; index < 13; index++) {
                if (index == 0) {
                    HSSFCell tempCell = secRow.createCell(index);
                    tempCell.setCellStyle(styleList.get(index));
                    tempCell.setCellValue("建安本部合计");
                } else {
                    HSSFCell tempCell = secRow.createCell(index);
                    tempCell.setCellStyle(styleList.get(index));
                    BigDecimal sumNum = new BigDecimal(yanfaRow.getCell(index).toString());
                    BigDecimal nextNum = new BigDecimal(Double.toString(jiananRow.getCell(index).getNumericCellValue()));
                    tempCell.setCellValue(nextNum.add(sumNum).doubleValue());
                }
            }

            //第二部分
            //计算本部合计
            changeStartRow = emptyList.get(3) + salaryExcelData.size() + 1;
            sheet.shiftRows(changeStartRow - 4, sheet.getLastRowNum(), salaryExcelData.size());

            //计算小计
            firRow = sheet.createRow(changeStartRow - 4);
            for (int index = 10; index < totalDataList.size(); index++) {
                if (index == 10) {
                    HSSFCell tempCell = firRow.createCell(index - 10);
                    tempCell.setCellStyle(styleList.get(0));
                    tempCell.setCellValue("合计");
                } else if (index == 11) {
                    HSSFCell tempCell = firRow.createCell(1);
                    tempCell.setCellStyle(styleList.get(1));
                    BigDecimal subNum = (BigDecimal) totalDataList.get(1);
                    BigDecimal nextNum = (BigDecimal) totalList.get(1);
                    tempCell.setCellValue(nextNum.subtract(subNum).doubleValue());
                } else if (index == 12) {
                    HSSFCell tempCell = firRow.createCell(2);
                    tempCell.setCellStyle(styleList.get(2));
                    BigDecimal subNum = (BigDecimal) totalDataList.get(2);
                    BigDecimal nextNum = (BigDecimal) totalList.get(2);
                    tempCell.setCellValue(nextNum.subtract(subNum).doubleValue());
                } else {
                    HSSFCell tempCell = firRow.createCell(index - 10);
                    tempCell.setCellStyle(styleList.get(index - 10));
                    BigDecimal subNum = (BigDecimal) totalDataList.get(index);
                    BigDecimal nextNum = (BigDecimal) totalList.get(index);
                    tempCell.setCellValue(nextNum.subtract(subNum).doubleValue());
                }
            }

            for (int i = 0; i < salaryExcelData.size(); i++) {
                ArrayList<Object> dataList = salaryExcelData.get(i);
                HSSFRow tempRow = null;
                if (dataList.contains("本页合计")) {
                    continue;
                }

                if (dataList.contains("研发小计")) {
                    tempRow = sheet.createRow(changeStartRow + i - 4);
                } else {
                    tempRow = sheet.createRow(changeStartRow + i - 3);
                }

                for (int index = 10; index < dataList.size(); index++) {
                    if (index == 10) {
                        HSSFCell tempCell = tempRow.createCell(0);
                        tempCell.setCellStyle(styleList.get(0));
                        tempCell.setCellValue((String) dataList.get(0));
                    } else if (index == 11) {
                        HSSFCell tempCell = tempRow.createCell(1);
                        tempCell.setCellStyle(styleList.get(1));
                        tempCell.setCellValue(((BigDecimal) dataList.get(1)).doubleValue());
                    } else if (index == 12) {
                        HSSFCell tempCell = tempRow.createCell(2);
                        tempCell.setCellStyle(styleList.get(2));
                        tempCell.setCellValue(((BigDecimal) dataList.get(2)).doubleValue());
                    } else {
                        HSSFCell tempCell = tempRow.createCell(index - 10);
                        tempCell.setCellStyle(styleList.get(index - 10));
                        tempCell.setCellValue(((BigDecimal) dataList.get(index)).doubleValue());
                    }
                }

            }

//            //计算本部合计
//            HSSFRow secRow = sheet.createRow(changeStartRow + 7);
//            HSSFRow jiananRow = sheet.getRow(changeStartRow - 4);
//            HSSFRow yanfaRow = sheet.getRow(changeStartRow + 6);
//            for (int index = 0; index < 13; index++) {
//                if (index == 0) {
//                    HSSFCell tempCell = secRow.createCell(index);
//                    tempCell.setCellStyle(styleList.get(index));
//                    tempCell.setCellValue("建安本部合计");
//                } else {
//                    HSSFCell tempCell = secRow.createCell(index);
//                    tempCell.setCellStyle(styleList.get(index));
//                    BigDecimal sumNum = new BigDecimal(yanfaRow.getCell(index).toString());
//                    BigDecimal nextNum = new BigDecimal(Double.toString(jiananRow.getCell(index).getNumericCellValue()));
//                    tempCell.setCellValue(nextNum.add(sumNum).doubleValue());
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hssfSheets;
    }

    public static void exportData(String fileName, HSSFWorkbook sheets) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(exportPath + "\\" + fileName));
            sheets.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
    }


    public static ArrayList<ArrayList<Object>> getData(XSSFWorkbook sheets,int month){
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        try {
            for(int i = 0; i < sectionNum * 2; i++){
                list.add(null);
            }
            XSSFSheet sheet = sheets.getSheetAt(0);

            //需要的部门名称
            List<String> sectionNameList = new ArrayList<>();
            sectionNameList.add("起重运输分公司");
            sectionNameList.add("仪表分公司");
            sectionNameList.add("热电维修分公司");
            sectionNameList.add("化肥维修分公司");
            sectionNameList.add("机修分公司");
            sectionNameList.add("电气分公司");
            sectionNameList.add("研发中心(起重运输)");
            sectionNameList.add("研发中心(机修)");
            sectionNameList.add("研发中心(电气)");
            sectionNameList.add("研发中心(仪表)");
            sectionNameList.add("研发中心(热电)");
            sectionNameList.add("研发中心(化肥)");

            //插入研发小计初始值
            ArrayList<Object> totalList = new ArrayList<>();
            totalList.add(0, "研发小计");
            for (int n = 0; n < 19; n++) {
                totalList.add(new BigDecimal("0"));
            }

            int rowNum = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rowNum; i++) {
                if (sheet.getRow(i) != null) {
                    XSSFCell sectionName = sheet.getRow(i).getCell(1);
                    if (sectionName != null && sectionNameList.contains(sectionName.toString())) {
                        if (sectionName.toString().length() >= 4 && "研发中心".equals(sectionName.toString().trim().substring(0, 4))) {
                            ArrayList<Object> dataList = new ArrayList<>();
                            BigDecimal tempNum = null;
                            XSSFCell tempCell = null;
                            XSSFRow row1 = sheet.getRow(i);
                            XSSFRow row2 = sheet.getRow(i + 1);
//                            0-单位
                            dataList.add(0, "研发" + sectionName.toString().substring(5, 7));

//                            1-人数
                            tempCell = row2.getCell(1);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(1, ((BigDecimal) totalList.get(1)).add(tempNum));
//                            2-高新建安人员工资
                            tempCell = row2.getCell(22);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(new BigDecimal(row1.getCell(17).toString())
                                    .subtract(tempNum));
                            totalList.set(2, ((BigDecimal) totalList.get(2)).add(new BigDecimal(row1.getCell(17).toString()).subtract(tempNum)));
//                            3-基本养老保险
                            tempCell = row1.getCell(18);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(3, ((BigDecimal) totalList.get(3)).add(tempNum));
//                            4-失业保险
                            tempCell = row2.getCell(18);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(4, ((BigDecimal) totalList.get(4)).add(tempNum));
//                            5-医疗保险
                            tempCell = row1.getCell(19);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(5, ((BigDecimal) totalList.get(5)).add(tempNum));
//                            6-公积金
                            tempCell = row1.getCell(22);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(6, ((BigDecimal) totalList.get(6)).add(tempNum));
//                            7-房租
                            tempCell = row1.getCell(20);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(7, ((BigDecimal) totalList.get(7)).add(tempNum));
//                            8-税金
                            tempCell = row1.getCell(21);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(8, ((BigDecimal) totalList.get(8)).add(tempNum));
//                            9-风险金
                            tempCell = row2.getCell(21);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(9, ((BigDecimal) totalList.get(9)).add(tempNum));
//                            10-暖气费
                            tempNum = new BigDecimal("0");
                            dataList.add(tempNum);
                            totalList.set(10, ((BigDecimal) totalList.get(10)).add(tempNum));
//                            11-年金
                            tempCell = row2.getCell(20);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(11, ((BigDecimal) totalList.get(11)).add(tempNum));
//                            12-实发
                            tempCell = row1.getCell(23);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(12, ((BigDecimal) totalList.get(12)).add(tempNum));
//                            13-带薪休假补贴
                            tempCell = row2.getCell(6);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(13, ((BigDecimal) totalList.get(13)).add(tempNum));
//                            14-误餐补贴
                            tempCell = row1.getCell(14);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(14, ((BigDecimal) totalList.get(14)).add(tempNum));
//                            15-午餐补贴总额部分
                            tempCell = row2.getCell(1);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString().trim().substring(0, 4) : Double.toString(0));
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum.multiply(new BigDecimal(Double.toString(80))));
                            totalList.set(15, ((BigDecimal) totalList.get(15)).add(tempNum.multiply(new BigDecimal(Double.toString(80)))));
//                            16-午餐补贴进福利费
                            BigDecimal fir = (BigDecimal) dataList.get(14);
                            BigDecimal sec = (BigDecimal) dataList.get(15);
                            tempNum = fir.subtract(sec);
                            dataList.add(tempNum);
                            totalList.set(16, ((BigDecimal) totalList.get(16)).add(tempNum));
//                            17-新职工工资
                            tempNum = new BigDecimal("0");
                            dataList.add(tempNum);
                            totalList.set(17, ((BigDecimal) totalList.get(17)).add(tempNum));
//                            18-高温费
                            tempCell = row1.getCell(7);
                            if(month > 5 && month < 10){
                                tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            }else{
                                tempNum = new BigDecimal(0);
                            }
                            dataList.add(tempNum);
                            totalList.set(18, ((BigDecimal) totalList.get(18)).add(tempNum));
//                            19-住房补贴
                            tempCell = row2.getCell(14);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
                            totalList.set(19, ((BigDecimal) totalList.get(19)).add(tempNum));
//                            19-工资总额
//                            tempNum = ((BigDecimal) dataList.get(2)).subtract(((BigDecimal) dataList.get(13))).subtract(((BigDecimal) dataList.get(14))).add(((BigDecimal) dataList.get(15)));
//                            dataList.add(((BigDecimal) dataList.get(2)).subtract(((BigDecimal) dataList.get(13))).subtract(((BigDecimal) dataList.get(14))).add(((BigDecimal) dataList.get(15))));
//                            totalList.set(19, ((BigDecimal) totalList.get(19)).add(tempNum));

                            list.set(sectionNameList.indexOf(sectionName.toString()),dataList);
                        }else{
                            ArrayList<Object> dataList = new ArrayList<>();
                            BigDecimal tempNum = null;
                            XSSFCell tempCell = null;
                            XSSFRow row1 = sheet.getRow(i);
                            XSSFRow row2 = sheet.getRow(i + 1);
//                            0-单位
                            dataList.add(0, sectionName.toString());
//                            1-人数
                            tempCell = row2.getCell(1);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            2-高新建安人员工资
                            tempCell = row2.getCell(22);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(new BigDecimal(row1.getCell(17).toString())
                                    .subtract(tempNum));
//                            3-基本养老保险
                            tempCell = row1.getCell(18);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            4-失业保险
                            tempCell = row2.getCell(18);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            5-医疗保险
                            tempCell = row1.getCell(19);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            6-公积金
                            tempCell = row1.getCell(22);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            7-房租
                            tempCell = row1.getCell(20);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            8-税金
                            tempCell = row1.getCell(21);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            9-风险金
                            tempCell = row2.getCell(21);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            10-暖气费
                            tempNum = new BigDecimal("0");
                            dataList.add(tempNum);
//                            11-年金
                            tempCell = row2.getCell(20);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            12-实发
                            tempCell = row1.getCell(23);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            13-带薪休假补贴
                            tempCell = row2.getCell(6);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            14-误餐补贴
                            tempCell = row1.getCell(14);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            15-午餐补贴总额部分
                            tempCell = row2.getCell(1);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString().trim().substring(0, 4) : Double.toString(0));
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum.multiply(new BigDecimal(Double.toString(80))));
//                            16-午餐补贴进福利费
                            BigDecimal fir = (BigDecimal) dataList.get(14);
                            BigDecimal sec = (BigDecimal) dataList.get(15);
                            tempNum = fir.subtract(sec);
                            dataList.add(tempNum);
//                            17-新职工工资
                            tempNum = new BigDecimal("0");
                            dataList.add(tempNum);
//                            18-高温费
                            tempCell = row1.getCell(7);
                            if(month > 5 && month < 10){
                                tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            }else{
                                tempNum = new BigDecimal(0);
                            }
                            dataList.add(tempNum);
//                            19-住房补贴
                            tempCell = row2.getCell(14);
                            tempNum = new BigDecimal(tempCell != null && !"".equals(tempCell.toString()) ? tempCell.toString() : Double.toString(0));
                            dataList.add(tempNum);
//                            19-工资总额
//                            dataList.add(((BigDecimal) dataList.get(2)).subtract(((BigDecimal) dataList.get(13))).subtract(((BigDecimal) dataList.get(14))).add(((BigDecimal) dataList.get(15))));

                            list.set(sectionNameList.indexOf(sectionName.toString()),dataList);
                        }
                    }
                }
            }

            list.add(totalList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //修改原表数据
    public static void changeData(ArrayList<ArrayList<Object>> salaryExcelData,String dateMess,HSSFWorkbook hssfSheets) {
        HSSFDataFormat df = hssfSheets.createDataFormat();
        HSSFSheet sheet = hssfSheets.createSheet(dateMess + "高新工资");
        try {
            for (int i = 0; i < salaryExcelData.size(); i++) {
                ArrayList<Object> dataList = salaryExcelData.get(i);
                HSSFRow tempRow = sheet.createRow(i);

                for (int index = 0; index < dataList.size(); index++) {
                    if (index < 13) {
                        if (index == 0) {
                            HSSFCell tempCell = tempRow.createCell(index);
                            tempCell.getCellStyle().setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点;
                            if(i == 0){
                                tempCell.setCellValue(dataList.get(index).toString().substring(0,4));
                            }else{
                                String secName = dataList.get(index).toString();
                                if(secName.substring(0,2).equals("研发")){
                                    tempCell.setCellValue(secName.substring(0,4));
                                }else{
                                    tempCell.setCellValue(dataList.get(index).toString().substring(0,2));
                                }
                            }

                        } else {
                            HSSFCell tempCell = tempRow.createCell(index);
                            tempCell.getCellStyle().setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点;
                            tempCell.setCellValue(((BigDecimal) dataList.get(index)).doubleValue());
                        }
                    }
                }

            }

            //第二部分
            for (int i = 0; i < salaryExcelData.size(); i++) {
                ArrayList<Object> dataList = salaryExcelData.get(i);
                HSSFRow tempRow = sheet.createRow(i + 15);

                for (int index = 10; index < dataList.size(); index++) {
                    if (index == 10) {
                        HSSFCell tempCell = tempRow.createCell(0);
                        tempCell.getCellStyle().setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点;
                        if(i == 0){
                            tempCell.setCellValue(dataList.get(0).toString().substring(0,4));
                        }else{
                            String secName = dataList.get(0).toString();
                            if(secName.substring(0,2).equals("研发")){
                                tempCell.setCellValue(secName.substring(0,4));
                            }else{
                                tempCell.setCellValue(dataList.get(0).toString().substring(0,2));
                            }
                        }

                    } else if (index == 11) {
                        HSSFCell tempCell = tempRow.createCell(1);
                        tempCell.getCellStyle().setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点;
                        tempCell.setCellValue(((BigDecimal) dataList.get(1)).doubleValue());
                    } else if (index == 12) {
                        HSSFCell tempCell = tempRow.createCell(2);
                        tempCell.getCellStyle().setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点;
                        tempCell.setCellValue(((BigDecimal) dataList.get(2)).doubleValue());
                    } else{
                        HSSFCell tempCell = tempRow.createCell(index - 10);
                        tempCell.getCellStyle().setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点;
                        tempCell.setCellValue(((BigDecimal) dataList.get(index)).doubleValue());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void batchExcelDealData(String path) throws IOException {
        File dir = new File(path);
        File[] files = dir.listFiles();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        for(int i = 0; i < files.length; i++){
            String dateMess = files[i].getName().trim().substring(0,6);
            XSSFWorkbook xssfSheets = getXSSFSheets(path, files[i].getName());
            ArrayList<ArrayList<Object>> salaryExcelData = getData(xssfSheets,Integer.parseInt(dateMess.substring(4,6)));
            changeData(salaryExcelData,dateMess,hssfWorkbook);
        }
        exportData("ht" + changeFileName, hssfWorkbook);
    }
}
