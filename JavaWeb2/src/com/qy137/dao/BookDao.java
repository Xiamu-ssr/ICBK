package com.qy137.dao;

import com.qy137.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookDao  {
    //查询所有书籍
    //  List<Map> getAllBook();
    List<Book> getAllBook(Book book);
    //修改上架状态
    int updateStatus(Integer bookid,Integer status);
    //删除书籍信息
    int delBook(Integer bookid);
    //查询分页搜索符合条件的总条数
    int getAllBookCount(Book book);
    //添加书籍信息
    int addBook(Book book);
    //根据id修改图书信息
    int updateIdBook(Book book);
}

