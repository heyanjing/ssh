package com.he.ssh.base.core.poi;

import com.alibaba.fastjson.JSON;
import com.he.ssh.base.bean.Result;
import com.he.ssh.base.core.Guava;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/4 10:20.
 */
public class Excel {
    private static final Logger log = LoggerFactory.getLogger(Excel.class);

//    **********************************************************************************************************************************
//    ***********************************// MEINFO:2017/12/21 10:12 导出 2007的excel**********************************************************************************
//    **********************************************************************************************************************************

    /**
     * web的导出功能
     *
     * @param fileName  导出文件名
     * @param sheetname sheet名称
     * @param dataList  写入excel的数据
     * @param titleList 标题与标题对应的属性名称的对象集合
     * @param response  response
     * @param request   request
     * @throws Exception Exception
     */
    public static void exportExcel2007(String fileName, String sheetname, List<?> dataList, List<Title> titleList, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Workbook xssfWorkbook = Excel.getExprotXSSFWorkbook(sheetname, dataList, titleList);
        response.reset();
        response.setContentType("application/msexcel;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String((fileName + ".xlsx").getBytes("GB2312"), "ISO8859-1") + "\"");
        OutputStream out = response.getOutputStream();
        xssfWorkbook.write(out);
        out.flush();
        out.close();

    }

