package com.qy137.controller;

import com.alibaba.fastjson.JSON;
import com.qy137.dao.TypeDao;
import com.qy137.dao.impl.TypeDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/type/*")
public class TypeController extends HttpServlet {
    private TypeDao typeDao =new TypeDaoImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        System.out.println(uri);
        int i = uri.lastIndexOf("/")+1;
        String s = uri.substring(i);
        switch (s){
            case "getAllType":
                getAllType(req,resp);
                break;
        }}

    /**
     * 查询所有的书籍类型信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getAllType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        List<Map> types = typeDao.getAllType();
        String s = JSON.toJSONString(types);
        resp.getWriter().write(s);
    }
}
