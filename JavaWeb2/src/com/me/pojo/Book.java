package com.me.pojo;

public class Book {
    private String ids;
    private String imgurl;
    private String bookname;
    private String bookwriter;
    private String bookbrief;
    private String other;
    private String bookpublisher;
    private String isbn;
    private String rmb;
    private String bookscore;

    public Book() {}

    public Book(String ids, String imgurl, String bookname, String bookwriter, String bookbrief, String other, String bookpublisher, String isbn, String rmb, String bookscore) {
        this.ids = ids;
        this.imgurl = imgurl;
        this.bookname = bookname;
        this.bookwriter = bookwriter;
        this.bookbrief = bookbrief;
        this.other = other;
        this.bookpublisher = bookpublisher;
        this.isbn = isbn;
        this.rmb = rmb;
        this.bookscore = bookscore;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookwriter() {
        return bookwriter;
    }

    public void setBookwriter(String bookwriter) {
        this.bookwriter = bookwriter;
    }

    public String getBookbrief() {
        return bookbrief;
    }

    public void setBookbrief(String bookbrief) {
        this.bookbrief = bookbrief;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getBookpublisher() {
        return bookpublisher;
    }

    public void setBookpublisher(String bookpublisher) {
        this.bookpublisher = bookpublisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    public String getBookscore() {
        return bookscore;
    }

    public void setBookscore(String bookscore) {
        this.bookscore = bookscore;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ids='" + ids + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", bookname='" + bookname + '\'' +
                ", bookwriter='" + bookwriter + '\'' +
                ", bookbrief='" + bookbrief + '\'' +
                ", other='" + other + '\'' +
                ", bookpublisher='" + bookpublisher + '\'' +
                ", isbn='" + isbn + '\'' +
                ", rmb='" + rmb + '\'' +
                ", bookscore='" + bookscore + '\'' +
                '}';
    }
}
