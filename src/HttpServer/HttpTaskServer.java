package HttpServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static HttpServer.TaskHandler.PATH;

public class HttpTaskServer {
    private static final int PORT = 8080;

    private final String HTTP_SERVER_START_MSG = "HTTP-сервер запущен на " + PORT + " порту!";
    private final HttpServer httpServer;

    private final TaskHandler taskHandler = new TaskHandler();

    public HttpTaskServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext(PATH, taskHandler);
        httpServer.start();
        System.out.println(HTTP_SERVER_START_MSG);
    }

    public void stop() {
        httpServer.stop(0);
    }
}
