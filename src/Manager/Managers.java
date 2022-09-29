package Manager;

import Task.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class Managers {

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static String historyToString(HistoryManager manager) {
        StringBuilder builderHistory = new StringBuilder();
        String stringHistory;
        ArrayList<Task> historyTasks = manager.getHistory();
        for (Task task : historyTasks) {
            builderHistory.append(task.getId() + ",");
        }
        stringHistory = String.valueOf(builderHistory);
        return stringHistory;
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        String[] split = value.split(",");
        for (int i = 0; i < split.length; i++) {
            history.add(parseInt(split[i]));
        }
        return history;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        String fileContents = readFileContentsOrNull(file);
        String[] lines = divideString(fileContents);
        InMemoryTaskManager inMemoryTaskManager = convertStringsToInMemoryTaskManager(lines);
        HistoryManager inMemoryHistoryManager = convertStringToHistoryManager(lines, inMemoryTaskManager);
        return new FileBackedTasksManager(inMemoryTaskManager.getId(), inMemoryTaskManager.getTasks(),
                inMemoryTaskManager.getEpics(), inMemoryTaskManager.getSubtasks(), inMemoryHistoryManager, file);
    }

    public static HistoryManager convertStringToHistoryManager(String[] lines,
                                                               InMemoryTaskManager inMemoryTaskManager) {
        HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
        String[] history = lines[lines.length - 1].split(",");
        for (int i = 0; i < history.length; i++) {
            int taskId = parseInt(history[i]);
            inMemoryHistoryManager.add(inMemoryTaskManager.getAnyTaskById(taskId));
        }
        return inMemoryHistoryManager;
    }

    public static InMemoryTaskManager convertStringsToInMemoryTaskManager(String[] lines) {
        Integer id = 0;
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
            return null;
        }
    }
}
