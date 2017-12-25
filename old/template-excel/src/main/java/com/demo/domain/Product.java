package com.demo.domain;

import com.demo.annotation.ExcelField;
import com.demo.annotation.ExcelFieldType;

import lombok.Data;

@Data
public class Product {
    @ExcelField(cellOrder=0, cellValue="Product no")
    private Long pno;
    @ExcelField(cellOrder=1, cellValue="Product name")
    private String pName;
    @ExcelField(cellOrder=2, fieldType= ExcelFieldType.Object)
    ProductDetail detail;
    @ExcelField(cellOrder=3, cellValue="Product price")
    private Integer price;
}
