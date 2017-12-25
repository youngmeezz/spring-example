package com.demo.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.DateFormatConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.annotation.ExcelField;
import com.demo.annotation.ExcelFieldType;
import com.demo.domain.ExcelPersistentEntity;

@Service
public class ExcelService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);
    public static ConcurrentHashMap<Class<?>, List<ExcelPersistentEntity>> metaDataMap = new ConcurrentHashMap<>(10);
    
    @PostConstruct
    public void setUp() {
                
    }
    
    public <T> void buildDocument(List<T> list, Workbook workbook) {
        if(workbook == null) {
            logger.debug("## [workbook is null]");
        }
        if(list == null || list.size() == 0) {
            logger.debug("## [list is empty]");
        }
        
        Class<?> clazz = list.get(0).getClass();
        List<ExcelPersistentEntity> metaDatas = metaDataMap.get(clazz);
        if(metaDatas == null) {
            metaDatas = getExcelPersistentListWithOrder(clazz);
            if(metaDatas.isEmpty()) {
                logger.debug("## [not exist @ExcelField] class : ", clazz.getName());
                return;
            }
            metaDataMap.put(clazz, metaDatas);
        }
        
        for(int i=0; i<metaDatas.size(); i++) {
            System.out.println("## [" + i + "] " + metaDatas.get(i));
        }
        
        // date format
        String excelFormatPattern = DateFormatConverter.convert(Locale.KOREA, "yyyy-MM-dd, HH:mm");
        System.out.println("excelFormatPattern :: " + excelFormatPattern);
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();     
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(excelFormatPattern));
        
        // create sheet
        Sheet sheet = workbook.createSheet("Spring MVC AbstractXlsView");
        
        // write first row
        int rowCnt = 0;
        Row header = sheet.createRow(rowCnt++);        
        int firstCellCnt = 0;
        for(ExcelPersistentEntity metaData : metaDatas) {
            if(metaData.getFieldType() == ExcelFieldType.Primitive) {
                header.createCell(firstCellCnt++).setCellValue(metaData.getCellName());
            }
        }
        
        // write datas
        for(T inst : list) {
            Row row = sheet.createRow(rowCnt++);
            writeRow(0, metaDatas, inst, row, cellStyle);            
        }
        
        autoSizeColumns(workbook);        
    }
    
    private void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(0);
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }       
    }
    
    private int writeRow(int startIdx, List<ExcelPersistentEntity> persistents, Object inst, Row row, CellStyle dateCellStyle) {
        int readIdx = startIdx;
        try {
            while(readIdx < persistents.size()) {
                ExcelPersistentEntity persistent = persistents.get(readIdx);
                Field field = persistent.getField();
                field.setAccessible(true);
                if(persistent.getFieldType() == ExcelFieldType.Primitive) {
                    if(persistent.getInvoker() != inst.getClass()) {                        
                        return --readIdx;
                    }
                    writeCell(field,inst,row, dateCellStyle);
                }
                else if(persistent.getFieldType() == ExcelFieldType.Object) {
                    Object nextInst = field.get(inst);
                    if(nextInst == null) {
                        nextInst = field.getType().newInstance();
                    }                    
                    readIdx = writeRow(readIdx + 1, persistents, nextInst, row, dateCellStyle);
                }
                readIdx++;
            }
        }
        catch(IllegalArgumentException | IllegalAccessException | InstantiationException e) {
            logger.error("## [failed to write acess field]", e);
            return readIdx;
        }
        return readIdx;       
    }
    
    private void writeCell(Field field, Object inst, Row row, CellStyle dateCellStyle) throws IllegalArgumentException, IllegalAccessException {
        short lastCellNum = row.getLastCellNum();
        if(lastCellNum < 0) {
            lastCellNum = 0;
        }
        Cell cell = row.createCell(lastCellNum);
        Object value = field.get(inst);
        System.out.println(String.valueOf(value));
        if(value == null) {
            cell.setCellValue("");
        }
        else {
            if(value instanceof Number) {
                if(value instanceof Double) {
                    cell.setCellValue((double)value);                
                }
                else if(value instanceof Long) {
                    long longVal = (long)value;
                    cell.setCellValue(longVal);
                }
                else {
                    int intVal = (int)value;
                    cell.setCellValue(intVal);
                }
            }
            if(value instanceof Boolean) {
                cell.setCellValue((Boolean)value);                      
            }
            else if(value instanceof Date) {
                cell.setCellValue((Date)value);                
                cell.setCellStyle(dateCellStyle);
            }
            else if(value instanceof String) {
                cell.setCellValue((String)value);
            }            
        }     
    }
    
    
    
    private List<ExcelPersistentEntity> getExcelPersistentListWithOrder(Class<?> clazz) {
        if(clazz == null) {
            logger.debug("## [clazz is null]");
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
