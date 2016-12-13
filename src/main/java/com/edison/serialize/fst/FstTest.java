package com.edison.serialize.fst;

import com.edison.serialize.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nustaq.serialization.FSTConfiguration;

import java.time.Instant;

/**
 * 文件名:com.edison.serialize.fst.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-12
 * 修改记录:
 */
public class FstTest {

    private User user;

    private long start;

    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    private static FSTConfiguration jconf = FSTConfiguration.createJsonConfiguration(false, false);


    @Before
    public void before() {
        user = new User(2000L, "xiaozhang", Byte.valueOf("1"), 19, "南京");
        start = Instant.now().toEpochMilli();
    }

    @After
    public void after() {
        System.out.println("处理耗时:" + (Instant.now().toEpochMilli() - start) / 1000f + " sec.");
    }

    @Test
    public void combined() {
        byte[] bits = serialize();
        User user = deserialize(bits);
        System.out.println(user);
        String json = toJSON(user);
        System.out.println(json);
    }

    public String toJSON(User user) {
        return jconf.asJsonString(user);
    }


    public byte[] serialize() {
        byte[] bits = conf.asByteArray(user);
        for (byte b : bits) {
            System.out.print(Byte.toString(b) + " ");
        }
        System.out.print("\n");
        return bits;
    }

    public <T> T deserialize(byte[] bits) {
        return (T) conf.asObject(bits);
    }

}
