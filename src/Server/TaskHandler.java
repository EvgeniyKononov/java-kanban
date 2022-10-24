package Server;

import Manager.FileBackedTasksManager;
import Manager.Managers;
import Task.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TaskHandler implements HttpHandler {
    public static final String PATH = "/tasks";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private FileBackedTasksManager fileBackedTasksManager = Managers.loadFromFile(new File("Resource/tasks.csv"));

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
    }


    private void writePrioritizedTasks(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getPrioritizedTasks());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void writeAllTasks(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getAllTasks());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void writeAllEpics(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getAllEpics());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void writeAllSubtask(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getAllSubtasks());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void writeHistory(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getInMemoryHistoryManager().getHistory());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void writeTask(HttpExchange httpExchange, Integer id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getTaskById(id));
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void writeSubtask(HttpExchange httpExchange, Integer id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getSubtaskById(id));
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void writeEpic(HttpExchange httpExchange, int id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getEpicById(id));
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void writeSubtasksByEpicId(HttpExchange httpExchange, Integer id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response = gson.toJson(fileBackedTasksManager.getAllSubtaskByEpicId(id));
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
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
    }

    private void addTask(HttpExchange httpExchange) throws IOException {
        String taskJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Task task = gson.fromJson(taskJson, Task.class);
        task.setStatus(Status.NEW);
        fileBackedTasksManager.addTask(task);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Задача добавлена".getBytes());
        }
    }

    private void amendTask(HttpExchange httpExchange, Integer id) throws IOException {
        String taskJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Task task = gson.fromJson(taskJson, Task.class);
        task.setId(id);
        fileBackedTasksManager.amendTask(task);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Задача изменена".getBytes());
        }
    }

    private void addSubtask(HttpExchange httpExchange) throws IOException {
        String subtaskJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Subtask subtask = gson.fromJson(subtaskJson, Subtask.class);
        subtask.setStatus(Status.NEW);
        fileBackedTasksManager.addTask(subtask);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Подзадача добавлена".getBytes());
        }
    }

    private void amendSubtask(HttpExchange httpExchange, Integer id) throws IOException {
        String subtaskJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Subtask subtask = gson.fromJson(subtaskJson, Subtask.class);
        subtask.setId(id);
        fileBackedTasksManager.amendSubtask(subtask);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Подзадача изменена".getBytes());
        }
    }

    private void addEpic(HttpExchange httpExchange) throws IOException {
        String epicJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Epic epic = gson.fromJson(epicJson, Epic.class);
        epic.setStatus(Status.NEW);
        fileBackedTasksManager.addTask(epic);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Эпик добавлен".getBytes());
        }
    }

    private void amendEpic(HttpExchange httpExchange, Integer id) throws IOException {
        String epicJson = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
        Epic epic = gson.fromJson(epicJson, Epic.class);
        epic.setId(id);
        fileBackedTasksManager.addTask(epic);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Эпик добавлен".getBytes());
        }
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
    }
    private void deleteAllTasks(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        fileBackedTasksManager.removeAllTasks();
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Все задачи удалены".getBytes());
        }
    }

    private void deleteAnyTaskById(HttpExchange httpExchange, Integer id) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        fileBackedTasksManager.removeAnyTaskById(id);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Задача удалена".getBytes());
        }
    }

    private void deleteAllEpics(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        fileBackedTasksManager.removeAllEpics();
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Все задачи удалены".getBytes());
        }
    }

    private void deleteAllSubtasks(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        fileBackedTasksManager.removeAllSubtasks();
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Все задачи удалены".getBytes());
        }
    }
}
