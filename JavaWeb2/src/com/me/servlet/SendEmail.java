package com.me.servlet;

import com.me.mysql.BaseSql;
import com.me.pojo.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.text.MessageFormat;

@WebServlet("/SendEmail")
public class SendEmail extends HttpServlet {
    //发件人
    private static String sender = "mumu2663@163.com";
    //授权码
    private static String pass = "YOGUBEJXAXMONHTO";
    //网易email服务器
    private static String host = "smtp.163.com";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //随机摇数
        Random random = new Random();
        String code = "";
        for(int i=0;i<4;++i){
            int j = random.nextInt(10);
            code += j;
        }
        //System.out.println("code = "+code);
        //收件人
        String receiver = "";
        String form = request.getParameter("type");
        if(form.equals("Login")){
            receiver = request.getParameter("email");
            System.out.println("Form from Login receiver="+receiver);
        }
        else if(form.equals("Register")){
            receiver = request.getParameter("email");
            System.out.println("Form from Register receiver="+receiver);
        }

        boolean CanSendEmail = true;
        //首先判断这个邮箱是否存在
        BaseSql sql = new BaseSql();
        String command = "select * from User where email = ?";
        Object[] params={receiver};
        List<User> reslist = new ArrayList<>();
        try {
            System.out.println("尝试select此用户");
            reslist = sql.query(command,params,User.class);
            if(reslist == null){
                //用户不存在
                if(form.equals("Login")){
                    //Login:返回0,Login做弹窗“无此用户”
                    //return
                    System.out.println("Login:无此邮箱");
                    response.getWriter().write("0");
                    CanSendEmail = false;
                }
                else if(form.equals("Register")){
                    System.out.println("Register:无此邮箱可注册");
                    //Register:程序正常运行
                }
            }
            else{
                //用户存在
                if(form.equals("Login")){
                    System.out.println("Login:用户存在可发件");
                    //Login:程序正常运行
                }
                else if(form.equals("Register")){
                    //Register:返回0,Register弹窗“用户已存在”
                    //return
                    System.out.println("Register:用户存在不可注册");
                    response.getWriter().write("0");
                    CanSendEmail = false;
                }
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
        if(CanSendEmail){
            //删除TemCode的匹配email
            System.out.println("尝试删除此用户前一次验证码");
            command = "delete from TemCode where email = ?";
            params = new Object[] {receiver};
            try {
                sql.updata(command,params);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //将[email,code]插入TemCode
            System.out.println("尝试插入此次邮箱和验证码");
            command = "insert into TemCode value (?,?)";
            params = new Object[] {receiver,code};
            try {
                sql.updata(command,params);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            // 获取系统的属性
            //Properties properties = System.getProperties();
            // 设置邮件服务器
            Properties properties = new Properties();
            //选择smtp传输协议
            properties.setProperty("mail.transport.protocol", "smtp");
            //验证:true(必选)
            properties.setProperty("mail.smtp.auth","true");
            //mail服务器
            properties.setProperty("mail.smtp.host", host);
            //端口25为网易163的非SSL
            properties.setProperty("mail.smtp.port","25");
            properties.setProperty("mail.smtp.socketFactory.port","25");
            //这样获取Session
            final PasswordAuthentication auth = new PasswordAuthentication(sender, pass);
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() { return auth; }
            });

            // 设置响应内容类型
            //response.setContentType("text/html;charset=UTF-8");
            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sender));
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(receiver));

                message.setSubject("ICBK 邮箱验证码");
                //读取html作为mail的正文
                StringBuffer stringBuffer = transHtml("/MyWeb/apache-tomcat-8.5.81/webapps/ICBK/EmailHtml.html");
//                StringBuffer stringBuffer = transHtml("D:\\Desktop\\Web\\JavaWeb2\\web\\EmailHtml.html");
                //将code填入html的预留位置
                String htmlText = MessageFormat.format(stringBuffer.toString(),code);
                //设置内容为utf-8的html
                message.setContent(htmlText,"text/html;charset=utf-8");
                // 发送消息
                Transport transport = session.getTransport();
                transport.connect(sender,pass);
                transport.send(message);
            }catch (MessagingException mex) {
                System.out.println("mail貌似发送失败了");
                mex.printStackTrace();
            }
        }
    }
    //把html转成StringBuffer
    private static StringBuffer transHtml(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        //System.out.println(buffer);
        return buffer;
    }
}
