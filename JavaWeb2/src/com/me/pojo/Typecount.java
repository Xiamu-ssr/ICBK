package com.me.pojo;

public class Typecount {
    private String name;
    private int num;

    public Typecount(){}

    public Typecount(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "TypeCount{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
