package Manager;

import HttpServer.LocalDateTimeAdapter;
import KVServer.KVServer;
import Task.Epic;
import Task.Subtask;
import Task.Task;
import Task.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class Managers {
    public static HTTPTaskManager getDefault() {
        return new HTTPTaskManager("http://localhost:8078/");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager createFileBackedTasksManager(File file) {
        return new FileBackedTasksManager(file);
    }

    public static KVServer createDefaultKVServer() throws IOException {
        return new KVServer();
    }

    public static String historyToString(HistoryManager manager) {
        StringBuilder builderHistory = new StringBuilder();
        String stringHistory;
        ArrayList<Task> historyTasks = manager.getHistory();
        for (Task task : historyTasks) {
            builderHistory.append(task.getId()).append(",");
        }
        stringHistory = String.valueOf(builderHistory);
        return stringHistory;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        String fileContents = readFileContentsOrNull(file);
        String[] lines = divideString(fileContents);
        if (lines != null) {
            InMemoryTaskManager inMemoryTaskManager = convertStringsToInMemoryTaskManager(lines);
            HistoryManager inMemoryHistoryManager = convertStringToHistoryManager(lines, inMemoryTaskManager);

            return new FileBackedTasksManager(inMemoryTaskManager.getId(), inMemoryTaskManager.getTasks(),
                    inMemoryTaskManager.getEpics(), inMemoryTaskManager.getSubtasks(), inMemoryHistoryManager, file);
        } else
            return new FileBackedTasksManager(file);
    }

    public static HistoryManager convertStringToHistoryManager(String[] lines,
                                                               InMemoryTaskManager inMemoryTaskManager) {
        if (lines[lines.length - 1].length() != 0) {
            HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
            String[] history = lines[lines.length - 1].split(",");
            for (String s : history) {
                int taskId = parseInt(s);
                inMemoryHistoryManager.add(inMemoryTaskManager.getAnyTaskById(taskId));
            }
            return inMemoryHistoryManager;
        } else
            return new InMemoryHistoryManager();
    }

    public static InMemoryTaskManager convertStringsToInMemoryTaskManager(String[] lines) {
        int id = 1;
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Epic> epics = new ArrayList<>();
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (int i = 1; i < lines.length - 2; i++) {
            String[] lineContents = lines[i].split(",");
            if (parseInt(lineContents[0]) >= id) {
                id = parseInt(lineContents[0]) + 1;
            }
            if (Type.valueOf(lineContents[1]) == Type.TASK) {
                Task task = new Task();
                task = task.fromString(lines[i]);
                tasks.add(task);
            }
            if (Type.valueOf(lineContents[1]) == Type.EPIC) {
                Epic epic = new Epic();
                epic = epic.fromString(lines[i]);
                epics.add(epic);
            }
            if (Type.valueOf(lineContents[1]) == Type.SUBTASK) {
                Subtask subtask = new Subtask();
                subtask = subtask.fromString(lines[i]);
                subtasks.add(subtask);
                for (Epic epic : epics) {
                    if (Objects.equals(epic.getId(), subtask.getEpicId())) {
                        ArrayList<Integer> subtaskId = epic.getSubtaskID();
                        subtaskId.add(subtask.getId());
                        epic.setSubtaskID(subtaskId);
                    }
                }
            }
        }
        return new InMemoryTaskManager(id, tasks, epics, subtasks);
    }

    public static String[] divideString(String fileContents) {
        if (fileContents != null) {
            return fileContents.split("\n");
        } else
            return null;
    }

    public static String readFileContentsOrNull(File file) {
        try {
            return Files.readString(Path.of(file.getCanonicalPath()));
        } catch (IOException exception) {
            System.out.println("Невозможно прочитать файл из указанного пути. Создаём новый");
            return null;
        }
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.serializeNulls();
        return gsonBuilder.create();
    }
}
