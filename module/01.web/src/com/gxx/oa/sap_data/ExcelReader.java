package com.gxx.oa.sap_data;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取Excel
 *
 * @author Gxx
 * @module oa
 * @datetime 14-7-16 15:10
 */
public class ExcelReader {
    /**
     * 导入Excel文件路径
     */
    private static final String IMPORT_EXCEL = "C:\\Users\\sky\\Desktop\\低植易耗品.xls";

    /**
     * 导出Excel文件路径
     */
    private static final String EXPORT_EXCEL = "C:\\Users\\sky\\Desktop\\物料主数据_静态数据准备_低植易耗品.xls";

    /**
     * 读取拼音 js 脚本
     */
    private static final String PIN_YIN_JS = "D:\\04.my_projects\\suncare-oa-ui\\module\\01.web\\src\\com\\gxx\\oa\\sap_data\\pinyin.js";

    /**
     * main函数
     * @param params
     * @throws Exception
     */
    public static void main(String[] params) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(IMPORT_EXCEL));
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFWorkbook workbook2 = new HSSFWorkbook(new FileInputStream(EXPORT_EXCEL));
        HSSFSheet sheet2 = workbook2.getSheetAt(0);
        int count2 = 0;

        int rowNum = sheet.getLastRowNum()+1;
        System.out.println(rowNum);
        String forEachFullName = StringUtils.EMPTY;
        List<String> codeArray = new ArrayList<String>();
        for(int i=0;i<rowNum;i++)
        {
            if(i == 0){
                System.out.println(getHSSFCellValue(sheet.getRow(i).getCell(0)));
                continue;
            }
            HSSFRow tempRow = sheet.getRow(i);//过滤掉第一行抬头
            String code = getHSSFCellValue(tempRow.getCell(0));
            String shortName = getHSSFCellValue(tempRow.getCell(1));
            String fullName = getHSSFCellValue(tempRow.getCell(2));
            /**
             * 2=02.Q0001=其他=原材料_其他
             * 2=02.TB98-01-60-33=上导轨=原材料_上导轨
             * 2=02.TB98-06-60-33=下导轨=原材料_下导轨
             */
            if(StringUtils.isNotBlank(fullName) && fullName.split("_").length != 3 && !fullName.endsWith("系列")){
                System.out.println(fullName.split("_").length + "=" + code + "=" + shortName + "=" + fullName);
            }
            if(StringUtils.isBlank(fullName) || (fullName.split("_").length != 3 && fullName.endsWith("系列"))){
                //System.out.println(code + "=" + shortName + "=" + fullName);
                continue;
            }
            if(StringUtils.equals(forEachFullName, fullName)){
                codeArray.add(code);
            }
            if(!StringUtils.equals(forEachFullName, fullName)){
                if(codeArray.size() > 0){
                    String sameCode = getSameCode(codeArray);
                    String[] strings = forEachFullName.split("_");
                    String name = strings[strings.length - 1];
                    String series = strings.length > 2 ? strings[1] : "";
                    HSSFRow tempRow2 = sheet2.createRow(++count2 + 4);//过滤掉第一行抬头
                    tempRow2.createCell(0);
                    tempRow2.getCell(0).setCellValue(count2);
                    tempRow2.createCell(1);
                    tempRow2.getCell(1).setCellValue(name);
                    tempRow2.createCell(4);
                    tempRow2.getCell(4).setCellValue(series);
                    if(sameCode.indexOf("奥为") > -1){
                        tempRow2.createCell(5);
                        tempRow2.getCell(5).setCellValue("奥为");
                    }
                    if(sameCode.indexOf("旭格") > -1){
                        tempRow2.createCell(5);
                        tempRow2.getCell(5).setCellValue("旭格");
                    }
                    if(sameCode.indexOf("锦绣") > -1){
                        tempRow2.createCell(5);
                        tempRow2.getCell(5).setCellValue("锦绣");
                    }
                    tempRow2.createCell(6);
                    if(sameCode.split("\\.").length > 2){
                        tempRow2.getCell(6).setCellValue(sameCode.split("\\.")[2]);
                    } else {
                        tempRow2.getCell(6).setCellValue(sameCode.split("\\.")[1]);
                    }
                }
                codeArray = new ArrayList<String>();
                codeArray.add(code);
                forEachFullName = fullName;
            }
        }
        FileOutputStream fOut = new FileOutputStream(EXPORT_EXCEL);
        workbook2.write(fOut);
        fOut.flush();
        fOut.close();
        System.out.println("生成结果文件成功！EXPORT_EXCEL=" + EXPORT_EXCEL);
    }

    /**
     * 返回共同的字符串
     * @param codeArrayList
     * @return
     */
    private static String getSameCode(List<String> codeArrayList) {
        if(codeArrayList.size() == 1){
            return codeArrayList.get(0);
        }
        String str = StringUtils.EMPTY;
        for(int i=0;i<codeArrayList.get(0).length();i++){
            str = codeArrayList.get(0).substring(0, i+1);
            for(int j=0;j<codeArrayList.size();j++){
                if(!codeArrayList.get(j).startsWith(str)){
                    String result = str.substring(0, str.length() - 1);
                    if(result.endsWith("-")){
                        result = result.substring(0, result.length()-1);
                    }
                    return result;
                }
            }
        }
        return str;
    }

    /**
     * 得到拼音
     * @param str
     * @return
     * @throws Exception
     */
    public static String getFullPinYin(String str) throws Exception{
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByExtension("js");
        engine.eval(new FileReader(PIN_YIN_JS));//JS文件的绝对路径
        //engine.eval("");//这个可以直接写JS语句：定义变量或者定义JS方法
        Invocable inv = (Invocable) engine;
        String pinYin = String.valueOf(inv.invokeFunction("getFullPinYin", str));//JS文件中加密的方法名 和 密码明文
        return pinYin;
    }

    /**
     * 得到每个字的第一个字符
     * @param str
     * @return
     * @throws Exception
     */
    public static String getAllFirstPinYin(String str) throws Exception{
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByExtension("js");
        engine.eval(new FileReader(PIN_YIN_JS));//JS文件的绝对路径
        //engine.eval("");//这个可以直接写JS语句：定义变量或者定义JS方法
        Invocable inv = (Invocable) engine;
        String firstPinYin = String.valueOf(inv.invokeFunction("getAllFirstPinYin", str));//JS文件中加密的方法名 和 密码明文
        return firstPinYin;
    }

    /**
     * 得到单元格的值
     * @param cell
     * @return
     */
    public static String getHSSFCellValue(HSSFCell cell)
    {
        if(null == cell)
        {
            return StringUtils.EMPTY;
        }
        Object cellValue = null;
//        String[] valueArr = mapvalue.split(",");
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING://字符串类型
                cellValue = cell.getRichStringCellValue().getString();
                if (((String)cellValue).trim().equals("")
                        || ((String)cellValue).trim().length() <= 0) {
                    cellValue = "";
                }
                //cellValue = inputEncode((String)cellValue);
                break;
            case HSSFCell.CELL_TYPE_NUMERIC://数字类型
//                if (valueArr.length == 2 && valueArr[1].equals("date")) {
//                    cellValue = cell.getDateCellValue();;
//                }
//                if (valueArr.length == 2 && valueArr[1].equals("timestamp")) {
//                    Date date = cell.getDateCellValue();
//                    SimpleDateFormat format1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String time=format1.format(date);
//                    cellValue= Timestamp.valueOf(time);
//                }else { // 如果长度为2，说明此列为默认字符串类型
                BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                // cellValue =big.toEngineeringString();
                cellValue = big.toString();
//                }
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                BigDecimal bigula = new BigDecimal(cell
                        .getCachedFormulaResultType());
                // cellValue = bigula.toEngineeringString();
                cellValue = bigula.toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            default:
                break;
        }
        return null == cellValue?"":cellValue.toString();
    }
}
