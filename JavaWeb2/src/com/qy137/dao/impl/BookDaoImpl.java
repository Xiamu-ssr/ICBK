package com.qy137.dao.impl;

import com.qy137.dao.BookDao;
import com.qy137.entity.Book;
import com.qy137.utils.DBUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@SuppressWarnings("all")
public class BookDaoImpl implements BookDao {
   /* @Override
    public List<Map> getAllBook() {
       // String sql="select tb.bookid,tb.bookname,tb.author,tb.count,tb.remark,tb.pulisher,tb.price,tb.status,tp.typeid,tp.typename from t_book tb  inner join t_type tp on tb.typeid=tp.typeid ";
       String sql="select tb.*,tp.typename from t_book tb  inner join t_type tp on tb.typeid=tp.typeid";
        List<Map> map = DBUtil.queryListMap(sql);
        if (map.size()>0){
            return map;
        }
        return null;
    }*/

    /**
     * 查询所有书籍信息
     * 此方法结合了搜索、下拉搜搜和分页查询
     * ①定义sql，首先查出所有的信息
     * ②当在搜索栏输入查询内容时，或使用下拉框是，对sql语句进行拼接
     * ③调用方法，查询所有
     *
     * @param book
     * @return
     */
    @Override
    public List<Book> getAllBook(Book book) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tb.*,tt.typename from t_book tb INNER JOIN t_type tt on tb.typeid = tt.typeid ");
        if (book.getBookname()!=null && !"".equals(book.getBookname())){
            sql.append(" and tb.bookname like '%"+book.getBookname()+"%' ");
        }
        if (book.getAuthor()!=null && !"".equals(book.getAuthor())){
            sql.append(" and tb.author like '%"+book.getAuthor()+"%' ");
        }
        if (book.getTypeid()!=null && book.getTypeid()>0){
            sql.append(" and tb.typeid=" +book.getTypeid());
        }
        //直接进行分页
        sql.append(" limit "+(book.getPage()-1)*book.getLimit()+","+book.getLimit());
        //  System.out.println(sql);
        /*
        将定义的StringBuilder类型的sql，装换成字符串类型
        对map进行遍历，得到每一条数据，在放入到books集合列表中
         */
        List<Map> map = DBUtil.queryListMap(sql.toString());
        ArrayList<Book> books = new ArrayList<>();
        if (map.size()>0){
            for (int i = 0; i <map.size() ; i++) {
                Map map1 = map.get(i);
                Book book1 = new Book();
                try {
                    BeanUtils.populate(book1,map1);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                books.add(book1);

            }
            return books;
        }
        return null;
    }

    /**
     * 查询所有的书籍数量，用来实现分页的功能
     * ①写sql语句，得到各种条件下查询的书籍数量
     * @param book
     * @return
     */
    @Override
    public int getAllBookCount(Book book) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) from t_book tb INNER JOIN t_type tt on tb.typeid = tt.typeid ");
        if (book.getBookname()!=null && !"".equals(book.getBookname())){
            sql.append(" and tb.bookname like '%"+book.getBookname()+"%' ");
        }
        if (book.getAuthor()!=null && !"".equals(book.getAuthor())){
            sql.append(" and tb.author like '%"+book.getAuthor()+"%' ");
        }
        if (book.getTypeid()!=null && book.getTypeid()>0){
            sql.append(" and tb.typeid=" +book.getTypeid());
        }
        List<List> lists = DBUtil.queryList(sql.toString());
        if (lists.size()>0){
            Object o = lists.get(0).get(0);
            return Integer.parseInt(o+"");
        }
        return 0;

    }

    /**
     * 根据bookid来修改书籍的上下架状态
     * 此时需要有bookid和status连个参数
     * @param bookid
     * @param status
     * @return
     */
    @Override
    public int updateStatus(Integer bookid, Integer status) {
        String sql="update t_book set status=? where bookid=?";
        int i=0;
        if (status==0){
            i=DBUtil.update(sql,1,bookid);
        }else {
            i=DBUtil.update(sql,0,bookid);
        }
        return i;
    }

    /**
     * 根据bookid来删除书籍信息
     * @param bookid
     * @return
     */
    @Override
    public int delBook(Integer bookid) {
        String sql="delete from t_book where bookid=?";
        return DBUtil.update(sql, bookid);
    }



    /**
     * 添加书籍信息
     * @param book
     * @return
     */
    @Override
    public int addBook(Book book) {
        String sql="insert into t_book values(null,?,?,?,?,?,?,?,?)";
        int i = DBUtil.update(sql, book.getBookname(), book.getAuthor(), book.getCount(), book.getRemark(), book.getTypeid(), book.getPublisher(), book.getPrice(), book.getStatus());
        return i;
    }

    @Override
    public int updateIdBook(Book book) {
        String sql="update t_book set bookname=?,author=?,remark=?,typeid=?,publisher=?,price=?,status=? where bookid=?";
        int i = DBUtil.update(sql, book.getBookname(), book.getAuthor(), book.getRemark(), book.getTypeid(), book.getPublisher(), book.getPrice(), book.getStatus(), book.getBookid());
        sql = "update allbooks set bookname=?,bookwriter=?,bookbrief=?,bookpublisher=?,rmb=? where ids=?";
        DBUtil.update(sql, book.getBookname(),book.getAuthor(),book.getRemark(),book.getPublisher(),book.getPrice(),book.getBookid());
        return i;
    }
}
