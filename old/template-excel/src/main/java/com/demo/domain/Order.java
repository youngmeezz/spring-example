package com.demo.domain;

import java.util.Date;

import com.demo.annotation.ExcelField;
import com.demo.annotation.ExcelFieldType;

import lombok.Data;

@Data
public class Order {
    @ExcelField(cellOrder=0, notNull = false, cellValue="Order Number")
    private Integer orderNumber;
    
    @ExcelField(cellOrder=2, notNull = false, fieldType=ExcelFieldType.Object)
    private Product product;
    
    @ExcelField(cellOrder=1, notNull = false, cellValue="Order Date")
    private Date orderDate;
    
    @ExcelField(cellOrder=3, notNull = false, cellValue="Order Name")
    private String orderName;
    
    @ExcelField(cellOrder=4, notNull = false, fieldType=ExcelFieldType.Object)
    private Customer customer;
    
    private String notExcelField;
}