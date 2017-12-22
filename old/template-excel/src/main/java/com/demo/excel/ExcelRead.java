package com.demo.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

public class ExcelRead {
	
	public static void main(String[] args) {
		readCustomerFromExcel("D:\\excel-test\\customers.xls");
	}
	
	public static void readCustomerFromExcel(String filepath) {		
		Workbook wb = getWorkbook(filepath);
		if(wb == null) {
			System.out.println("## [Workbook is null] filepath : " + filepath);
			return;
		}
		
		System.out.println("## number of sheets : " + wb.getNumberOfSheets());
		
		Sheet sheet = wb.getSheetAt(0);
		if(sheet == null) {
			System.out.println("## [sheet is null]");
			return;
		}
		
		System.out.println("## [exist sheet] sheet name : " + sheet.getSheetName());
		int rowsCnt = sheet.getPhysicalNumberOfRows();
		System.out.println("## sheet`s row count : " + rowsCnt);
		int cellsCnt = 0;
		
		// rows
		IntStream.range(0, rowsCnt).forEach(rowIdx -> {
			System.out.println("### --- display rows--- ###");
			// read first row		
			Row row = sheet.getRow(rowIdx);			
			System.out.println("## current rows`s first cell num : " + row.getFirstCellNum() + " , last cell num : " + row.getLastCellNum());

			// cells
			IntStream.range(row.getFirstCellNum(), row.getLastCellNum()).forEach(cellIdx -> {
				Cell cell = row.getCell(cellIdx);
				System.out.print(String.format("convertNumToColString : %s , enum name : %s , enum ordinal : %d" , 
						CellReference.convertNumToColString(cell.getColumnIndex())
						,cell.getCellTypeEnum().name()
						,cell.getCellTypeEnum().ordinal()
						));
				System.out.print(", cell type : ");
				Object value = null;				
				switch(cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK :					
						System.out.print("CELL_TYPE_BLANK :: ");
						value = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN :
						value = Boolean.valueOf(cell.getBooleanCellValue());
						System.out.print("CELL_TYPE_BOOLEAN :: ");					
						break;				
					case Cell.CELL_TYPE_NUMERIC :
						value = Double.valueOf(cell.getNumericCellValue());
						System.out.print("CELL_TYPE_NUMERIC :: ");
						break;
					case Cell.CELL_TYPE_STRING :
						value = cell.getStringCellValue(); 
						System.out.print("CELL_TYPE_STRING :: ");				
						break;
					case Cell.CELL_TYPE_FORMULA :
						value = cell.getCellFormula(); 
						System.out.print("CELL_TYPE_FORMULA :: ");					
						break;
					case Cell.CELL_TYPE_ERROR :
						value = Byte.valueOf(cell.getErrorCellValue());
						System.out.print("CELL_TYPE_ERROR :: ");
						break;
					default : 
						System.out.print("CELL_TYPE_RROR OR DEFAULT");
						break;
				}
				System.out.println(value);
			});
		});
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
