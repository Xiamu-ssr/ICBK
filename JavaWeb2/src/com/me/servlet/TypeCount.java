package com.me.servlet;

import com.alibaba.fastjson.JSON;
import com.me.mysql.BaseSql;
import com.me.pojo.Typecount;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TypeCount")
public class TypeCount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type","text/html;charset=UTF-8");

        BaseSql sql = new BaseSql();
        String command = "select * from TypeCount";
        Object[] params = {};
        List<Typecount>reslist = new ArrayList<>();
        try {
            reslist = sql.query(command,params,Typecount.class);
            String res = JSON.toJSONString(reslist);
//            System.out.println(res);
            response.getWriter().write(res);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
