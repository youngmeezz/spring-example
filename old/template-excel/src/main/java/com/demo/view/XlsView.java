package com.demo.view;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.demo.domain.Customer;

// test write basic excel file
@Component("xlsView-basic")
public class XlsView extends AbstractXlsView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		long startTime = System.currentTimeMillis();
		// change the file name
		response.setHeader("Content-Disposition", "attachment;filename=\"my-xls-file.xls\"");
		
		@SuppressWarnings("unchecked")
		List<Customer> customers = (List)model.get("datas");
		
		// create excel xls sheet
		Sheet sheet = workbook.createSheet("Spring MVC AbstractXlsView");
		
		// create header row
		Row header = sheet.createRow(0);
		
		createCell(header,0,"seq");
		createCell(header,1,"Name");
		createCell(header,2,"Email");
		createCell(header,3,"cellphone");
		
		int rowCnt = 1;
		for(Customer customer : customers) {
			Row customerRow = sheet.createRow(rowCnt++);
			customerRow.createCell(0).setCellValue(customer.getSeq());
			customerRow.createCell(1).setCellValue(customer.getName());
			customerRow.createCell(2).setCellValue(customer.getEmail());
			customerRow.createCell(3).setCellValue(customer.getCellphone());			
		}
		
		autoSizeColumns(workbook);
		
		System.out.println("## create time :: " + (System.currentTimeMillis() - startTime));
	}
	
	private void autoSizeColumns(Workbook workbook) {
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
	
	private void createCell(Row row, int idx, String value) {
		Cell cell = row.createCell(idx);
		CellStyle style=  cell.getCellStyle();
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
}
