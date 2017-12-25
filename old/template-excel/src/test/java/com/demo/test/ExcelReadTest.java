package com.demo.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.demo.annotation.ExcelField;
import com.demo.annotation.ExcelFieldType;
import com.demo.domain.ExcelPersistentEntity;
import com.demo.domain.ExcelReadResult;
import com.demo.domain.ExcelReadResultMessage;
import com.demo.domain.Order;
import com.demo.domain.Pair;

public class ExcelReadTest {    
    public static ConcurrentHashMap<Class<?>, List<ExcelPersistentEntity>> metaDataMap = new ConcurrentHashMap<>(10);
    
    @Test
    public void readTest() {        
        // List<Order> orders = readFromExcel("test-form-excel.xls", Order.class);
        // List<Order> orders = readFromExcel("test-form-diff-order-excel.xls", Order.class);
        // List<Order> orders = readFromExcel("test-form-one-row-excel.xls", Order.class);
        List<ExcelReadResult<Order>> results = readFromExcel("test-form-not-matched-regex-excel.xls", Order.class);        
        System.out.println("####################################################################");
        results.forEach(result -> {
            System.out.println("Result : " + result.getResultMessage().isSuccess() + ", Message : " + result.getResultMessage().getMessage());
            System.out.println(result.getT());
        });
        System.out.println("####################################################################");
    }
    
