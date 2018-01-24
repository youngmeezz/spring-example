package org.springdemo.persistent;

import org.junit.Test;
import org.springdemo.runner.AbstractTestRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */
public class DataSourceTest extends AbstractTestRunner {
    @Autowired
    DataSource dataSource;

    @Test
    public void connTest() {
        assertNotNull(dataSource);

        try(Connection conn = dataSource.getConnection()) {
            System.out.println(conn);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
