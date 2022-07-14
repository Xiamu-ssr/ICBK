package com.qy137.controller;

import com.alibaba.fastjson.JSON;
import com.qy137.dao.BookDao;
import com.qy137.dao.impl.BookDaoImpl;
import com.qy137.entity.Book;
import com.qy137.utils.LayTab;
import com.qy137.utils.Result;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/book/*")
@SuppressWarnings("all")
public class BookController extends HttpServlet {
    private BookDao bookDao=new BookDaoImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        int i = uri.lastIndexOf("/");
        String s = uri.substring(i + 1);
        //前台对传过来的数据进行判断，分别跳转到不同的方法
        switch (s){
            case "getAllBook":
                getAllBook(req,resp);
                break;
            case "updateStatus":
                updateStatus(req,resp);
                break;
            case "delBook":
                delBook(req,resp);
                break;
            case "addOrUpdateBook":
                addOrUpdateBook(req,resp);
                break;
        }
    }

    protected void getAllBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        Map<String, String[]> parameterMap = req.getParameterMap();
        Book book = new Book();
        try {
            BeanUtils.populate(book,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        List<Book> allBook = bookDao.getAllBook(book);

        // LayTab layTab = new LayTab(allBook.size(),allBook);
        int count = bookDao.getAllBookCount(book);
        LayTab layTab = new LayTab(count,allBook);
        String s = JSON.toJSONString(layTab);
        resp.getWriter().write(s);
    }
    /**
     * 修改图书的上下架状态
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        String bookid = req.getParameter("bookid");
        String status = req.getParameter("status");
        int i = bookDao.updateStatus(Integer.parseInt(bookid), Integer.parseInt(status));
        PrintWriter writer = resp.getWriter();
        Result result =null;
        if (i>0){
            result=Result.success(null,"上架/下架成功！");
        }else {
            result=result.fail();
        }
        //将result转化为json格式
        String s = JSON.toJSONString(result);
        writer.write(s);


        //使用工具类前
       /* if (i>0){
            writer.write("上架/下架成功！");
        }else {
            writer.write("上架/下架失败！");
        }*/

    }
    /**
     * 根据id删除书籍信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        String bookid = req.getParameter("bookid");
        int i = bookDao.delBook(Integer.parseInt(bookid));
        if (i>0){
            resp.getWriter().write("删除成功");
        }else {
            resp.getWriter().write("删除成功");
        }

    }
    /**
     * 添加和修改图书信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addOrUpdateBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        Map<String, String[]> map = req.getParameterMap();
        Book book = new Book();
        try {
            BeanUtils.populate(book,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        int i=0;
        if (book.getBookid()>0){
            i=bookDao.updateIdBook(book);
        }else {
            i = bookDao.addBook(book);
        }
        Result result;
        if (i>0){
            result = Result.success();
        }else {
            result = Result.fail();
        }
        String s = JSON.toJSONString(result);
        resp.getWriter().write(s);


    }

}
