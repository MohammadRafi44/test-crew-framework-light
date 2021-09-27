package com.example.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestResult;

public class ExcelManager {

  private static final Logger LOGGER = LogManager.getLogger(ExcelManager.class);
  private static final List<Map<String, String>> excelRow;

  static {
    excelRow = getExcelRow();
  }

  public static synchronized List<Map<String, String>> getControllerRowsList() {
    return excelRow;
  }

  private static synchronized List<Map<String, String>> getExcelRow() {
    FileInputStream fileInputStream;
    List<Map<String, String>> rowMapList;
    try {
      fileInputStream = new FileInputStream(Constants.RUN_MANAGER_WORKBOOK);
      Workbook workbook = new XSSFWorkbook(fileInputStream);
      Sheet sheet = workbook.getSheet(Constants.CONTROLLER_SHEET_NAME);
      List<String> headerList = new LinkedList<>();
      for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
        headerList.add(sheet.getRow(0).getCell(i).getStringCellValue());
      }
      LOGGER.debug("Headers list [{}]", headerList);
      rowMapList = new LinkedList<>();
      for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
        Row row = sheet.getRow(i);
        Map<String, String> rowMap = new LinkedHashMap<>();
        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
          Cell cell = row.getCell(j);
          if (StringUtils.isNotBlank(getCellValue(cell)) && !getCellValue(cell).equals("P_Key")) {
            rowMap.put(headerList.get(j), getCellValue(cell));
            LOGGER.debug("Added Key : [{}] | Value [{}] to row map", headerList.get(j), getCellValue(cell));
          } else {
            break;
          }
        }
        rowMapList.add(rowMap);
      }
      fileInputStream.close();
    } catch (IOException e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
    return rowMapList.stream().filter(map -> map.size() > 0).collect(Collectors.toList());
  }

  public static synchronized List<Map<String, String>> getExcelRowsAsListOfMap(String excelWorkbookName, String excelSheetName,
      String testMethodName) {
    FileInputStream fileInputStream;
    List<Map<String, String>> rowMapList = new LinkedList<>();
    try {
      fileInputStream = new FileInputStream(excelWorkbookName);
      Workbook workbook = new XSSFWorkbook(fileInputStream);
      Sheet sheet = workbook.getSheet(excelSheetName);
      List<String> headerList = new LinkedList<>();
      for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
        headerList.add(sheet.getRow(0).getCell(i).getStringCellValue());
      }
      LOGGER.debug("Headers list [{}]", headerList);
      for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
        Row row = sheet.getRow(i);
        Map<String, String> rowMap = new LinkedHashMap<>();
        for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
          Cell cell = row.getCell(j);
          if (StringUtils.isNotBlank(getCellValue(cell)) && getCellValue(sheet.getRow(i).getCell(1)).equals(testMethodName)) {
            rowMap.put(headerList.get(j), getCellValue(cell));
            LOGGER.debug("Added Key : [{}] | Value [{}] to row map", headerList.get(j), getCellValue(cell));
          } else {
            break;
          }
        }
        if (rowMap.size() > 0) {
          rowMapList.add(rowMap);
        }
      }
      fileInputStream.close();
    } catch (IOException e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
    return rowMapList;
  }

  protected static synchronized void writeTestStatusToExcel(ITestResult result) {
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(Constants.RUN_MANAGER_WORKBOOK);
      Workbook workbook = new XSSFWorkbook(fileInputStream);
      Sheet sheet = workbook.getSheet(Constants.CONTROLLER_SHEET_NAME);
      Map<String, Integer> headersMap = new LinkedHashMap<>();
      for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
        headersMap.put(sheet.getRow(0).getCell(i).getStringCellValue(), i);
      }
      for (int columnIndex = 1; columnIndex < sheet.getPhysicalNumberOfRows(); columnIndex++) {
        if (StringUtils.isNotBlank(sheet.getRow(columnIndex).getCell(headersMap.get("TestMethodName")).getStringCellValue())) {
          String testMethodName = sheet.getRow(columnIndex).getCell(headersMap.get("TestMethodName")).getStringCellValue();
          String executeFlag = sheet.getRow(columnIndex).getCell(headersMap.get("Execute")).getStringCellValue();
          if (executeFlag.equalsIgnoreCase("yes") && result.getMethod().getMethodName().equals(testMethodName)) {
            String status = result.getStatus() == ITestResult.FAILURE ? "Failed" : "Passed";
            sheet.getRow(columnIndex).getCell(headersMap.get("Status")).setCellValue(status);
          }
        }
      }
      fileInputStream.close();
      FileOutputStream fileOutputStream = new FileOutputStream(Constants.RUN_MANAGER_WORKBOOK);
      workbook.write(fileOutputStream);
      fileOutputStream.close();
    } catch (Exception e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
    LOGGER.debug("Successfully wrote back test result to TestRunner sheet");
  }

  public static synchronized void writeToExcelColumn(Map<String, String> data, String sheetName, String columnName, String columnValueToSet) {
    writeToExcelColumn(Constants.RUN_MANAGER_WORKBOOK, sheetName, data.get("TestMethodName"), columnName,
        columnValueToSet);
  }

  public static synchronized void writeToExcelColumn(String workBookPath, String sheetName, String testMethodName, String columnName,
      String columnValueToSet) {
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(workBookPath);
      Workbook workbook = new XSSFWorkbook(fileInputStream);
      Sheet sheet = workbook.getSheet(sheetName);
      Map<String, Integer> headersMap = new LinkedHashMap<>();
      for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
        headersMap.put(sheet.getRow(0).getCell(i).getStringCellValue(), i);
      }
      for (int columnIndex = 1; columnIndex < sheet.getPhysicalNumberOfRows(); columnIndex++) {
        String runTimePKey = sheet.getRow(columnIndex).getCell(headersMap.get("TestMethodName")).getStringCellValue();
        if (runTimePKey.equals(testMethodName)) {
          sheet.getRow(columnIndex).getCell(headersMap.get(columnName)).setCellValue(columnValueToSet);
        }
      }
      fileInputStream.close();
      FileOutputStream fileOutputStream = new FileOutputStream(workBookPath);
      workbook.write(fileOutputStream);
      fileOutputStream.close();
    } catch (Exception e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
    LOGGER.debug("Successfully wrote to excel");
  }

  protected static Map<String, String> getControllerRowMapByTestMethodName(String testMethodName) {
    return excelRow.stream().filter(map -> map.get("TestMethodName").equals(testMethodName)).collect(Collectors.toList()).get(0);
  }

  private static String getCellValue(Cell cell) {
    return cell.getCellType().equals(CellType.NUMERIC) ? String.valueOf((int) cell.getNumericCellValue()) : cell.getStringCellValue();
  }

}
