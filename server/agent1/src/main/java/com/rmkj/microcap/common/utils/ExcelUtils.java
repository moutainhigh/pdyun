package com.rmkj.microcap.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * POI包 工具类
 * 时间字段会自动格式化为 yyyy-MM-dd h24-mm-ss
 * @author renwp
 * 2016年7月20日 下午5:19:59
 */
public final class ExcelUtils {
	
	/**
	 * sheet数据最大值 10000
	 */
	public static int SHEET_MAX_SIZE = 10000;
	
	// 存住 Field
	private static HashMap<String, SoftReference<Field>> FIELD_CACHE = new HashMap<>();

	/**
	 * 画出表格内容
	 * @param wb
	 * @param sheet
	 * @param rownum
	 * @param list
	 * @param clazz
	 * @param columnWidth
	 * @param columnName
	 * @param renderBox
	 */
	public static void drawExcelBody(HSSFWorkbook wb, HSSFSheet sheet, int rownum, List<?> list, Class<?> clazz, int[] columnWidth, String[] columnName, RenderBox renderBox) {
		// 设置每列宽度
		for (int i = 0; i < columnWidth.length; i++) {
			sheet.setColumnWidth(i, columnWidth[i]);
		}
		
		// 开始画表内容
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		int columnTotal = columnName.length;
		HSSFRow createRow = null;
		HSSFCell createCell = null;
		
		String name = clazz.getName();
		String key = null;
		SoftReference<Field> softReference = null;
		Field declaredField = null;
		for (Object obj : list) {
			createRow = sheet.createRow(rownum++);
			
			for (int column = 0; column < columnTotal; column++) {
				createCell = createRow.createCell(column);
				createCell.setCellStyle(cellStyle);

				if(renderBox != null){
					Render render = renderBox.get(columnName[column]);
					if(render != null){
						createCell.setCellValue(render.format(obj));
						continue;
					}
				}

				declaredField = null;
				key = name.concat(".").concat(columnName[column]);
				softReference = FIELD_CACHE.get(key);
				if(softReference != null){
					declaredField = FIELD_CACHE.get(key).get();
				}
				if(declaredField == null){
					try {
						declaredField = clazz.getDeclaredField(columnName[column]);
						if(declaredField != null){
							FIELD_CACHE.put(key, new SoftReference<Field>(declaredField));
						}
					} catch (NoSuchFieldException | SecurityException e) {
						e.printStackTrace();
					}
				}

				if(declaredField != null) {
					createCell.setCellValue(getColumn(declaredField, obj).toString());
				}
			}
		}
	}
	
	public static void drawExcelHeader(HSSFWorkbook wb, HSSFSheet sheet, int rownum, String[] columnHeader){
		// 开始画表头
		Font font = wb.createFont();     
        //设置字体样式   
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle cellHeaderStyle = wb.createCellStyle();
		cellHeaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellHeaderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellHeaderStyle.setFont(font);
		
		HSSFCell headerCell = null;
		HSSFRow headerRow = sheet.createRow(rownum++);
		// 表头高度
		headerRow.setHeight((short)(20*30));
		for (int i = 0; i < columnHeader.length; i++) {
			headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(cellHeaderStyle);
			headerCell.setCellValue(columnHeader[i]);
		}
	}
	
	/**
	 * 反射获取bean的属性值
	 * @param declaredField
	 * @param obj
	 * @return
	 */
	public static Object getColumn(Field declaredField, Object obj){
		try {
			if(declaredField != null){
				if(!declaredField.isAccessible()){
					declaredField.setAccessible(true);
				}
				Object object = declaredField.get(obj);
				if(object instanceof Date){
					return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", object);
				}
				if(object == null){
					return "";
				}
				return object;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 
	 * @param fileName
	 * @param response
	 * @param wb
	 */
	public static void exportExcel(String fileName, HttpServletResponse response, HSSFWorkbook wb){
		ServletOutputStream writer = null;
		try {
			fileName = fileName.concat(DateUtils.getDateTime().replaceAll(" |:|-", ""));
			//描述
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(fileName+".xls", "utf-8"));
			//输出文件的类型
	       	response.setContentType("application/msexcel");
	       	response.setCharacterEncoding("utf-8");
	       	writer = response.getOutputStream();
	       	
			wb.write(writer);
			writer.flush();
			writer.close();
			writer = null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}				
		}
	}

	public static void mapper(List<?> list, Class clazz,
							  HSSFWorkbook wb, String sheetName, String[] columnHeaders, String[] columnNames, int[] columnWidths) {
		mapper(list, clazz, wb, sheetName, columnHeaders, columnNames, columnWidths, null, 0);
	}

	public static void mapper(List<?> list, Class clazz,
							  HSSFWorkbook wb, String sheetName, String[] columnHeaders, String[] columnNames, int[] columnWidths, RenderBox renderBox) {
		mapper(list, clazz, wb, sheetName, columnHeaders, columnNames, columnWidths, renderBox, 0);
	}

	public static void mapper(List<?> list, Class clazz,
							  HSSFWorkbook wb, String sheetName, String[] columnHeaders, String[] columnNames, int[] columnWidths, int beginRow) {
		mapper(list, clazz, wb, sheetName, columnHeaders, columnNames, columnWidths, null, beginRow);
	}

	public static void mapper(List<?> list, Class clazz,
							  HSSFWorkbook wb, String sheetName, String[] columnNames, int[] columnWidths, int beginRow) {
		mapper(list, clazz, wb, sheetName, null, columnNames, columnWidths, null, beginRow);
	}

	/**
	 * 映射
	 * @param list
	 * @param clazz
	 * @param wb
	 * @param sheetName
	 * @param columnHeaders
	 * @param columnNames
	 * @param columnWidths
	 * @param renderBox
	 * @param beginRow
	 */
	public static void mapper(List<?> list, Class clazz,
							  HSSFWorkbook wb, String sheetName, String[] columnHeaders, String[] columnNames, int[] columnWidths, RenderBox renderBox, int beginRow) {
		HSSFSheet sheet = wb.createSheet(sheetName);
		if(columnHeaders != null){
			drawExcelHeader(wb, sheet, beginRow++, columnHeaders);
		}
		drawExcelBody(wb, sheet, beginRow, list, clazz, columnWidths, columnNames, renderBox);
	}

	public static class RenderBox{
		private Map<String, Render> map = new HashMap<>();

		public RenderBox add(String column, Render render){
			map.put(column, render);
			return this;
		}

		public Render get(String column){
			return map.get(column);
		}
	}

	public interface Render{
		String format(Object obj);
	}

	/**
	 *
	 * @param dataFromDB
	 * @param wb
	 * @param columnHeaders
	 * @param columnNames
	 * @param columnWidths
	 * @param renderBox
	 */
	public static void work(DataFromDB dataFromDB, Class clazz, HSSFWorkbook wb, String[] columnHeaders, String[] columnNames, int[] columnWidths, RenderBox renderBox){
		List list = dataFromDB.find();
		ExcelUtils.mapper(list, clazz, wb, "第1页", columnHeaders, columnNames, columnWidths, renderBox);
		long totalPage = dataFromDB.totalPage();
		for (int i = 2; i <= totalPage; i++) {
			list = dataFromDB.find();
			ExcelUtils.mapper(list, clazz, wb, "第"+(i)+"页", columnHeaders, columnNames, columnWidths, renderBox);
		}
	}

	public interface DataFromDB<T>{
		List<T> find();
		long totalPage();
	}

}
