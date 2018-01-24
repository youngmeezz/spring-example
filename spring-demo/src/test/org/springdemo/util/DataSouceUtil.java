package org.springdemo.util;

import org.junit.Test;
import org.springdemo.runner.AbstractTestRunner;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.annotation.ExceptionProxy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */
public class DataSouceUtil {
    public static void dropAndCreateSeq(DataSource ds, String seqName) {
        PreparedStatement pstmt=null;
        try (Connection conn = ds.getConnection()){
            String deleteSql = "DROP SEQUENCE " + seqName;
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.executeUpdate();
            String createSql = "CREATE SEQUENCE " + seqName;
            pstmt = conn.prepareStatement(createSql);
            pstmt.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(pstmt != null) { try{pstmt.close();}catch(SQLException e){}}
        }
    }

    public static void deleteAll(DataSource ds, String tableName) {
        PreparedStatement pstmt=null;
        try (Connection conn = ds.getConnection()){
            String sql = "DELETE FROM " + tableName;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(pstmt != null) { try{pstmt.close();}catch(SQLException e){}}
        }
    }
}