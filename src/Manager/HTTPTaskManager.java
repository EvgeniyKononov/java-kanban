package Manager;

import KVClient.KVTaskClient;
import Task.Epic;
import Task.Subtask;
import Task.Task;
import com.google.gson.Gson;

import java.io.File;
import java.util.Collections;

public class HTTPTaskManager extends FileBackedTasksManager {
    KVTaskClient kvTaskClient;
    Gson gson;

    public HTTPTaskManager(String url) {
        super(new File("Resources"));
        this.kvTaskClient = new KVTaskClient(url);
        this.gson = Managers.getGson();
        Task[] taskArray = gson.fromJson(kvTaskClient.load("tasks"), Task[].class);
        if (taskArray != null) {
            Collections.addAll(tasks, taskArray);
        }
        Epic[] epicArray = gson.fromJson(kvTaskClient.load("epics"), Epic[].class);
        if (epicArray != null) {
            Collections.addAll(epics, epicArray);
        }
        Subtask[] subtaskArray = gson.fromJson(kvTaskClient.load("subtasks"), Subtask[].class);
        if (subtaskArray != null) {
            Collections.addAll(subtasks, subtaskArray);
        }
        inMemoryHistoryManager = convertIdArrayToHistoryManager(gson.fromJson(kvTaskClient.load("history"),
                Integer[].class));
        id = loadId();
    }

    private HistoryManager convertIdArrayToHistoryManager(Integer[] idArray) {
        HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
        if (idArray != null) {
            for (int i = 0; i < idArray.length; i++) {
                inMemoryHistoryManager.add(getAnyTaskById(i));
            }
            return inMemoryHistoryManager;
        } else
            return new InMemoryHistoryManager();
    }

    private Integer loadId() {
        Integer currentId = 1;
        for (Task task : tasks) {
            if (task.getId() > currentId) {
                currentId = task.getId() + 1;
            }
        }
        for (Epic epic : epics) {
            if (epic.getId() > currentId) {
                currentId = epic.getId() + 1;
            }
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getId() > currentId) {
                currentId = subtask.getId() + 1;
            }
        }
        return currentId;
    }

    @Override
    protected void save() {
        String tasksJson = gson.toJson(tasks);
        kvTaskClient.save("tasks", tasksJson);
        String epicJson = gson.toJson(epics);
        kvTaskClient.save("epics", epicJson);
        String subtasksJson = gson.toJson(subtasks);
        kvTaskClient.save("subtasks", subtasksJson);
        String historyJson = gson.toJson(inMemoryHistoryManager.getHistory());
        kvTaskClient.save("history", historyJson);
    }
}
