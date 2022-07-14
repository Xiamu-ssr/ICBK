package com.qy137.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据库的连接类
 */
public class DBUtil {
    private  static DataSource dataSource;
    static {
        Properties properties = new Properties();
        try {
//            FileInputStream fileInputStream = new FileInputStream("D:\\Desktop\\Web\\JavaWeb2\\src\\druid.properties");
            FileInputStream fileInputStream = new FileInputStream("/MyWeb/apache-tomcat-8.5.81/webapps/ICBK/WEB-INF/classes/druid.properties");
            properties.load(fileInputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据库的连接
     * @return
     */
    public static Connection getConn(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实现修改的方法，在实现增删改的时候调用
     * @param sql
     * @param objects
     * @return
     */
    public static int update(String sql, Object... objects){
        Connection conn = getConn();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            setParamter(ps,objects);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(conn,ps,null);
        }
        return -1;
    }

    /**
     * 实现设置参数的方法
     * @param ps
     * @param objects
     */

    public static void setParamter(PreparedStatement ps,Object... objects){
        try {
            if(objects.length > 0){
                for (int i = 0; i < objects.length; i++) {
                    ps.setObject(i+1,objects[i]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现查询所有的方法，List<List>
     * @param sql
     * @param objects
     * @return
     */
    public static List<List> queryList(String sql, Object... objects){
        Connection conn = getConn();
        PreparedStatement ps=null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(sql);
            setParamter(ps,objects);
            resultSet = ps.executeQuery();
            ArrayList<List> bigList = new ArrayList<>();
            while (resultSet.next()){
                ArrayList<Object> smList = new ArrayList<>();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount() ; i++) {
                    smList.add(resultSet.getObject(i));
                }
                bigList.add(smList);
            }
            return bigList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(conn,ps,resultSet);
        }
        return null;
    }

    /**
     * 实现查询所有的方法List<Map>
     * @param sql
     * @param objects
     * @return
     */
    public static List<Map> queryListMap(String sql, Object... objects){
        Connection conn = getConn();
        PreparedStatement ps = null;
        ResultSet set = null;
        try {
            ps = conn.prepareStatement(sql);
            setParamter(ps,objects);
            set = ps.executeQuery();
            ArrayList<Map> bigList = new ArrayList<>();
            while (set.next()){
                HashMap<Object, Object> map = new HashMap<>();
                for (int i = 1; i <= set.getMetaData().getColumnCount(); i++) {
                    map.put(set.getMetaData().getColumnName(i),set.getObject(i));
                }
                bigList.add(map);
            }
            return bigList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(conn,ps,set);
        }
        return null;
    }

    /**
     * 关闭连接
     * @param conn
     * @param ps
     * @param set
     */
    public static void closeAll(Connection conn,PreparedStatement ps,ResultSet set){
        try {
            if(set!=null){
                set.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(ps!=null){
                ps.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if(conn!=null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

