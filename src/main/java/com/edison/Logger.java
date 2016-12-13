package com.edison;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangzhengfei on 16/7/5.
 */
public class Logger {

    private ThreadLocal<SimpleDateFormat> df = new ThreadLocal<SimpleDateFormat>();


    public void info(String str) {
        SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        df.set(dsf);
        String date_str = df.get().format(new Date());
        System.out.println(date_str + " [INFO] ["+Thread.currentThread().getName()+"]" + str);
    }

    public static void main(String[] args) {
        new Logger().info("dsadsa");
    }
}
