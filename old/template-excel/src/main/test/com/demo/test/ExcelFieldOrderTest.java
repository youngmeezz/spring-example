package com.demo.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.demo.annotation.ExcelField;
import com.demo.annotation.ExcelFieldType;
import com.demo.domain.Customer;
import com.demo.domain.ExcelPersistentEntity;
import com.demo.domain.Order;
import com.demo.domain.Product;
import com.demo.domain.ProductDetail;

public class ExcelFieldOrderTest {
    List<Order> orders;
    
    @Before
    public void setUp() {
        orders = new ArrayList<>(10);
        Calendar cal = Calendar.getInstance();
        IntStream.range(1, 11).forEach(i -> {
            Order order = new Order();
            order.setOrderNumber(i);
            order.setOrderDate(new Date());            
            order.setCustomer(createCustomer(i, cal));
            order.setOrderName("OrderName" + i);
            order.setProduct(createProduct(i));
            order.setNotExcelField("not excel field!");
            orders.add(order);
        });
    }
    
    @Test
    @Ignore
    public void checkValue() {
        List<ExcelPersistentEntity> persistents = getExcelPersistentListWithOrder(Order.class);
        Order order = orders.get(0);
        display(0, persistents, order);
        /*try {
        }        
        catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }*/
    }
    
    public int display(int startIdx, List<ExcelPersistentEntity> persistents, Object inst) {        
        int readIdx = startIdx;
        try {
            while(readIdx < persistents.size()) {
                ExcelPersistentEntity persistent = persistents.get(readIdx);
                Field field = persistent.getField();
                field.setAccessible(true);                
                if(persistent.getFieldType() == ExcelFieldType.Primitive) {
                    if(persistent.getInvoker() != inst.getClass()) {
                        return readIdx;
                    }
                    System.out.println("## [" + field.getName() + "] " +persistent.getCellName() + " : " + String.valueOf(field.get(inst)));
                }
                else if(persistent.getFieldType() == ExcelFieldType.Object) {
                    Object nextInst = field.get(inst);
                    if(nextInst == null) {
                        nextInst = field.getType().newInstance();
                    }                    
                    readIdx = display(readIdx + 1, persistents, nextInst);
                }
                readIdx++;
            }
        }
        catch(IllegalArgumentException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return readIdx;
        }        
        return readIdx;
    }
    
    @Test    
    public void checkPersistents() {
        List<ExcelPersistentEntity> meta = getExcelPersistentListWithOrder(Order.class);        
        for(int i=0; i<meta.size(); i++) {
            ExcelPersistentEntity persistent = meta.get(i);
            System.out.println(String.format("## [%d] : %s :: %s , invoker : %s", 
                    i, persistent.getFieldType(), persistent.getField().getName(), persistent.getInvoker()));
            //System.out.println(persistent);
        }
    }
    
    private List<ExcelPersistentEntity> getExcelPersistentListWithOrder(Class<?> clazz) {
        if(clazz == null) {
            System.out.println("clazz is null");
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
            
    private Customer createCustomer(int i, Calendar cal) {
        Customer c = new Customer();
        c.setSeq(i);
        c.setName("customer" + i);
        c.setEmail("customer" + i + "@github.com");
        String phone = "010-";
        for(int j=0;j<3;j++) {
            phone +=i;
        }
        phone += "-";
        for(int j=0;j<4;j++) {
            phone +=i;
        }
        c.setCellphone(phone);
        
        cal.set(Calendar.MONTH, i%12);            
        c.setRegDate(cal.getTime());
        
        return c;
    }
    
    private Product createProduct(int i) {
        Product p = new Product();
        p.setPName("product" + i);
        p.setPno((long)i);
        p.setPrice(2000);
        
        // p.setDetail(createProductDetail());
        
        return p;
    }
    
    private ProductDetail createProductDetail() {
        ProductDetail p = new ProductDetail();
        p.setCountry("country");
        p.setManufacture("manufacture");
        
        return p;
    }
    

}
