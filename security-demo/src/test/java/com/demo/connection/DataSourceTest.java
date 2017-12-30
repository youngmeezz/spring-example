package com.demo.connection;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.runner.AbstractTestRunner;

public class DataSourceTest extends AbstractTestRunner {
    @Autowired
    DataSource ds;
    
    @Test
    public void connTest() {
        assertNotNull(ds);
        try(Connection conn = ds.getConnection()) {
            System.out.println(conn);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }        
    }
    
    
}
