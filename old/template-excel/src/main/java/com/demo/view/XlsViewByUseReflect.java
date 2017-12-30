package com.demo.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.demo.service.ExcelService;

import lombok.extern.java.Log;

@Component("xlsView")
@Log
public class XlsViewByUseReflect extends AbstractXlsView {
    @Autowired
    ExcelService excelService;
    
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		
		log.info("## [buildExcelDocument]");
		// change the file name
		response.setHeader("Content-Disposition", "attachment;filename=\"my-xls-file.xls\"");		
		excelService.buildDocument((List)model.get("datas"), workbook);
	}
}