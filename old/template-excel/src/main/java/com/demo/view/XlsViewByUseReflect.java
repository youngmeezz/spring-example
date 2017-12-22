package com.demo.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.demo.domain.Customer;
import com.demo.excel.ExcelWriteByReflection;

@Component("xlsView")
public class XlsViewByUseReflect extends AbstractXlsView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		
		// change the file name
		response.setHeader("Content-Disposition", "attachment;filename=\"my-xls-file.xls\"");
		
		@SuppressWarnings("unchecked")
		List<Customer> customers = (List)model.get("customers");
		
		ExcelWriteByReflection.buildExcelDocument(customers, workbook);
	}
}