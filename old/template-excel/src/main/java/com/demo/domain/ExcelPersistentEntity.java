package com.demo.domain;

import java.lang.reflect.Field;

import lombok.Data;

@Data
public class ExcelPersistentEntity {
	private Field field;
	private int order;
	private String cellName;
	private boolean notNull;
}
