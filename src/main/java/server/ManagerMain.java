package server;

import com.sun.net.httpserver.HttpServer;
import model.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.handlers.item.ItemsHandler;
import server.handlers.admin.ListAdminHandler;
import server.handlers.record.RecordHandler;
import server.handlers.record.CommitHandler;
import server.handlers.record.ConfirmHandler;
import server.handlers.user.LoginHandler;
import server.handlers.user.LogoutHandler;
import server.handlers.user.RegisterHandler;
import server.handlers.user.ListUserHandler;
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

    private static final Logger logger = LogManager.getLogger();
    public static boolean running = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(SqlStatementUtils.generateQueryCondition(UserBean.class, UserBean.CONDITION_LOGIN));
        InetSocketAddress sa= new InetSocketAddress(8888);
        HttpServer server = null;

        try {
            server = HttpServer.create(sa, 0);
        } catch (IOException e) {
            logger.fatal("Server creating failed: " + e.getMessage());
        }
        if(server!=null) {
            server.createContext("/user/login", new LoginHandler());
            server.createContext("/user/register", new RegisterHandler());
            server.createContext("/user/logout", new LogoutHandler());

            server.createContext("/record/commit", new CommitHandler());
            server.createContext("/record/confirm", new ConfirmHandler());

            server.createContext("/items", new ItemsHandler());
            server.createContext("/admin/list", new ListAdminHandler());
            server.createContext("/user/list", new ListUserHandler());

            server.createContext("/records/unconfirmed", new RecordHandler());

            server.start();
            System.out.println("Server running");
            while (running) {
                String command = scanner.nextLine();
                if(command.contains("stop")) {
                    System.out.println("Stopping...");
                    server.stop(0);
                    System.out.println("Stopped");
                    running = false;
                } else if(command.contains("print")){
                    System.out.println(clientInstanceMap.toString());
                }
            }
        }
    }
}