    public <T> List<ExcelReadResult<T>> readFromExcel(String filepath, Class<T> clazz) {
        Workbook wb = getWorkbook(filepath);
        if(wb == null) {
            System.out.println("## [Workbook is null] filepath : " + filepath);
            return Collections.emptyList();
        }        
        if(wb.getNumberOfSheets() < 1) {
            System.out.println("## [sheet is empty]");
            return Collections.emptyList();
        }
        
        // get sheet 
        Sheet sheet = wb.getSheetAt(0);
        int rowsCnt = sheet.getPhysicalNumberOfRows();
        System.out.println("## getFirstRowNum() : " + sheet.getFirstRowNum() + ", lastRowNum() :: " + sheet.getLastRowNum());
        if(rowsCnt < 1) {
            System.out.println("## [rows cnt less than 1] rowsCnt : " + rowsCnt);
            return Collections.emptyList();
        }        
        System.out.println("## rowsCnt : " + rowsCnt);
        
        // get class`s meta data
        List<ExcelPersistentEntity> metaDatas = metaDataMap.get(clazz);
        if(metaDatas == null) {
            metaDatas = getExcelPersistentListWithOrder(clazz);
            if(metaDatas.isEmpty()) {
                System.out.println("## [not exist @ExcelField] class : " + clazz.getName());
                return Collections.emptyList();
            }
            metaDataMap.put(clazz, metaDatas);
        }
        
        // check first order
        int rowsIdx = 0; 
        Row firstRow = sheet.getRow(rowsIdx++);        
        int readCell = 0;
        for(ExcelPersistentEntity persistent : metaDatas) {
            if(persistent.getFieldType() == ExcelFieldType.Primitive) {
                if(!persistent.getCellName().equals(firstRow.getCell(readCell++).getStringCellValue())) {
                    System.out.println("## diff order!!");
                    return Collections.emptyList();
                }
            }
        }    
        
        List<ExcelReadResult<T>> results = new ArrayList<>(rowsCnt);        
        try {
            for(;rowsIdx<rowsCnt; rowsIdx++) {
                Row row = sheet.getRow(rowsIdx);                
                if(isEmptyRow(row)) {
                    continue;
                }
                System.out.println("## row.getPhysicalNumberOfCells() :: " + row.getPhysicalNumberOfCells());
                ExcelReadResult<T> result = new ExcelReadResult<>();
                ExcelReadResultMessage message = new ExcelReadResultMessage();
                
                T t = clazz.newInstance();
                result.setT(t);
                result.setResultMessage(message);
                
                results.add(result);                                
                readRow(0,0,metaDatas,t,row,message);
                System.out.println("## complete one row : " + t);
            }            
        }
        catch(IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    private boolean isEmptyRow(Row row) {
        if(row == null) {
            return true;
        }
        
        for(int i=row.getFirstCellNum(); i<row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if(cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {                
                return false;
            }
        }
        
        return true;
    }
    
    private Pair<Integer,Integer> readRow(int startPersistentIdx, int readCellIdx, List<ExcelPersistentEntity> persistents, Object inst, Row row, ExcelReadResultMessage message) {
        int readPersistentIdx = startPersistentIdx;
        
        try {
            while(readPersistentIdx < persistents.size() && readCellIdx < row.getLastCellNum()) {
                ExcelPersistentEntity persistent = persistents.get(readPersistentIdx);
                Field field = persistent.getField();
                field.setAccessible(true);
                
                if(persistent.getFieldType() == ExcelFieldType.Primitive) {
                    if(persistent.getInvoker() != inst.getClass()) {
                        return new Pair<>(readPersistentIdx - 1, readCellIdx);
                    }
                    // read excel value & set to inst
                    System.out.println("## read :: " + persistent.getCellName());
                    Cell cell = row.getCell(readCellIdx++);
                    Object value = getValueFromCell(cell, field);
                    if(value == null) {
                        // check not null property
                        if(persistent.isNotNull()) {
                            message.setSuccess(false);
                            message.setMessage(message.getMessage() + " [Must be not empty : "+ persistent.getCellName()+ " / row : " + row.getRowNum() + "] ");
                        }
                    }
                    else {
                        String regex = persistent.getRegex();
                        boolean regexMatched = true;
                        if(regex.length() > 0) {
                            regexMatched = ((String)value).matches(regex);
                            System.out.println("regexMatched : " + regexMatched + ", regex : " + regex + ", value : " + value);
                        }
                        if(!regexMatched) {
                            message.setSuccess(false);
                            message.setMessage(message.getMessage() + " [Must be matched : "+ persistent.getCellName()+ " / row : " + row.getRowNum() + "] ");                            
                        }
                        
                        field.setAccessible(true);
                        field.set(inst, value);
                    }
                }
                else if(persistent.getFieldType() == ExcelFieldType.Object) {
                    // newInstance + setting & recursive
                    Object nextInst = field.getType().newInstance();
                    field.set(inst, nextInst);
                    Pair<Integer, Integer> result = readRow(readPersistentIdx + 1, readCellIdx, persistents, nextInst, row, message);
                    readPersistentIdx = result.getFirst();
                    readCellIdx = result.getSecond();
                }
                readPersistentIdx++;
            }            
        }
        catch(IllegalArgumentException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return new Pair<>(readPersistentIdx, readCellIdx);
        }
        
        return new Pair<>(readPersistentIdx, readCellIdx);
    }
    
    private static Object getValueFromCell(Cell cell, Field field) {
        if(cell == null) {
            return null;
        }
        Class<?> fieldType = field.getType();
        Object value = null;
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK :
                System.out.println("CELL_TYPE_BLANK :: ");
                value = null;
                break;
            case Cell.CELL_TYPE_BOOLEAN :
                value = Boolean.valueOf(cell.getBooleanCellValue());
                System.out.print("CELL_TYPE_BOOLEAN :: " + String.valueOf(value));                    
                break;              
            case Cell.CELL_TYPE_NUMERIC :
                System.out.print("CELL_TYPE_NUMERIC :: ");
                if(HSSFDateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                }
                else {
                    double doubleValue = cell.getNumericCellValue();
                    System.out.println("doubleValue :: " + doubleValue);
                    if(fieldType.isAssignableFrom(Double.class)) {
                        value = Double.valueOf(cell.getNumericCellValue());                                        
                    }
                    else if(fieldType.isAssignableFrom(Long.class)) {
                        value = Long.valueOf((long)doubleValue);
                    }
                    else {
                        value = Integer.valueOf((int)doubleValue);
                    }                    
                }
                System.out.println(String.valueOf(value));
                break;
            case Cell.CELL_TYPE_STRING :
                value = cell.getStringCellValue(); 
                System.out.println("CELL_TYPE_STRING :: " + String.valueOf(value));             
                break;
            case Cell.CELL_TYPE_FORMULA :
                value = cell.getCellFormula(); 
                System.out.println("CELL_TYPE_FORMULA :: "+ String.valueOf(value));                    
                break;
            case Cell.CELL_TYPE_ERROR :
                value = null;
                System.out.println("CELL_TYPE_ERROR :: "+ String.valueOf(value));
                break;
            default : 
                System.out.println("CELL_TYPE_RROR OR DEFAULT");
                break;
        }
        
        return value;
    }
    
    private Workbook getWorkbook(String filepath) {
        try {
            ClassPathResource resource = new ClassPathResource(filepath);
            String lowerCase = filepath.toLowerCase();          
            
            if(lowerCase.endsWith(".xls")) {
                return new HSSFWorkbook(resource.getInputStream());
            }
            else if(lowerCase.endsWith(".xlsx")) {
                return new HSSFWorkbook(resource.getInputStream());
            }
        }
        catch(IOException e) {
            
        }                
        return null;
    }
    
    private List<ExcelPersistentEntity> getExcelPersistentListWithOrder(Class<?> clazz) {
        if(clazz == null) {
            System.out.println("## [clazz is null]");
            return null;
        }
        
        List<ExcelPersistentEntity> ret = new ArrayList<>();
        
        PriorityQueue<Field> fieldQueue = new PriorityQueue<>(new Comparator<Field>() {
            @Override
            public int compare(Field f1, Field f2) {
                ExcelField e1 = f1.getAnnotation(ExcelField.class);
                ExcelField e2 = f2.getAnnotation(ExcelField.class);
                return e1.cellOrder() - e2.cellOrder();
            }            
        });
        
        // sort for member fields
        for(Field field : clazz.getDeclaredFields()) {
            if(field.getAnnotation(ExcelField.class) != null) {
                fieldQueue.offer(field);                
            }
        }
        
        while(!fieldQueue.isEmpty()) {
            Field field = fieldQueue.poll();
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            ExcelPersistentEntity metaData = new ExcelPersistentEntity();
            metaData.setFieldType(excelField.fieldType());
            metaData.setField(field);
            
            if(excelField.fieldType() == ExcelFieldType.Object) {
                ret.add(metaData);
                // recursive
                List<ExcelPersistentEntity> childs = getExcelPersistentListWithOrder(field.getType());
                ret.addAll(childs);                
            }
            else if(excelField.fieldType() == ExcelFieldType.Primitive) {
                metaData.setCellName(excelField.cellValue());
                metaData.setNotNull(excelField.notNull());            
                metaData.setRegex(excelField.regex());
                metaData.setInvoker(clazz);
                ret.add(metaData);
            }
        }
        
        return ret;
    }
}
