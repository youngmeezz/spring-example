package com.demo.domain;

import lombok.Data;

@Data
public class ExcelReadResult<T> {
    T t;
    private ExcelReadResultMessage resultMessage;
}