    /**
     * 获取导出需要的
     *
     * @param sheetname sheet名称
     * @param dataList  写入excel的数据
     * @param titleList 标题与标题对应的属性名称的对象集合
     * @return XSSFWorkbook
     */
    @SuppressWarnings("unchecked")
    public static Workbook getExprotXSSFWorkbook(String sheetname, List<?> dataList, List<Title> titleList) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFSheet sheet = workbook.createSheet(sheetname);
        sheet.setDefaultColumnWidth(20);
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            XSSFCell cell = titleRow.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(titleList.get(i).getTitle());
        }
        for (int i = 0; i < dataList.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            Object data = dataList.get(i);
            Map<String, String> map = null;
            if (data instanceof Map) {
                map = convertMap((Map<String, Object>) data);
            } else {
                map = getFieldValueMap(data);
            }
            for (int j = 0; j < titleList.size(); j++) {
                row.createCell(j).setCellValue(map.get(titleList.get(j).getProp()));
            }
        }
        return workbook;
    }

    public static Map<String, String> convertMap(Map<String, Object> map) {
        return convertMap(map, true);
    }

    /**
     * @param map
     * @param isDate
     * @return 将原有map的值进行格式化处理
     */
    public static Map<String, String> convertMap(Map<String, Object> map, Boolean isDate) {
        Map<String, String> result = new HashMap<>();
        map.forEach((key, val) -> {
            String strVal = null;
            if (val instanceof Date) {
                DateFormat dateFormat = isDate ? DateFormat.getDateInstance() : DateFormat.getDateTimeInstance();
                strVal = dateFormat.format(val);
            } else if (val instanceof Double) {
                strVal = new DecimalFormat("0.00").format(val);
            } else {
                if (null != val && !val.equals("")) {
                    strVal = String.valueOf(val);
                } else {
                    strVal = "-";
                }
            }
            result.put(key, strVal);
        });

        return result;
    }

    /**
     * @param bean   实例对象
     * @param isDate 是否格式化成2017-09-12
     *               2017-09-12 12:12:12
     *               默认2017-09-12
     * @return 对象字段 对应的值的map集合
     */
    public static Map<String, String> getFieldValueMap(Object bean, Boolean isDate) {
        Class<?> cls = bean.getClass();
        Map<String, String> valueMap = new HashMap<>();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                String fieldType = field.getType().getSimpleName();
                String fieldGetName = Excel.parGetName(field.getName());
                Method fieldGetMet = null;
                for (Method met : methods) {
                    if (met.getName().equals(fieldGetName)) {
                        fieldGetMet = met;
                        break;
                    }
                }
                if (fieldGetMet != null) {
                    Object fieldVal = fieldGetMet.invoke(bean);
                    String result = null;
                    if ("Date".equals(fieldType)) {
                        DateFormat dateFormat = isDate ? DateFormat.getDateInstance() : DateFormat.getDateTimeInstance();
                        result = dateFormat.format(fieldVal);
                    } else if ("Double".equals(fieldType)) {
                        result = new DecimalFormat("0.00").format(fieldVal);
                    } else {
                        if (null != fieldVal && !fieldVal.equals("")) {
                            result = String.valueOf(fieldVal);
                        } else {
                            result = "-";
                        }
                    }
                    valueMap.put(field.getName(), result);
                }
            } catch (Exception e) {
                log.info("{}", e);
            }
        }
        return valueMap;
    }

    public static Map<String, String> getFieldValueMap(Object bean) {
        return getFieldValueMap(bean, true);
    }

    /**
     * @param fieldName
     * @return 字段对应的get方法名
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "get" + fieldName.substring(startIndex, startIndex + 1).toUpperCase() + fieldName.substring(startIndex + 1);
    }

    //**********************************************************************************************************************************
//******************************************// MEINFO:2017/12/21 10:13 导入 2003 或2007的excel*************************************************************************
//**********************************************************************************************************************************

    /**
     * @param inputStream excel的输入流
     * @param name        文件名
     * @param head        excel的表头
     * @return Result 的data中存放读取的数据List<Object> headCellValues
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static Result getWorkbookResult(InputStream inputStream, String name, Object[] head) throws IOException, InvalidFormatException {
        Result result = Result.success();
        Workbook wb = null;
        if (name.endsWith(".xls")) {
            wb = new HSSFWorkbook(new POIFSFileSystem(inputStream));
        } else if (name.endsWith(".xlsx")) {
            wb = new XSSFWorkbook(OPCPackage.open(inputStream));
        } else {
            return Result.failure();
        }
        Sheet sheet = wb.getSheetAt(0);
        int realRowNumber = getRealRowNumber(sheet);
        Row row = sheet.getRow(0);
        List<Object> headCellValues = getCellValues(row);

        //region Description
        log.info("{}", Arrays.asList(head));
        log.info("{}", headCellValues);
        log.info("{}", Arrays.equals(head, headCellValues.toArray()));
        //endregion
        if (Arrays.equals(head, headCellValues.toArray())) {
            List<List<Object>> cellValuesList = getCellValuesList(sheet, realRowNumber);
            //["CL2017000001","通用设备","轿车","轿车","2017-11-30","2017年9月20号、11月31号","2017-11-30 00:00:00","原值","1","0.00","198,000.00","新购","在用","自用","","","","","0.000000","重庆市城市建设综合开发管理办公室","2017-12-14 10:18:00","SVW71810BU","","不提折旧","平均年限法","0.000","198,000.00","0","","领导实物用车","","","","LSVD76A43HN117083","上海","121555"]
            log.info(JSON.toJSONString(cellValuesList));
            result.setData(cellValuesList);
            return result;
        } else {
            return Result.failure("请下载标准模板");
        }
    }

    /**
     * @param sheet
     * @param realRowNumber
     * @return 获取整个sheet的数据
     */
    public static List<List<Object>> getCellValuesList(Sheet sheet, int realRowNumber) {
        List<List<Object>> cellValuesList = Guava.newArrayList();
        for (int i = 1; i <= realRowNumber; i++) {
            Row rowi = sheet.getRow(i);
            List<Object> cellValues = getCellValues(rowi);
            log.error("****************");
            cellValuesList.add(cellValues);
        }
        return cellValuesList;
    }

    /**
     * @param sheet
     * @return 移除空白行，获取真实行数
     */
    public static int getRealRowNumber(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < lastRowNum; i++) {
            Row row1 = sheet.getRow(i);
            if (row1 == null) {
                sheet.shiftRows(i + 1, lastRowNum, -1);
                lastRowNum = sheet.getLastRowNum();
                i = i - 1;
            } else {
                Boolean isRemove = true;//默认移除该行
                for (Cell cell : row1) {
                    if (cell.getCellTypeEnum() != CellType.BLANK) {
                        isRemove = false;
                        break;
                    }
                }
                if (isRemove) {
                    if (i == sheet.getLastRowNum()) {
                        sheet.removeRow(row1);
                    } else {
                        sheet.shiftRows(i + 1, lastRowNum, -1);
                        lastRowNum = sheet.getLastRowNum();
                        i = i - 1;
                    }
                }
            }
        }
        return sheet.getLastRowNum();
    }

    /**
     * @param row
     * @return 获取一行的数据
     */
    public static List<Object> getCellValues(Row row) {
        List<Object> list = Guava.newArrayList();
        for (Cell cell : row) {
            Object cellValue = getCellValue(cell);
            list.add(cellValue);
        }
        return list;
    }

    /**
     * @param cell
     * @return 获取一个单元格的值
     */
    public static Object getCellValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            CellType cellType = cell.getCellTypeEnum();
            log.warn("{}", cellType);
            //region Description
            switch (cellType) {
                case STRING:
                    cellValue = cell.getStringCellValue().trim();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue();
                    } else {
                        cellValue = cell.getNumericCellValue();
                    }
                    break;
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    cell.getCellFormula();
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                case ERROR:
                    cellValue = "";
                    break;
                case _NONE:
                    cellValue = "";
                    break;
                default:
                    cellValue = "";
                    break;
            }
            //endregion
        } else {
            cellValue = "";
        }
        return cellValue;
    }


}
