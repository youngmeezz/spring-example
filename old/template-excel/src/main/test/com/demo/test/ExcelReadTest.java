package com.demo.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.demo.annotation.ExcelField;
import com.demo.annotation.ExcelFieldType;
import com.demo.domain.ExcelPersistentEntity;
import com.demo.domain.Order;

public class ExcelReadTest {    
    public static ConcurrentHashMap<Class<?>, List<ExcelPersistentEntity>> metaDataMap = new ConcurrentHashMap<>(10);
    
    @Test
    public void readTest() {
        List<Order> orders = readFromExcel("test-form-excel.xls", Order.class);
        //List<Order> orders = readFromExcel("test-form-diff-order-excel.xls", Order.class);
        System.out.println("Result :: " + orders.size());
    }
    
    public <T> List<T> readFromExcel(String filepath, Class<T> clazz) {
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
        if(rowsCnt < 1) {
            System.out.println("## [rows cnt less than 1] rowsCnt : " + rowsCnt);
            return Collections.emptyList();
        }
        
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
        
        List<T> insts = new ArrayList<>(rowsCnt);
        IntStream.range(rowsIdx, rowsCnt).forEach(rowIdx -> {
            Row row = sheet.getRow(rowIdx);
            if(row == null) {
                return;
            }                        
            
        });                       
        
        System.out.println("rowCnt :: " + rowsCnt);        
        return Collections.emptyList();   
    }
    
    private static Workbook getWorkbook(String filepath) {
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
                metaData.setInvoker(clazz);
                ret.add(metaData);
            }
        }
        
        return ret;
    }
}
