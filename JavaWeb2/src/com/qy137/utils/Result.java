package com.qy137.utils;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 这是一个工具类
 * 用来实现操作成功后的提示信息
 */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result(1,"操作成功",null);
    }
    public static Result success(Object data,String msg){
        return new Result(1,msg,data);
    }
    public static Result fail(){
        return new Result(2,"操作失败",null);
    }
    public static Result fail(String msg){
        return new Result(2,msg,null);
    }

    public Result(){}
    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
