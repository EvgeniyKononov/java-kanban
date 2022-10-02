package Manager;

import Task.Epic;
import Task.Subtask;
import Task.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
        createDirIfNotExist(file);
    }

    public FileBackedTasksManager(Integer id, ArrayList<Task> tasks, ArrayList<Epic> epics,
                                  ArrayList<Subtask> subtasks, HistoryManager inMemoryHistoryManager, File file) {
        super(id, tasks, epics, subtasks, inMemoryHistoryManager);
        this.file = file;
    }

    private void createDirIfNotExist(File file){
        String path = file.getPath();
        int index = 0;
        while (path.indexOf(92, index) > 0) {
            index = path.indexOf(92, index);
            File dirPath = new File(path.substring(0, index));
            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
            index++;
        }
    }

    private void save() {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("id,type,name,status,description,epic");
            bufferedWriter.newLine();
            for (Task task : tasks) {
                bufferedWriter.write(task.toString());
                bufferedWriter.newLine();
            }
            for (Epic epic : epics) {
                bufferedWriter.write(epic.toString());
                bufferedWriter.newLine();
            }
            for (Subtask subtask : subtasks) {
                bufferedWriter.write(subtask.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            if (inMemoryHistoryManager.getHistory() != null) {
                bufferedWriter.write(Objects.requireNonNull(Managers.historyToString(inMemoryHistoryManager)));
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void amendTask(Task updatedTask) {
        super.amendTask(updatedTask);
        save();
    }

    @Override
    public void amendEpic(Epic updatedTask) {
        super.amendEpic(updatedTask);
        save();
    }

    @Override
    public void amendSubtask(Subtask updatedTask) {
        super.amendSubtask(updatedTask);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAnyTaskById(Integer id) {
        super.removeAnyTaskById(id);
        save();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = super.getAllTasks();
        save();
        return tasks;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epics = super.getAllEpics();
        save();
        return epics;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> subtasks = super.getAllSubtasks();
        save();
        return subtasks;
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public ArrayList<Subtask> getAllSubtaskByEpicId(Integer id) {
        ArrayList<Subtask> subtasks = super.getAllSubtaskByEpicId(id);
        save();
        return subtasks;
    }

    @Override
    public Task getAnyTaskById(Integer id) {
        Task task = super.getAnyTaskById(id);
        save();
        return task;
    }

    @Override
    public HistoryManager getInMemoryHistoryManager() {
        HistoryManager historyManager = super.getInMemoryHistoryManager();
        save();
        return historyManager;
    }
}
