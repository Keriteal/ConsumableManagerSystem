package server;

import com.sun.net.httpserver.HttpServer;
import model.UserBean;
import server.handlers.ConfirmHandler;
import server.handlers.LoginHandler;
import server.handlers.RegisterHandler;
import utils.LoggerUtils;
import utils.SqlStatementUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Scanner;

public class ManagerMain {
    // UUID 对应 用户（类型，id）
    public static HashMap<String, ClientInstance> clientInstanceMap = new HashMap<>();

    public static final String HTTP_CONTENT_TYPE_PROTOBUF = "application/x-protobuf";
    public static final String HTTP_CONTENT_TYPE_JSON = "application/json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(SqlStatementUtils.generateQueryCondition(UserBean.class, UserBean.CONDITION_LOGIN));
        InetSocketAddress sa= new InetSocketAddress(8888);
        HttpServer server = null;

        try {
            server = HttpServer.create(sa, 0);
        } catch (IOException e) {
            LoggerUtils.logger.error("Server creating failed: " + e.getMessage());
        }
        if(server!=null) {
            server.createContext("/login", new LoginHandler());
            server.createContext("/register", new RegisterHandler());
            server.createContext("/confirm", new ConfirmHandler());
            server.start();
            System.out.println("Server running");
            String command = scanner.nextLine();
            if(command.contains("stop")) {
                System.out.println("Stopping...");
                server.stop(0);
                System.out.println("Stopped");
            }
        }
    }
}
