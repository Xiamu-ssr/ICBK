package com.qy137.entity;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.io.Serializable;
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Book implements Serializable {
    private Integer bookid;
    private String bookname;
    private String author;
    private Integer count;
    private String remark;
    private String publisher;
    private String price;
    private String status;
    private Integer typeid;
    private String typename;

    private Integer page;
    private Integer limit;

    public Book(){}
    public Book(Integer bookid, String bookname, String author, Integer count, String remark, String publisher, String price, String status, Integer typeid, String typename, Integer page, Integer limit) {
        this.bookid = bookid;
        this.bookname = bookname;
        this.author = author;
        this.count = count;
        this.remark = remark;
        this.publisher = publisher;
        this.price = price;
        this.status = status;
        this.typeid = typeid;
        this.typename = typename;
        this.page = page;
        this.limit = limit;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", bookname='" + bookname + '\'' +
                ", author='" + author + '\'' +
                ", count=" + count +
                ", remark='" + remark + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price='" + price + '\'' +
                ", status='" + status + '\'' +
                ", typeid=" + typeid +
                ", typename='" + typename + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}

