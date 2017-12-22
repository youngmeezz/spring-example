package com.demo.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.demo.domain.Customer;
import com.demo.domain.ExcelPersistentEntity;

public class ExcelReadByReflection {
	
	public static void main(String[] args) {
		String[] filepaths = new String[] {
				"D:\\excel-test\\customers_reflection.xls",
				"D:\\excel-test\\customers_reflection_not_matched_header.xls"
		};
		
		for(String path : filepaths) {
			System.out.println("## check " + path);
			List<Customer> customers = readFromExcel(path, Customer.class);
			if(customers == null || customers.size() == 0) {
				System.out.println("# result customers size is 0");
				continue;
			}
			else {
				System.out.println("## result customers size is " + customers.size());
			}
			
			for(Customer customer : customers) {
				System.out.println(customer);
			}			
		}		
	}
	
	
	public static <T> List<T> readFromExcel(String filepath, Class<T> clazz) {
		Workbook wb = getWorkbook(filepath);
		if(wb == null) {
			System.out.println("## [Workbook is null] filepath : " + filepath);
			return Collections.emptyList();
		}
		
		System.out.println("## number of sheets : " + wb.getNumberOfSheets());
		
		Sheet sheet = wb.getSheetAt(0);
		if(sheet == null) {
			System.out.println("## [sheet is null]");
			return Collections.emptyList();
		}
		
		// check header (read parse error)
		int rowsCnt = sheet.getPhysicalNumberOfRows();
		if(rowsCnt < 1) {
			System.out.println("## [rows cnt less than 1] rowsCnt : " + rowsCnt);
			return Collections.emptyList();
		}
		int rowsIdx = 0;
		boolean matchedHeader = checkHeadersOrder(sheet.getRow(rowsIdx++), clazz);
		System.out.println("## [check headers order] result : " + matchedHeader);
		if(!matchedHeader) {
			return Collections.emptyList(); 
		}
		
		// read row & parse to Class
		List<T> insts = new ArrayList<>(rowsCnt);
		IntStream.range(rowsIdx, rowsCnt).forEach(rowIdx -> {
			Row row = sheet.getRow(rowIdx);
			if(row == null) {
				return;
			}
			
			try {				
				T t = clazz.newInstance();
				for (int cellIdx = row.getFirstCellNum(); cellIdx < row.getLastCellNum(); cellIdx++) {
					Cell cell = row.getCell(cellIdx);
					List<ExcelPersistentEntity> metaDatas = ExcelWriteByReflection.metaDataMap.get(clazz);
					if (metaDatas == null) {
						metaDatas = ExcelWriteByReflection.putExcelMetaData(clazz);
						ExcelWriteByReflection.metaDataMap.put(clazz, metaDatas);
					}

					ExcelPersistentEntity metaData = metaDatas.get(cellIdx);
					Field field = metaData.getField();
					Object value = getValueFromCell(cell, field);
					if (value == null && metaData.isNotNull()) {
						System.out.println( "## [parse error : Cell to Instance] Not Null Value : " + metaData.getCellName());
						return;
					}
					
					field.setAccessible(true);
					field.set(t, value);
				}
				
				insts.add(t);				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return insts;		
	}
	
	private static Object getValueFromCell(Cell cell, Field field) {
		if(cell == null) {
			return null;
		}
		Class<?> fieldType = field.getType();
		Object value = null;
		switch(cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK :
				System.out.println("CELL_TYPE_BLANK :: ");
				value = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN :
				value = Boolean.valueOf(cell.getBooleanCellValue());
				System.out.println("CELL_TYPE_BOOLEAN :: ");					
				break;				
			case Cell.CELL_TYPE_NUMERIC :
				double doubleValue = cell.getNumericCellValue();
				System.out.println("doubleValue :: " + doubleValue);
				if(fieldType.isAssignableFrom(Integer.class)) {
					value = Integer.valueOf((int)doubleValue);					
				}
				else if(fieldType.isAssignableFrom(Date.class) && HSSFDateUtil.isCellDateFormatted(cell)) {
					value = cell.getDateCellValue();
				}
				else {
					value = Double.valueOf(cell.getNumericCellValue());					
				}
				System.out.println("CELL_TYPE_NUMERIC :: ");
				break;
			case Cell.CELL_TYPE_STRING :
				value = cell.getStringCellValue(); 
				System.out.println("CELL_TYPE_STRING :: ");				
				break;
			case Cell.CELL_TYPE_FORMULA :
				value = cell.getCellFormula(); 
				System.out.println("CELL_TYPE_FORMULA :: ");					
				break;
			case Cell.CELL_TYPE_ERROR :
				value = Byte.valueOf(cell.getErrorCellValue());
				System.out.println("CELL_TYPE_ERROR :: ");
				break;
			default : 
				System.out.println("CELL_TYPE_RROR OR DEFAULT");
				break;
		}
		
		return value;
	}
	
	// header 체크 clazz의 order만 일치하면 맞는 걸로 (즉, 1~6이 기본 + 7,8이 쓸 데 없는거 넣어도 무시)
	private static boolean checkHeadersOrder(Row row, Class<?> clazz) {
		List<ExcelPersistentEntity> metaDatas = ExcelWriteByReflection.metaDataMap.get(clazz);
		if(metaDatas == null) {
			metaDatas = ExcelWriteByReflection.putExcelMetaData(clazz); 
		}
		
		if(metaDatas.isEmpty()) {
			System.out.println("## [not exist ExcelMetaData list] class name : " + clazz.getName());
			return false;
		}
		
		if(metaDatas.size() != row.getPhysicalNumberOfCells()) {
			System.out.println("## diff metaDatas size and row.getPhysicalNumberOfCells");
			return false;
		}
		
		int cellCnt = 0;
		for(ExcelPersistentEntity metaData : metaDatas) {
			Cell cell = row.getCell(cellCnt);
			if(cell == null) {
				return false;
			}
			
			if(!metaData.getCellName().equals(cell.getStringCellValue())) {
				return false;
			}
			cellCnt++;
		}
		
		return true;
	}
	
	
	private static Workbook getWorkbook(String filepath) {		
		try(FileInputStream fis = new FileInputStream(filepath)) {
			String lowerCase = filepath.toLowerCase();			
			
			if(lowerCase.endsWith(".xls")) {
				return new HSSFWorkbook(fis);
			}
			else if(lowerCase.endsWith(".xlsx")) {
				return new HSSFWorkbook(fis);
			}
			
			return null;
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}