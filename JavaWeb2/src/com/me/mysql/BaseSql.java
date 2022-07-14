package com.me.mysql;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseSql {
    //补充预解析sql语句
    private void fill(PreparedStatement pstate,Object[] params) throws SQLException {
        ParameterMetaData parameterMetaData = pstate.getParameterMetaData();
        int col = parameterMetaData.getParameterCount();
        for(int i=0;i<col;++i){
            pstate.setObject(i+1,params[i]);
        }
    }
    //updata方法,返回数字,-1表示空语句
    public int updata(String command,Object[] params) throws SQLException {
        if(command == null || command.isEmpty()){return -1;}
        Connection conn = JdbcUtils.getConn();
        PreparedStatement pstate = conn.prepareStatement(command);
        fill(pstate,params);
        return pstate.executeUpdate();
    }
    // query方法
    public <T> List<T> query(String command,Object[] params,Class<T> cls) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (command == null || command.isEmpty()) {
            return null;
        }
        List<T> list = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        PreparedStatement pstate = conn.prepareStatement(command);
        fill(pstate, params);
        ResultSet resultSet = pstate.executeQuery();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int col = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            T t = cls.getConstructor().newInstance();
            for (int i = 0; i < col; ++i) {
                String colName = resultSetMetaData.getColumnName(i + 1);
                BeanUtils.setProperty(t, colName, resultSet.getString(colName));
            }
            list.add(t);
        }
        conn.close();
        return list.size() != 0 ? list : null;
    }
}
