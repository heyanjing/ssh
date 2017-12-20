package com.he.ssh.web.controller;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by heyanjing on 2017/12/20 11:06.
 */
@Controller
@RequestMapping("/file/upload")
public class FileUploadController {
    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
    public static Object[] head = {"资产编号", "资产大类", "资产分类", "资产名称", "财务入账日期", "会计凭证号", "取得日期", "价值类型", "数量", "单价", "价值", "取得方式", "使用状况", "使用方向", "使用部门", "管理部门", "存放地点", "使用人", "面积", "制单人", "制单时间", "规格型号", "产权形式", "折旧状态", "折旧方法", "累计折旧", "净值", "折旧年限", "自定义编号", "备注", "清查编号", "原资产编号", "车辆行驶证所有人", "车辆识别号", "车辆产地", "发动机号"};


    /**
     * @http /file/upload
     */
    @GetMapping("/")
    public String index() {
        return "/upload/webuploader";
    }

    @RequestMapping("/excel/import")
    @ResponseBody
    public Result uploadFile(@RequestParam("file") MultipartFile[] files) {


        try {
            for (MultipartFile file : files) {
                InputStream inputStream = file.getInputStream();
                String name = file.getOriginalFilename();
                //region Description
                log.info(file.getContentType());//application/vnd.ms-excel
                log.info(file.getName());//file
                log.info(file.getOriginalFilename());//卡片列表 - 副本.xls
                log.info(file.getSize() + "");//19456
                log.info("{}", file.isEmpty());//false
                //endregion
                Result workbookResult = this.getWorkbookResult(inputStream, name);


            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return null;
    }

    private Result getWorkbookResult(InputStream inputStream, String name) throws IOException, InvalidFormatException {
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
        int realRowNumber = this.getRealRowNumber(sheet);
        Row row = sheet.getRow(0);
        List<Object> headCellValues = this.getCellValues(row);

        //region Description
        log.info("{}", Arrays.asList(head));
        log.info("{}", headCellValues);
        log.info("{}", Arrays.equals(head, headCellValues.toArray()));
        //endregion
        if (Arrays.equals(head, headCellValues.toArray())) {
            List<List<Object>> cellValuesList = this.getCellValuesList(sheet, realRowNumber);
            //["CL2017000001","通用设备","轿车","轿车","2017-11-30","2017年9月20号、11月31号","2017-11-30 00:00:00","原值","1","0.00","198,000.00","新购","在用","自用","","","","","0.000000","重庆市城市建设综合开发管理办公室","2017-12-14 10:18:00","SVW71810BU","","不提折旧","平均年限法","0.000","198,000.00","0","","领导实物用车","","","","LSVD76A43HN117083","上海","121555"]
            log.info(JSON.toJSONString(cellValuesList));
            result.setData(cellValuesList);
            return result;
        } else {
            return Result.failure("请下载标准模板");
        }
    }

    private List<List<Object>> getCellValuesList(Sheet sheet, int realRowNumber) {
        List<List<Object>> cellValuesList = Guava.newArrayList();
        for (int i = 1; i <= realRowNumber; i++) {
            Row rowi = sheet.getRow(i);
            List<Object> cellValues = this.getCellValues(rowi);
            log.error("****************");
            cellValuesList.add(cellValues);
        }
        return cellValuesList;
    }

    private int getRealRowNumber(Sheet sheet) {
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

    private List<Object> getCellValues(Row row) {
        List<Object> list = Guava.newArrayList();
        for (Cell cell : row) {
            Object cellValue = this.getCellValue(cell);
            list.add(cellValue);
        }
        return list;
    }

    private Object getCellValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            CellType cellType = cell.getCellTypeEnum();
            log.warn("{}",cellType);
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
