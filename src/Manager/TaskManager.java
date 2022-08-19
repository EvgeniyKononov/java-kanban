package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;

public interface TaskManager {
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    void amendTask(Task updatedTask);

    void amendEpic(Epic updatedTask);

    void amendSubtask(Subtask updatedTask);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    void removeAnyTaskById(Integer id);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    Task getTaskById(Integer id);

    Epic getEpicById(Integer id);

    Subtask getSubtaskById(Integer id);

    ArrayList<Subtask> getAllSubtaskByEpicId(Integer id);

    Task getAnyTaskById(Integer id);

}
