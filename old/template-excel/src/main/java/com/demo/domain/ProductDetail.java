package com.demo.domain;

import com.demo.annotation.ExcelField;

import lombok.Data;

@Data
public class ProductDetail {
    @ExcelField(cellOrder=0, cellValue="manufacture")
    private String manufacture;
    
    @ExcelField(cellOrder=1, cellValue="country")
    private String country;
}
