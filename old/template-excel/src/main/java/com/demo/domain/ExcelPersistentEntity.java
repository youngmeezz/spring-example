package com.demo.domain;

import java.lang.reflect.Field;

import com.demo.annotation.ExcelFieldType;

import lombok.Data;

@Data
public class ExcelPersistentEntity {
	private Field field;
	private String cellName;
	private String regex;
	private boolean notNull;
	private ExcelFieldType fieldType;
	private Class<?> invoker;
}