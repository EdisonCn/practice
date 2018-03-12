package com.edison.serialize;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 *
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2017-02-20
 * 修改记录:
 */
public class ProtostuffTest {

    public static void main(String[] args) {
        // new User instance
        User user = new User(1,"name", Byte.valueOf("2"),22,"上海市虹口区虹关路368号");
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<User> schema = RuntimeSchema.createFrom(User.class);
        // 序列化 user 类
        byte[] bytes = ProtostuffIOUtil.toByteArray(user, schema, buffer);
        User t = new User();
        // 将 bytes 反序列化 ， 存储到 t 变量里面
        ProtostuffIOUtil.mergeFrom(bytes, t, schema);
        System.out.println("反序列化结果：" + t);
    }
}
