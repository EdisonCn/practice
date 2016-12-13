package com.edison.serialize;

import java.io.Serializable;

/**
 * 文件名:com.edison.serialize.kryo.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-12
 * 修改记录:
 */
public class User implements Serializable{

    public User(){

    }

    public User(long id,String name,byte sex,int age,String address){
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.address = address;
    }

    private long id;

    private String name;

    private byte sex;

    private int age;

    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", age=").append(age);
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }
}
