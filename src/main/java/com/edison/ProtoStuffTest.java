package com.edison;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 文件名:${PACKAGE_NAME}.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-07-11
 * 修改记录:
 */
public class ProtoStuffTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis() ;
        User user = new User();
        user.setUserId("U000001");
        user.setUserName("测试用户");
        Schema<User> schema = RuntimeSchema.createFrom(User.class);
        LinkedBuffer buffer = LinkedBuffer.allocate(4096);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(user, schema, buffer);
        } finally {
            buffer.clear();
        }
        long end = System.currentTimeMillis() ;
        long userTime = end - start;
        System.out.println(userTime+","+new String(protostuff));
        User userA = new User();
        ProtostuffIOUtil.mergeFrom(protostuff,userA,schema);
        System.out.println(userA);
    }

}
