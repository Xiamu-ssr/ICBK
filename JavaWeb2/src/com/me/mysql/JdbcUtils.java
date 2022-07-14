package com.me.mysql;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DataSource dataSource;
    static {
        Properties properties = new Properties();
        try {
//            FileInputStream fileInputStream = new FileInputStream("D:\\Desktop\\Web\\JavaWeb2\\src\\druid.properties");
            FileInputStream fileInputStream = new FileInputStream("/MyWeb/apache-tomcat-8.5.81/webapps/ICBK/WEB-INF/classes/druid.properties");
            properties.load(fileInputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConn() throws SQLException {
//        System.out.println(dataSource.getConnection());
        return dataSource.getConnection();
    }
}
