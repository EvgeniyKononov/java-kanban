package HttpServer;

import Manager.HTTPTaskManager;
import Manager.Managers;
import Task.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TaskHandler implements HttpHandler {
    public static final String PATH = "/tasks";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private HTTPTaskManager HTTPTaskManager = Managers.getDefault();
    private static Gson gson = Managers.getGson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Integer id = null;
        try {
            String method = httpExchange.getRequestMethod();
            URI uri = httpExchange.getRequestURI();
            String path = uri.getPath();
            String[] splitPath = path.split("/");
            String query = uri.getQuery();
            if (query != null) {
                String idString = query.substring(query.indexOf("=") + 1);
                id = Integer.parseInt(idString);
            }
            switch (method) {
                case "GET": {
                    handleGet(httpExchange, splitPath, id);
                    break;
                }
                case "POST": {
                    handlePost(httpExchange, splitPath, id);
                    break;
                }
                case "DELETE": {
                    handleDelete(httpExchange, splitPath, id);
                    break;
                }
                default:
                    httpExchange.sendResponseHeaders(501, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write("Метод не поддерживается".getBytes());
                    }
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(404, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Путь не найден".getBytes());
            }
        }
        httpExchange.close();
    }

    private void handleGet(HttpExchange httpExchange, String[] splitPath, Integer id) throws IOException {
        if (splitPath.length == 2) {
            writePrioritizedTasks(httpExchange);
            return;
        }
        switch (splitPath[2]) {
            case "task":
                if (id == null) {
                    writeAllTasks(httpExchange);
                } else {
                    writeTask(httpExchange, id);
                }
                break;
            case "subtask":
                if (splitPath.length == 3) {
                    if (id == null) {
                        writeAllSubtask(httpExchange);
                    } else {
                        writeSubtask(httpExchange, id);
                    }
                } else {
                    if (splitPath[3].equals("epic") && id != null) {
                        writeSubtasksByEpicId(httpExchange, id);
                    }
                }
                break;
            case "epic":
                if (id == null) {
                    writeAllEpics(httpExchange);
                } else {
                    writeEpic(httpExchange, id);
                }
                break;
            case "history":
                writeHistory(httpExchange);
                break;
            default:
                throw new IOException();
        }
        httpExchange.close();
    }


    private void writePrioritizedTasks(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getPrioritizedTasks());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void writeAllTasks(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getAllTasks());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void writeAllEpics(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getAllEpics());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void writeAllSubtask(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getAllSubtasks());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void writeHistory(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getInMemoryHistoryManager().getHistory());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void writeTask(HttpExchange httpExchange, Integer id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getTaskById(id));
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void writeSubtask(HttpExchange httpExchange, Integer id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getSubtaskById(id));
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void writeEpic(HttpExchange httpExchange, int id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getEpicById(id));
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void writeSubtasksByEpicId(HttpExchange httpExchange, Integer id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(HTTPTaskManager.getAllSubtaskByEpicId(id));
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();
    }

    private void handlePost(HttpExchange httpExchange, String[] splitPath, Integer id) throws IOException {
        if (splitPath.length == 2) {
            throw new IOException();
        }
        switch (splitPath[2]) {
            case "task":
                if (id == null) {
                    addTask(httpExchange);
                } else {
                    amendTask(httpExchange, id);
                }
                break;
            case "subtask":
                if (id == null) {
                    addSubtask(httpExchange);
                } else {
                    amendSubtask(httpExchange, id);
                }
                break;
            case "epic":
                if (id == null) {
                    addEpic(httpExchange);
                } else {
                    amendEpic(httpExchange, id);
                }
                break;
            default:
                throw new IOException();
        }
        httpExchange.close();
    }

    private void addTask(HttpExchange httpExchange) throws IOException {
        String taskJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Task task = gson.fromJson(taskJson, Task.class);
        task.setStatus(Status.NEW);
        HTTPTaskManager.addTask(task);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Задача добавлена".getBytes());
        }
        httpExchange.close();
    }

    private void amendTask(HttpExchange httpExchange, Integer id) throws IOException {
        String taskJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Task task = gson.fromJson(taskJson, Task.class);
        task.setId(id);
        HTTPTaskManager.amendTask(task);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Задача изменена".getBytes());
        }
        httpExchange.close();
    }

    private void addSubtask(HttpExchange httpExchange) throws IOException {
        String subtaskJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Subtask subtask = gson.fromJson(subtaskJson, Subtask.class);
        subtask.setStatus(Status.NEW);
        HTTPTaskManager.addSubtask(subtask);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Подзадача добавлена".getBytes());
        }
        httpExchange.close();
    }

    private void amendSubtask(HttpExchange httpExchange, Integer id) throws IOException {
        String subtaskJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Subtask subtask = gson.fromJson(subtaskJson, Subtask.class);
        subtask.setId(id);
        HTTPTaskManager.amendSubtask(subtask);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Подзадача изменена".getBytes());
        }
        httpExchange.close();
    }

    private void addEpic(HttpExchange httpExchange) throws IOException {
        String epicJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Epic epic = gson.fromJson(epicJson, Epic.class);
        epic.setStatus(Status.NEW);
        HTTPTaskManager.addEpic(epic);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Эпик добавлен".getBytes());
        }
        httpExchange.close();
    }

    private void amendEpic(HttpExchange httpExchange, Integer id) throws IOException {
        String epicJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Epic epic = gson.fromJson(epicJson, Epic.class);
        epic.setId(id);
        HTTPTaskManager.amendEpic(epic);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Эпик добавлен".getBytes());
        }
        httpExchange.close();
    }

    private void handleDelete(HttpExchange httpExchange, String[] splitPath, Integer id) throws IOException {
        if (splitPath.length == 2) {
            throw new IOException();
        }
        switch (splitPath[2]) {
            case "task":
                if (id == null) {
                    deleteAllTasks(httpExchange);
                } else {
                    deleteAnyTaskById(httpExchange, id);
                }
                break;
            case "subtask":
                if (id == null) {
                    deleteAllSubtasks(httpExchange);
                } else {
                    deleteAnyTaskById(httpExchange, id);
                }
                break;
            case "epic":
                if (id == null) {
                    deleteAllEpics(httpExchange);
                } else {
                    deleteAnyTaskById(httpExchange, id);
                }
                break;
            default:
                throw new IOException();
        }
        httpExchange.close();
    }

    private void deleteAllTasks(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        HTTPTaskManager.removeAllTasks();
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Все задачи удалены".getBytes());
        }
        httpExchange.close();
    }

    private void deleteAnyTaskById(HttpExchange httpExchange, Integer id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        HTTPTaskManager.removeAnyTaskById(id);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Задача удалена".getBytes());
        }
        httpExchange.close();
    }

    private void deleteAllEpics(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        HTTPTaskManager.removeAllEpics();
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Все задачи удалены".getBytes());
        }
        httpExchange.close();
    }

    private void deleteAllSubtasks(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        HTTPTaskManager.removeAllSubtasks();
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Все задачи удалены".getBytes());
        }
        httpExchange.close();
    }
}

