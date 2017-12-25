package com.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ExcelReadResultMessage {
    private boolean success;
    private String message;
    
    public ExcelReadResultMessage() {
        success = true;
        message = "";
    } 
}
