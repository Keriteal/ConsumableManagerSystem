package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class ServerThread extends Thread{
    int port = 8088;
    private boolean continueRun = true;

    public void run() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        Executor executor = new ThreadPoolExecutor(10, 10000, 5, TimeUnit.SECONDS, blockingQueue);

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server Started");
            while (continueRun) {
                Socket socket = serverSocket.accept();
                executor.execute(() ->{
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                        System.out.println("Server Accepted:" + reader.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void quit() {
        this.continueRun = false;
    }
}
