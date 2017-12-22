package com.demo.domain;

import java.util.Date;

import com.demo.annotation.ExcelField;

import lombok.Data;

@Data
public class Customer {
	@ExcelField(cellOrder = 0, cellValue ="Customers Sequence", notNull = true)
	private Integer seq;	
	@ExcelField(cellOrder = 1, cellValue ="Customers Name")
	private String name;
	@ExcelField(cellOrder = 2, cellValue ="Customers cell phone")
	private String cellphone;
	@ExcelField(cellOrder = 3, cellValue ="Customers Email")
	private String email;
	@ExcelField(cellOrder = 4, cellValue ="Customers Reg date")
	private Date regDate;
	
	private String addr;
}