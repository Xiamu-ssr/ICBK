package com.me.servlet;

import com.me.mysql.BaseSql;
import com.me.pojo.TemCode;
import com.me.pojo.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String code = request.getParameter("code");

        BaseSql sql =new BaseSql();
        String command = "select * from TemCode where email = ? and code = ?";
        Object[] params = {email,code};
        List<TemCode> reslist = new ArrayList<>();
        try {
            reslist = sql.query(command,params,TemCode.class);
            if(reslist == null){
                //返回1-验证码错误
                System.out.println("Register:验证码错误");
                response.getWriter().write("1");
            }
            else {
                //注册成功,插入User
                command = "insert into User value (?,?)";
                params=new Object[] {email,name};
                sql.updata(command,params);
                System.out.println("Register:注册成功");
                //删除验证码缓存
                command = "delete from TemCode where email = ?";
                params = new Object[] {email};
                sql.updata(command,params);
                response.getWriter().write("2");
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
