import com.libstar.lsp.support.util.DateUtil;
import com.libstar.lsp.support.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导出excel工具类
 */
public class ExportExcelUtil<T> {
    /**
     * excel导出
     *
     * @param title   excel名称
     * @param headers 标题
     * @param dataset 需要导出的集合
     * @param out     导出流
     */
    public void exportExcel(String title, String[] headers,
                            Collection<T> dataset, OutputStream out) {
        // 声明一个工作薄
        Workbook workbook = null;
        if (CollectionUtils.size(dataset) < 5000) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook();
        }
        // 生成一个表格
        Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(19);

        // 生成表头样式
        CellStyle headStyle = workbook.createCellStyle();
        // 设置样式
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 生成一个字体
        Font font = workbook.createFont();
        font.setBold(true);
        // 把字体应用到当前的样式
        headStyle.setFont(font);

        // 生成单元格并设置样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        // 产生表格标题行
        Row row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});

                    // 判断值的类型后进行强制类型转换
                    String textValue = "";

                    if (value instanceof Date) {
                        textValue = DateUtil.formatDateTime((Date) value);
                    } else if (value != null) {
                        textValue = value.toString();
                    }

                    // 判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            RichTextString richString = new XSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 导出---带标题
     *
     * @param title excel名称
     * @param bigTitle 大标题
     * @param countTitle 统计行数据
     * @param headers 列标题
     * @param dataset 导出的集合
     * @param out     流
     */
    public void exportExcel(String title,String bigTitle,String countTitle, String[] headers,
                            Collection<T> dataset, OutputStream out) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(18);

        // 生成表头样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        // 设置样式
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        // 把字体应用到当前的样式
        headStyle.setFont(font);

        // 生成单元格并设置样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);


        //生成大标题样式
        HSSFCellStyle firstHeaderRowStyle = workbook.createCellStyle();
        // 设置样式
        firstHeaderRowStyle.setAlignment(HorizontalAlignment.CENTER);
        firstHeaderRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成一个字体样式
        HSSFFont firstHeaderRowFont = workbook.createFont();
        firstHeaderRowFont.setBold(true);
        firstHeaderRowFont.setFontHeightInPoints((short)14);
        // 把字体应用到当前的样式
        firstHeaderRowStyle.setFont(firstHeaderRowFont);

        HSSFRow firstHeaderRow=sheet.createRow(0);// 第一行
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,headers.length-1));
        HSSFCell yearCell=firstHeaderRow.createCell(0);
        yearCell.setCellValue(bigTitle);
        yearCell.setCellStyle(firstHeaderRowStyle);

        //生成count标题样式
        HSSFCellStyle nextHeaderRowStyle=workbook.createCellStyle();
        // 设置样式
        nextHeaderRowStyle.setAlignment(HorizontalAlignment.LEFT);
        nextHeaderRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成一个字体样式
        HSSFFont nextHeaderRowFont = workbook.createFont();
        // nextHeaderRowFont.setBold(true);
        // 把字体应用到当前的样式
        nextHeaderRowStyle.setFont(nextHeaderRowFont);
        nextHeaderRowStyle.setWrapText(true);

        HSSFRow nextHeaderRow=sheet.createRow(2);
        sheet.addMergedRegion(new CellRangeAddress(2,3,0,headers.length-1));
        HSSFCell dateCell=nextHeaderRow.createCell(0);
        if(StringUtils.isEmpty(countTitle)){
            dateCell.setCellValue("");
        }else {
            dateCell.setCellValue(countTitle);
        }
        dateCell.setCellStyle(nextHeaderRowStyle);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(4);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 4;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});

                    // 判断值的类型后进行强制类型转换
                    String textValue = "";

                    if (value instanceof Date) {
                        textValue = DateUtil.formatDateTime((Date) value);
                    } else if (value != null) {
                        textValue = value.toString();
                    }

                    // 判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
