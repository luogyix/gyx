package com.agree.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExcelUtils {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	/**
	 * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
	 */
	public static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) {
		String agent = request.getHeader("USER-AGENT");
		try {
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				pFileName = URLEncoder.encode(pFileName, "utf-8");
			} else {
				pFileName = new String(pFileName.getBytes("utf-8"), "iso8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return pFileName;
	}
	
	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断JDBC类型：Oracle
	 * 
	 * @return
	 */
	public static boolean isOracle() {
		boolean out = false;
		String jdbcType = System.getProperty("eRedg4.JdbcType");
		if (jdbcType.equalsIgnoreCase("oracle")) {
			out = true;
		}
		return out;
	}

	/**
	 * 判断JDBC类型：Mysql
	 * 
	 * @return
	 */
	public static boolean isMysql() {
		boolean out = false;
		String jdbcType = System.getProperty("eRedg4.JdbcType");
		if (jdbcType.equalsIgnoreCase("mysql")) {
			out = true;
		}
		return out;
	}
	/**
	 * 返回当前日期时间字符串<br>
	 * 默认格式:yyyy-mm-dd hh:mm:ss
	 * 
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}
	
	/**
	 * 读取 office 2003 excel
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unused")
	private static List<List<Object>> read2003Excel(File file, String baseTag) throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hwb.getSheet(baseTag);
		logger.info(""+hwb.getNumberOfSheets());
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
			
			row = sheet.getRow(i);
			
			if (row == null) continue;
			
			List<Object> linked = new LinkedList<Object>();
			
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				
				cell = row.getCell(j);
				
				if (cell == null)  continue;
				
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
															// 字符
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				
				DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
				
				switch (cell.getCellType()) {
				
					case XSSFCell.CELL_TYPE_STRING:
//						logger.info(i + "行" + j + " 列 is String type");
						value = cell.getStringCellValue();
						break;
						
					case XSSFCell.CELL_TYPE_NUMERIC:
//						logger.info(i + "行" + j
//								+ " 列 is Number type ; DateFormt:"
//								+ cell.getCellStyle().getDataFormatString());
						if ("@".equals(cell.getCellStyle().getDataFormatString())) 
							value = df.format(cell.getNumericCellValue());
						  else 
							if ("General".equals(cell.getCellStyle().getDataFormatString()))
								value = nf.format(cell.getNumericCellValue());
							  else 
								value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						break;
						
					case XSSFCell.CELL_TYPE_BOOLEAN:
//						logger.info(i + "行" + j + " 列 is Boolean type");
						value = cell.getBooleanCellValue();
						break;
						
					case XSSFCell.CELL_TYPE_BLANK:
//						logger.info(i + "行" + j + " 列 is Blank type");
						value = "";
						logger.info("空白");
						break;
						
					default:
//						logger.info(i + "行" + j + " 列 is default type");
						value = cell.toString();
				}
				
//				if (value == null || "".equals(value)) continue;
				
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}
	
	
	/**
	 * 读取Office 2007 excel
	 * */
	@SuppressWarnings("unused")
	private static List<List<Object>> read2007Excel(File file, String baseTag) throws IOException {
		
		List<List<Object>> list = new LinkedList<List<Object>>();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheet(baseTag);
		logger.info("" + xwb.getNumberOfSheets());
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
			
			row = sheet.getRow(i);
			
			if (row == null) {
				continue;
			}
			
			List<Object> linked = new LinkedList<Object>();
			
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				
				cell = row.getCell(j);
				
				if (cell == null)  continue;
				
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
															// 字符
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				
				DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
				
				switch (cell.getCellType()) {
				
					case XSSFCell.CELL_TYPE_STRING:
//						logger.info(i + "行" + j + " 列 is String type");
						value = cell.getStringCellValue();
//						logger.info(value);
						break;
						
					case XSSFCell.CELL_TYPE_NUMERIC:
//						logger.info(i + "行" + j
//								+ " 列 is Number type ; DateFormt:"
//								+ cell.getCellStyle().getDataFormatString());
						if ("@".equals(cell.getCellStyle().getDataFormatString()))
							value = df.format(cell.getNumericCellValue());
						  else 
							if ("General".equals(cell.getCellStyle().getDataFormatString())) 
								value = nf.format(cell.getNumericCellValue());
							else 
								value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						break;
						
					case XSSFCell.CELL_TYPE_BOOLEAN:
//						logger.info(i + "行" + j + " 列 is Boolean type");
						value = cell.getBooleanCellValue();
						break;
						
					case XSSFCell.CELL_TYPE_BLANK:
//						logger.info(i + "行" + j + " 列 is Blank type");
						value = "";
						break;
						
					default:
//						logger.info(i + "行" + j + " 列 is default type");
						value = cell.toString();
				}
				
				if (value == null || "".equals(value)) continue;
				
				linked.add(value);
			}
			
			list.add(linked);
		}
		return list;
	}
	
	/** 
     * 获取单元格数据内容为字符串类型的数据 
     * @param cell Excel单元格 
     * @return String 单元格数据内容 
     */  
    public static String getStringCellValue(Cell cell) {  
    	if (cell == null) {  
    		return "";  
    	}  
        String strCell = "";  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_STRING:  
            strCell = cell.getStringCellValue();  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
        	double number = cell.getNumericCellValue();
        	int num = (int)number;
            strCell = String.valueOf(num);  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            strCell = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case Cell.CELL_TYPE_BLANK:  
            strCell = "";  
            break;  
        default:  
            strCell = "";  
            break;  
        }  
        if (strCell.equals("") || strCell == null) {  
            return "";  
        }  
        return strCell;  
    }
    
    /** 
     * 获取单元格数据内容为日期类型的数据 
     * @param cell Excel单元格 
     * @return String 单元格数据内容 
     */  
    @SuppressWarnings("deprecation")
	public static String getDateCellValue(Cell cell) {  
        String result = "";  
        try {  
            int cellType = cell.getCellType();  
            if (cellType == Cell.CELL_TYPE_NUMERIC) {  
                Date date = cell.getDateCellValue();  
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)   
                + "-" + date.getDate();  
            } else if (cellType == Cell.CELL_TYPE_STRING) {  
                String date = getStringCellValue(cell);  
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();  
            } else if (cellType == Cell.CELL_TYPE_BLANK) {  
                result = "";  
            }  
        } catch (Exception e) {  
            logger.info("日期格式不正确!",e	);  
        }  
        return result;  
    }  
}
