package server;

import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientInstance;
import server.handlers.ConfirmHandler;
import server.handlers.LoginHandler;
import server.handlers.RegisterHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class ManagerMain {
    public static HashMap<String, ClientInstance> clientInstanceMap = new HashMap<>();
    public static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        InetSocketAddress sa= new InetSocketAddress(8888);
        HttpServer server = null;

        try {
            server = HttpServer.create(sa, 0);
        } catch (IOException e) {
            logger.error("Server creating failed: " + e.getMessage());
        }
        if(server!=null) {
            server.createContext("/login", new LoginHandler());
            server.createContext("/register", new RegisterHandler());
            server.createContext("/confirm", new ConfirmHandler());
            server.start();
            System.out.println("Server running");
        }
    }
}
