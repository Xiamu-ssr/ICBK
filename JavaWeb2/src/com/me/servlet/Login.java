package com.me.servlet;

import com.me.mysql.BaseSql;
import com.me.pojo.TemCode;
import com.me.pojo.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpCookie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CollationKey;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String code = request.getParameter("code");

//        System.out.println(email );
        BaseSql sql = new BaseSql();
        String command = "select * from TemCode where email = ? and code = ?";
        Object[] params = {email,code};
        try {
            List<TemCode> reslist = new ArrayList<>();
            reslist = sql.query(command,params, TemCode.class);
            if( reslist == null){
                //返回1:验证码错误
                System.out.println("Login:验证码错误");
                response.getWriter().write("1");
            }
            else{
                //返回2:正确登录
                System.out.println("Login:正确登录");
//                读取email的昵称
                command = "select * from User where email = ?";
                params = new Object[] {email};
                List<User> reslist2 = new ArrayList<>();
                reslist2 = sql.query(command,params,User.class);
                String name = reslist2.get(0).getName();
//              清空cookies后  cookies在每个人浏览器设置登录状态和昵称
                Cookie[] cookies = request.getCookies();
                for(int i=0;i<cookies.length;++i){
                    Cookie cookie = new Cookie(cookies[i].getName(),null);
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }
                Cookie cookie = new Cookie("UserName",name);
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
                //删除验证码缓存
                command = "delete from TemCode where email = ?";
                params = new Object[] {email};
                sql.updata(command,params);
                String[] Manager = {"1138882663@qq.com",
                        "2556419331@qq.com",
                        "2241306469@qq.com",
                        "2439036092@qq.com",
                        "3230763314@qq.com",
                        "2587410589@qq.com",
                        "1252573780@qq.com",
                        "2927676817@qq.com"
                };
                boolean Mflag = false;
                for(int i=0;i<Manager.length;++i){
                    if(email.equals(Manager[i])){
                        Mflag = true;
                        break;
                    }
                }
                if(Mflag){
                    cookie = new Cookie("LoginStatus","2");
                    cookie.setMaxAge(-1);
                    response.addCookie(cookie);
                    response.getWriter().write("3");
                }else {
                    cookie = new Cookie("LoginStatus","1");
                    cookie.setMaxAge(-1);
                    response.addCookie(cookie);
                    response.getWriter().write("2");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
