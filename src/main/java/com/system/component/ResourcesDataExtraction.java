package com.jlja.excel.hightech.utils;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ResourcesDataExtraction {

    static ArrayList<String> sections = new ArrayList<String>() {{
        add("机修分公司");
        add("电气分公司");
        add("仪表分公司");
        add("起重运输分公司");
        add("化肥维修分公司");
        add("热电维修分公司");
    }};

    //设置月数据
    static int startDate = 201801;
    static int lastDate = 202012;

    public static void main(String[] args) throws IOException {
        String filePath = "C:\\lipeng\\财务\\高新\\调账\\材料\\originalResourcesData.xlsx";
        String sheetName = "Sheet1";
        String exportPath = "C:\\lipeng\\财务\\高新\\调账\\材料\\resourcesData.xlsx";
        XSSFWorkbook sheets = getSheets(filePath);
        Map<String, Map<String, Map<String, Double>>> dataMap = getResourcesExcelData(sheets, sheetName);
        List<Map.Entry<String, Map<String, Map<String, Double>>>> dataList = mapSort(dataMap);
        exportExcel(exportPath, dataList);

    }

    //获取需要的数据和列数的关系
    public static Map<Integer, String> getDataNum(XSSFRow row) {
        Map<Integer, String> dataNumMap = new HashMap<>();
        int columns = row.getPhysicalNumberOfCells();
        for (int j = 0; j < columns; j++) {
            String cell = row.getCell(j).toString().trim();
            if (cell.equals("材料编号")) {
                dataNumMap.put(j, "材料编号");
            } else if (cell.equals("记帐月份")) {
                dataNumMap.put(j, "记帐月份");
            } else if (cell.equals("领料单位")) {
                dataNumMap.put(j, "领料单位");
            } else if (cell.equals("出库金额")) {
                dataNumMap.put(j, "出库金额");
            }
        }
        //有填充色列
        dataNumMap.put(20, "");
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
    public static Map<String, Map<String, Map<String, Double>>> getResourcesExcelData(XSSFWorkbook sheets, String sheetName) {
        Map<String, Map<String, Map<String, Double>>> dataMap = new HashMap<>();
        Map<Integer, String> dataNumMap = null;
        List<Map<String, String>> tempDataList = new ArrayList<>();

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
                    Map<String, String> rowMap = new HashMap<>();
                    //判断填充色
                    XSSFCell checkCell = row.getCell(20);
                    short color = checkCell.getCellStyle().getFillForegroundColor();
                    if (color == 40) {//40为蓝色
                        continue;
                    }
                    //获取所需数据
                    dataNumMap.forEach((key, value) -> {
                        String cell = null;

                        if (value.equals("材料编号")) {
                            cell = row.getCell(key).toString().trim().substring(0, 2);
                            rowMap.put("材料编号", cell);
                        } else if (value.equals("记帐月份")) {
                            cell = row.getCell(key).toString().trim();
                            rowMap.put("记帐月份", cell);
                        } else if (value.equals("领料单位")) {
                            cell = row.getCell(key).toString().trim();
                            rowMap.put("领料单位", cell);
                        } else if (value.equals("出库金额")) {
                            cell = row.getCell(key).toString().trim();
                            rowMap.put("出库金额", cell);
                        }
                    });
                    if (rowMap != null && !rowMap.isEmpty()) {
                        tempDataList.add(rowMap);
                    }
                }
            }

            //处理表格数据
            tempDataList.forEach(rowData -> {
                String resourcesNum = rowData.get("材料编号");
                String month = rowData.get("记帐月份");
                String section = rowData.get("领料单位");
                String money = rowData.get("出库金额");

                if (dataMap.containsKey(resourcesNum)) {
                    Map<String, Map<String, Double>> monthData = dataMap.get(resourcesNum);

                    if (monthData.containsKey(month)) {
                        Map<String, Double> sectiuonData = monthData.get(month);

                        if (sectiuonData.containsKey(section)) {
                            sectiuonData.put(section, sectiuonData.get(section) + Double.parseDouble(money));
//                            Double oriMoney = sectiuonData.get(section);
//                            oriMoney += Double.parseDouble(money);
                        } else {
                            sectiuonData.put(section, Double.parseDouble(money));
                        }

                    } else {
                        monthData.put(month, new HashMap<String, Double>() {{
                            put(section, Double.parseDouble(money));
                        }});
                    }

                } else {
                    dataMap.put(resourcesNum, new HashMap<String, Map<String, Double>>() {{
                        put(month, new HashMap<String, Double>() {{
                            put(section, Double.parseDouble(money));
                        }});
                    }});
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    //数据排序
    public static List<Map.Entry<String, Map<String, Map<String, Double>>>> mapSort(Map<String, Map<String, Map<String, Double>>> dataMap) {
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Map<String, Map<String, Double>>>> list = new ArrayList<Map.Entry<String, Map<String, Map<String, Double>>>>(dataMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Map<String, Map<String, Double>>>>() {
            //升序排序
            public int compare(Map.Entry<String, Map<String, Map<String, Double>>> o1,
                               Map.Entry<String, Map<String, Map<String, Double>>> o2) {
                return Integer.parseInt(o1.getKey()) - Integer.parseInt(o2.getKey());
            }
        });
        return list;
    }

    //导出
    public static void exportExcel(String exportPath, List<Map.Entry<String, Map<String, Map<String, Double>>>> dataList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();        //创建一个sheet工作表
        XSSFSheet sheet = workbook.createSheet("材料数据");
        Map<String, Map<String, Double>> statisticsMoneyMap = new HashMap<>();
        try {
            int dataLength = dataList.size();
            //设置单元格水平和垂直居中格式
            XSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

            //设置数值单元格水平和垂直居中格式
            XSSFCellStyle numStyle = workbook.createCellStyle();
            XSSFDataFormat df = workbook.createDataFormat();
            style.setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

            //部门总数
            int sectionTotalNum = sections.size();

            for (int i = 0; i < dataLength; i++) {

                XSSFRow row1 = null;
                XSSFRow row2 = null;

                if (i == 0) {
                    CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);
                    sheet.addMergedRegion(region);
                    row1 = sheet.createRow(0);
                    row2 = sheet.createRow(1);
                    XSSFCell titleCell = row1.createCell(0);
                    titleCell.setCellStyle(style);
                    titleCell.setCellValue("材料编号");
                }

                XSSFRow row3 = sheet.createRow(i + 2);

                //设置材料编号数据
                XSSFCell resourcesNumCell = row3.createCell(0);
                resourcesNumCell.setCellStyle(style);
                resourcesNumCell.setCellValue(dataList.get(i).getKey());
                Map<String, Map<String, Double>> monthData = dataList.get(i).getValue();
                //金额总计
                BigDecimal totalMoney = new BigDecimal(Double.toString(0));

                //总计月份
                int totalMonth = 0;

                //设置月数据
                int tempDate = startDate;
                int endDate = lastDate;

                for (int j = 0; tempDate <= endDate; tempDate += (tempDate % 100 >= 12 ? 89 : 1)) {
                    totalMonth++;

                    //只有第一次插入初始值
                    if (i == 0) {
                        //插入按月统计数据
                        HashMap<String, Double> secMap = new HashMap<String, Double>() {{
                            sections.forEach(section -> {
                                put(section, 0d);
                            });
                        }};
                        secMap.put(tempDate + "合计", 0d);
                        statisticsMoneyMap.put(String.valueOf(tempDate), secMap);
                    }


                    //插入月数据
                    if (i == 0) {
                        CellRangeAddress monthRegion = new CellRangeAddress(0, 0, (j * (sectionTotalNum + 1)) + 1, (j + 1) * (sectionTotalNum + 1));
                        sheet.addMergedRegion(monthRegion);
                        XSSFCell monthCell = row1.createCell((j * (sectionTotalNum + 1)) + 1);
                        monthCell.setCellStyle(style);
                        monthCell.setCellValue(String.valueOf(tempDate));
                    }

                    //每月金额求和
                    BigDecimal sumMoney = new BigDecimal(Double.toString(0));
                    if (monthData.containsKey(String.valueOf(tempDate))) {

                        Map<String, Double> sectionData = monthData.get(String.valueOf(tempDate));
                        for (int k = 0; k < sectionTotalNum; k++) {
                            String section = sections.get(k);
                            if (sectionData.containsKey(section)) {
                                //部门
                                if (i == 0) {
                                    XSSFCell sectionCell = row2.createCell((j * (sectionTotalNum + 1)) + 1 + k);
                                    sectionCell.setCellStyle(style);
                                    sectionCell.setCellValue(String.valueOf(section));
                                }

                                //金额
                                XSSFCell moneyCell = row3.createCell((j * (sectionTotalNum + 1)) + 1 + k);
                                moneyCell.setCellStyle(numStyle);
                                moneyCell.setCellValue(sectionData.get(section));
                                sumMoney = sumMoney.add(new BigDecimal(Double.toString(sectionData.get(section))));

                                statisticsMoneyMap.get(String.valueOf(tempDate)).put(section,
                                        new BigDecimal(Double.toString(statisticsMoneyMap.get(String.valueOf(tempDate)).get(section)))
                                                .add(new BigDecimal(Double.toString(sectionData.get(section)))).doubleValue());

                            } else {
                                //部门
                                if (i == 0) {
                                    XSSFCell sectionCell = row2.createCell((j * (sectionTotalNum + 1)) + 1 + k);
                                    sectionCell.setCellStyle(style);
                                    sectionCell.setCellValue(String.valueOf(section));
                                }

                                //金额
                                XSSFCell moneyCell = row3.createCell((j * (sectionTotalNum + 1)) + 1 + k);
                                moneyCell.setCellStyle(numStyle);
                                moneyCell.setCellValue(0d);

                            }
                        }
                        //合计
                        if (i == 0) {
                            XSSFCell sectionCell = row2.createCell((j + 1) * (sectionTotalNum + 1));
                            sectionCell.setCellStyle(style);
                            sectionCell.setCellValue(String.valueOf(tempDate) + "合计");
                        }

                        //金额
                        XSSFCell moneyCell = row3.createCell((j + 1) * (sectionTotalNum + 1));
                        moneyCell.setCellStyle(numStyle);
                        moneyCell.setCellValue(sumMoney.doubleValue());
                        totalMoney = totalMoney.add(sumMoney);

                        statisticsMoneyMap.get(String.valueOf(tempDate)).put(tempDate + "合计",
                                new BigDecimal(Double.toString(statisticsMoneyMap.get(String.valueOf(tempDate)).get(tempDate + "合计")))
                                        .add(sumMoney).doubleValue());
                    } else {
                        for (int k = 0; k < sectionTotalNum; k++) {
                            String section = sections.get(k);
                            //部门
                            if (i == 0) {
                                XSSFCell sectionCell = row2.createCell((j * (sectionTotalNum + 1)) + 1 + k);
                                sectionCell.setCellStyle(style);
                                sectionCell.setCellValue(String.valueOf(section));
                            }

                            //金额
                            XSSFCell moneyCell = row3.createCell((j * (sectionTotalNum + 1)) + 1 + k);
                            moneyCell.setCellStyle(numStyle);
                            moneyCell.setCellValue(0d);
                        }
                        //合计
                        if (i == 0) {
                            XSSFCell sectionCell = row2.createCell((j + 1) * (sectionTotalNum + 1));
                            sectionCell.setCellStyle(style);
                            sectionCell.setCellValue(String.valueOf(tempDate) + "合计");
                        }

                        //金额
                        XSSFCell moneyCell = row3.createCell((j + 1) * (sectionTotalNum + 1));
                        moneyCell.setCellStyle(numStyle);
                        moneyCell.setCellValue(sumMoney.doubleValue());
                        totalMoney = totalMoney.add(sumMoney);

                        statisticsMoneyMap.get(String.valueOf(tempDate)).put(tempDate + "合计",
                                new BigDecimal(Double.toString(statisticsMoneyMap.get(String.valueOf(tempDate)).get(tempDate + "合计")))
                                        .add(sumMoney).doubleValue());
                    }
                    j++;
                }
                //计算总计
                if (i == 0) {
                    CellRangeAddress monthRegion = new CellRangeAddress(0, 1, (totalMonth * (sectionTotalNum + 1)) + 1, (totalMonth * (sectionTotalNum + 1)) + 1);
                    sheet.addMergedRegion(monthRegion);
                    XSSFCell totalTextCell = row1.createCell((totalMonth * (sectionTotalNum + 1)) + 1);
                    totalTextCell.setCellStyle(style);
                    totalTextCell.setCellValue("总计");
                }

                //插入总计数据
                XSSFCell moneyCell = row3.createCell((totalMonth * (sectionTotalNum + 1)) + 1);
                moneyCell.setCellStyle(numStyle);
                moneyCell.setCellValue(totalMoney.doubleValue());
            }

            //按部门输出总计
            XSSFRow totalRow = sheet.createRow(dataList.size() + 2);
            XSSFCell totCell = totalRow.createCell(0);
            totCell.setCellStyle(style);
            totCell.setCellValue("总计");

            BigDecimal lastSumMoney = new BigDecimal("0");

            int firDate = startDate;
            int behDate = lastDate;

            int totalMonth = 0;

            for (int t = 0; firDate <= behDate; firDate += (firDate % 100 >= 12 ? 89 : 1)) {
                totalMonth++;
                for (int k = 0; k < sectionTotalNum; k++) {
                    Map<String, Double> sectionMap = statisticsMoneyMap.get(String.valueOf(firDate));
                    XSSFCell sumCell = totalRow.createCell(t * (sectionTotalNum + 1) + 1 + k);
                    sumCell.setCellStyle(numStyle);
                    sumCell.setCellValue(sectionMap.get(sections.get(k)));
                }
                XSSFCell sumCell = totalRow.createCell((t + 1) * (sectionTotalNum + 1));
                sumCell.setCellStyle(numStyle);
                sumCell.setCellValue(statisticsMoneyMap.get(String.valueOf(firDate)).get(firDate + "合计"));
                lastSumMoney = lastSumMoney.add(new BigDecimal(
                        Double.toString(statisticsMoneyMap.get(String.valueOf(firDate)).get(firDate + "合计"))
                ));
                t++;
            }
            XSSFCell moneyCell = totalRow.createCell((totalMonth * (sectionTotalNum + 1)) + 1);
            moneyCell.setCellStyle(numStyle);
            moneyCell.setCellValue(lastSumMoney.doubleValue());

        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fs = null;
        try {
            //导出
            File file = new File(exportPath);
            fs = new FileOutputStream(file);
            workbook.write(fs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fs.close();
        }
    }
}

