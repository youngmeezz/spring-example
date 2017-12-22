package com.demo.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import com.demo.domain.Customer;

import lombok.extern.java.Log;

@Component("xlsxStreamView")
@Log
public class XlsxStreamingView extends AbstractXlsxStreamingView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
    	
    	log.info("## buildExcelDocument");    	
    	// change the file name
		response.setHeader("Content-Disposition", "attachment;filename=\"my-xls-file.xlsx\"");

		@SuppressWarnings("unchecked")
		List<Customer> customers = (List) model.get("customers");

		// create excel xls sheet
		Sheet sheet = workbook.createSheet("Spring MVC AbstractXlsView");
		
		// create header row
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Seq");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Email");
		header.createCell(3).setCellValue("cellphone");

		int rowCnt = 1;
		for (Customer customer : customers) {
			Row customerRow = sheet.createRow(rowCnt++);
			customerRow.createCell(0).setCellValue(customer.getSeq());
			customerRow.createCell(1).setCellValue(customer.getName());
			customerRow.createCell(2).setCellValue(customer.getEmail());
			customerRow.createCell(3).setCellValue(customer.getCellphone());
		}
    }
	
}