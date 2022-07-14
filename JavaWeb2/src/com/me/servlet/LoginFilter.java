package com.me.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//拦截所以资源
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //转型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        Cookie[] cookies = request.getCookies();
//        初始登录注册资源放行
        if(requestURI.endsWith("index.html")||
                requestURI.endsWith("Register.html") ||
                requestURI.endsWith("SendEmail") ||
                requestURI.endsWith("JavaWeb2/") ||
                requestURI.endsWith("Login") ||
                requestURI.endsWith("Register")||
                requestURI.contains("frame") ||
                requestURI.contains("css") ||
                requestURI.contains("img")){
            System.out.println("公开资源可访问");
            filterChain.doFilter(servletRequest,servletResponse);
        }else if(requestURI.endsWith("BookManager.jsp")){
            boolean flag = true;
            System.out.println("验证登录状态cookies中");
            for(int i=0;i<cookies.length;++i){
                System.out.println(cookies[i].getName()+" - "+cookies[i].getValue());
                if("LoginStatus".equals(cookies[i].getName())&&"2".equals(cookies[i].getValue())){
                    System.out.println("验证成功");
                    flag = false;
                    break;
                }
            }
            if(flag){
                System.out.println("非管理员");
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setStatus(302);
                response.setHeader("Location","http://119.3.175.29/ICBK/index.html");
            }else {
                System.out.println("已登录,合法访问");
                filterChain.doFilter(servletRequest,servletResponse);
            }
        } else if (cookies != null ){
            boolean flag = true;
            System.out.println("验证登录状态cookies中");
            for(int i=0;i<cookies.length;++i){
                System.out.println(cookies[i].getName()+" - "+cookies[i].getValue());
                if("LoginStatus".equals(cookies[i].getName())){
                    System.out.println("验证成功");
                    flag = false;
                    break;
                }
            }
            if(flag){
                System.out.println("不存在登录状态cookie");
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setStatus(302);
                response.setHeader("Location","http://119.3.175.29/ICBK/index.html");
            }else {
                System.out.println("已登录,合法访问");
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }else {
            System.out.println("非法访问资源");
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("http://119.3.175.29/ICBK/index.html");
            //response.getWriter().write("666");
        }
    }

    @Override
    public void destroy() {

    }
}
