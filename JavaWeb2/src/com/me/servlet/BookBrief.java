package com.me.servlet;

import com.alibaba.fastjson.JSON;
import com.me.mysql.BaseSql;
import com.me.pojo.Book;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BookBrief")
public class BookBrief extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String BookName = URLDecoder.decode(request.getParameter("BookName"),"UTF-8");
        // 解决返回给前端的数据 中文乱码
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type","text/html;charset=UTF-8");

        BaseSql sql = new BaseSql();
        String command = "select * from allbooks where bookname = ?";
        Object[] params = {BookName};
//        System.out.println(command + BookName);
        List<Book> reslist = new ArrayList<>();
        try {
            reslist = sql.query(command,params,Book.class);
            if(reslist == null){
                System.out.println("系统错误:无此书籍");
            }
            else{
//                细节转化一波带走
                String res = JSON.toJSONString(reslist);
                response.getWriter().write(res);
            }
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
