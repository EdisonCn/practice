package com.edison.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by wangzhengfei on 16/7/9.
 */
public class BioSocketServer {

    private int port;

    public BioSocketServer(int port) throws IOException, InterruptedException {
        this.port = port;
        ServerSocket server = new ServerSocket(port);
        ExecutorService executor = new ThreadPoolExecutor(5, 100, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        //server.setSoTimeout(2000);
        while (true) {
            Socket socket = server.accept();
            System.out.println("========================");
            executor.submit(new Worker(socket));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new BioSocketServer(8000);
    }

    private static class Worker implements Callable<Map<String, Object>> {
        private Socket socket;

        public Worker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public Map<String, Object> call() throws Exception {
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            if ((len = is.read(buffer)) > 0){
                System.out.println(new String(buffer,0,len));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String date_str = sdf.format(new Date());
            String response = "HTTP/1.1 200 OK" + "\r\n\r\n" + "hellworld,current time:"+date_str;
            socket.getOutputStream().write(response.getBytes());
            socket.getOutputStream().flush();
            socket.close();
            return null;
        }
    }
}
