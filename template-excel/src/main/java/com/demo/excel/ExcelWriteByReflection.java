package com.demo.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.DateFormatConverter;

import com.demo.annotation.ExcelField;
import com.demo.domain.ExcelPersistentEntity;

public class ExcelWriteByReflection {
	public static Map<Class<?>, List<ExcelPersistentEntity>> metaDataMap = new Hashtable<>();
	
	/*
	public static void main(String[] args) {
		System.out.println("## param is String");		
		buildExcelDocument(Arrays.asList("T", "t2"), null);
		
		System.out.println("## Generic is Object");
		Object obj = new Object();
		List objs = new ArrayList();
		objs.add(obj);
		buildExcelDocument(objs,null);
		
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer());	
		buildExcelDocument(customers,null);		
	}
	*/
	
	public static <T> void buildExcelDocument(List<T> list, Workbook workbook) {
		if(workbook == null) {
			System.out.println("## workbook is null");
			//return;
		}
		
		if(list == null || list.size() == 0) {
			System.out.println("## list is empty");
			return;
		}
		
		Class<T> clazz = (Class<T>)list.get(0).getClass();
		System.out.println("## generic class T`s name : " + clazz.getName());
		List<ExcelPersistentEntity> metaDatas = metaDataMap.get(clazz);
		if(metaDatas == null) {
			metaDatas = putExcelMetaData(clazz);
			if(metaDatas.isEmpty()) {
				System.out.println("## not exist @ExcelField");
				return;
			}
			System.out.println("## push cache class : " + clazz.getName());
			metaDataMap.put(clazz, metaDatas);
		}
		else {
			System.out.println("## cache used class : " + clazz.getName());
		}
		
		// date format
		String excelFormatPattern = DateFormatConverter.convert(Locale.KOREA, "yyyy-MM-dd, HH:mm");
		System.out.println("excelFormatPattern :: " + excelFormatPattern);
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();		
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(excelFormatPattern));

		// create sheet
		Sheet sheet = workbook.createSheet("Spring MVC AbstractXlsView");
		
		// write first row
		int rowCnt = 0;
		Row header = sheet.createRow(rowCnt++);
		for(ExcelPersistentEntity metaData : metaDatas) {
			header.createCell(metaData.getOrder()).setCellValue(metaData.getCellName());			
		}
		
		// write inst datas
		for(T inst : list) {
			try {
				Row row = sheet.createRow(rowCnt++);
				for(ExcelPersistentEntity metaData : metaDatas) {
					Field field = metaData.getField();
					field.setAccessible(true);
					Object value = field.get(inst);
					Cell cell = row.createCell(metaData.getOrder());
					
					// temp code :: check Object -> Cast
					if(value instanceof Boolean) {
						cell.setCellValue((Boolean)value);						
					}					
					else if(value instanceof Integer) {
						cell.setCellValue((Integer)value);
					}
					else if(value instanceof Date) {
						cell.setCellValue((Date)value);
						cell.setCellStyle(cellStyle);
					}
					else if(value instanceof String) {
						cell.setCellValue((String)value);
					}
					else {						
						System.out.println("## not exist value type " + metaData.getField().getName());
					}
				}
				
				autoSizeColumns(workbook);
			} 
			catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	private static void autoSizeColumns(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
	    for (int i = 0; i < numberOfSheets; i++) {
	        Sheet sheet = workbook.getSheetAt(i);
	        if (sheet.getPhysicalNumberOfRows() > 0) {
	            Row row = sheet.getRow(0);
	            Iterator<Cell> cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                int columnIndex = cell.getColumnIndex();
	                sheet.autoSizeColumn(columnIndex);
	            }
	        }
	    }		
	}
	
	
	public static List<ExcelPersistentEntity> putExcelMetaData(Class<?> clazz) {
		// extract meta data
		PriorityQueue<ExcelPersistentEntity> fieldQueue = new PriorityQueue<ExcelPersistentEntity>(new Comparator<ExcelPersistentEntity>() {
			@Override
			public int compare(ExcelPersistentEntity o1, ExcelPersistentEntity o2) {
				return o1.getOrder() - o2.getOrder();
			}
		});

		for (Field field : clazz.getDeclaredFields()) {
			ExcelField excelField = field.getAnnotation(ExcelField.class);
			if (excelField == null) {
				continue;
			}
			ExcelPersistentEntity metaData = new ExcelPersistentEntity();
			metaData.setField(field);
			metaData.setOrder(excelField.cellOrder());
			metaData.setCellName(excelField.cellValue());
			metaData.setNotNull(excelField.notNull());
			fieldQueue.offer(metaData);
		}
		
		if(fieldQueue.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<ExcelPersistentEntity> ret = new ArrayList<>(fieldQueue.size());
		while(!fieldQueue.isEmpty()) {
			ret.add(fieldQueue.poll());
		}
		
		return ret;
	}	
}
