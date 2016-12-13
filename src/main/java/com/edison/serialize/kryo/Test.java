package com.edison.serialize.kryo;

import com.edison.serialize.User;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件名:com.edison.serialize.kryo.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-12
 * 修改记录:
 */
public class Test {

    private long start;

    private final static Kryo kryo = new Kryo();

    @Before
    public void beforeTest() {
        start = Instant.now().toEpochMilli();
    }

    @After
    public void afterTest() {
        System.out.println("处理耗时:"+(Instant.now().toEpochMilli() - start)/1000f +" sec.");
    }

    @org.junit.Test
    public void kryoUserTest(){
        User user = new User(1001L,"xiaowang",Byte.valueOf("1"),18,"虹口区虹关路368号建邦大厦");
        String serializedStr = serializationObject(user);
        System.out.println(serializedStr);
        System.out.println("反序列化Bean:"+deserializationObject(serializedStr,User.class));;
    }

    @org.junit.Test
    public void listTest() throws IOException {
        //List<String> list = Arrays.asList("OSChina.NET","Team@OSC", "Git@OSC", "Sonar@OSC");
        List<String> list = new ArrayList<String>(8);
        list.add("OSChina.NET");
        list.add("Team@OSC");
        list.add("Git@OSC");
        list.add("Sonar@OSC");
        byte[] bits = serialize(list);
        for(byte b : bits){
            System.out.print(Byte.toString(b)+" ");
        }
        List<String> deserializeObject = (List<String>) deserialize(bits);
        System.out.println("\n"+deserializeObject);
    }



    public byte[] serialize(Object object){
        Output output = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            output = new Output(baos);
            kryo.writeClassAndObject(output, object);
            output.flush();
            return baos.toByteArray();
        }finally{
            if(output != null)
                output.close();
        }
    }

    public static Object deserialize(byte[] bits) throws IOException {
        if(bits == null || bits.length == 0)
            return null;
        Input ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bits);
            ois = new Input(bais);
            return kryo.readClassAndObject(ois);
        } finally {
            if(ois != null)
                ois.close();
        }
    }

    private <T extends Serializable> String serializationObject(T obj) {
        kryo.setReferences(false);
        kryo.register(obj.getClass(), new JavaSerializer());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, obj);
        output.flush();
        output.close();

        byte[] b = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(new Base64().encode(b));
    }

    private <T extends Serializable> T deserializationObject(String obj,
                                                             Class<T> clazz) {
        kryo.setReferences(false);
        kryo.register(clazz, new JavaSerializer());

        ByteArrayInputStream bais = new ByteArrayInputStream(
                new Base64().decode(obj));
        Input input = new Input(bais);
        return (T) kryo.readClassAndObject(input);
    }
}
