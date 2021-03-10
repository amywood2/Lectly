package com.example.lectly;

public class ClassItem {
    private int id;
    private String name,parentid;
    public ClassItem(int  id,String name,String parentid)
    {
        this.id = id;
        this.name = name;
        this.parentid =parentid;
    }


    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }


    public String getParentid() {
        return parentid;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}
