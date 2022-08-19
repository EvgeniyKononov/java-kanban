package Manager;

import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.Objects;

public class InMemoryTaskManager implements TaskManager {
    private Integer id = 1;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final ArrayList<Epic> epics = new ArrayList<>();
    static HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();


    private void updateEpicStatus(ArrayList<Epic> epics) {
        for (Epic epic : epics) {
            epic.setSubtaskAmount(0);
            epic.setNewSubtasksAmount(0);
            epic.setDoneSubtaskAmount(0);
            for (Subtask subtask : epic.getSubtask()) {
                if (subtask.getStatus() == Status.NEW) {
                    epic.setSubtaskAmount(epic.getSubtaskAmount() + 1);
                    epic.setNewSubtasksAmount(epic.getNewSubtasksAmount() + 1);
                } else if (subtask.getStatus() == Status.DONE) {
                    epic.setSubtaskAmount(epic.getSubtaskAmount() + 1);
                    epic.setDoneSubtaskAmount(epic.getDoneSubtaskAmount() + 1);
                } else {
                    epic.setSubtaskAmount(epic.getSubtaskAmount() + 1);
                }
            }
            if (Objects.equals(epic.getSubtaskAmount(), epic.getNewSubtasksAmount())) {
                epic.setStatus(Status.NEW);
            } else if (Objects.equals(epic.getSubtaskAmount(), epic.getDoneSubtaskAmount())) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> subtaskList = new ArrayList<>();
        for (Epic epic : epics) {
            subtaskList.addAll(epic.getSubtask());
        }
        return subtaskList;
    }

    public ArrayList<Epic> getAllEpics() {
        return epics;
    }

    public void removeAllSubtasks() {
        for (Epic epic : epics) {
            epic.setSubtask(new ArrayList<>());
        }
        updateEpicStatus(epics);
    }

    public void removeAllEpics() {
        epics.clear();
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public Task getAnyTaskById(Integer id) {
        Task taskById = new Task();
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), id)) {
                taskById = task;
                break;
            }
        }
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), id)) {
                taskById = epic;
                break;
            }
            for (Subtask subtask : epic.getSubtask()) {
                if (Objects.equals(subtask.getId(), id)) {
                    taskById = subtask;
                    break;
                }
            }
        }
        inMemoryHistoryManager.add(taskById);
        return taskById;
    }

    public void addTask(Task task) {
        task.setId(id);
        tasks.add(task);
        id++;
    }

    public void addEpic(Epic epic) {
        epic.setId(id);
        epics.add(epic);
        id++;
    }

    public void addSubtask(Subtask subtask) {
        subtask.setId(id);
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), subtask.getEpicId())) {
                epic.getSubtask().add(subtask);
                id++;
            }
        }
        updateEpicStatus(epics);
    }

    public void amendTask(Task updatedTask) {
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), updatedTask.getId())) {
                tasks.set(tasks.indexOf(task), updatedTask);
                break;
            }
        }
    }

    public void amendEpic(Epic updatedTask) {
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), updatedTask.getId())) {
                updatedTask.setSubtask(epic.getSubtask());
                updatedTask.setSubtaskAmount(epic.getSubtaskAmount());
                updatedTask.setDoneSubtaskAmount(epic.getDoneSubtaskAmount());
                epics.set(epics.indexOf(epic), updatedTask);
                break;
            }
        }
    }

    public void amendSubtask(Subtask updatedTask) {
        for (Epic epic : epics) {
            for (Subtask subtask : epic.getSubtask()) {
                if (Objects.equals(subtask.getId(), updatedTask.getId())) {
                    int index = epic.getSubtask().indexOf(subtask);
                    epic.getSubtask().set(index, updatedTask);
                }
            }
        }
        updateEpicStatus(epics);
    }

    public void removeAnyTaskById(Integer id) {
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), id)) {
                tasks.remove(task);
                break;
            }
        }
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), id)) {
                epics.remove(epic);
                break;
            }
            for (Subtask subtask : epic.getSubtask()) {
                if (Objects.equals(subtask.getId(), id)) {
                    epic.getSubtask().remove(subtask);
                    break;
                }
            }
        }
        updateEpicStatus(epics);
    }

    public ArrayList<Subtask> getAllSubtaskByEpicId(Integer id) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), id)) {
                subtasksList.addAll(epic.getSubtask());
            }
        }
        return subtasksList;
    }

    public Task getTaskById(Integer id) {
        Task taskById = new Task();
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), id)) {
                taskById = task;
                break;
            }
        }
        inMemoryHistoryManager.add(taskById);
        return taskById;
    }

    public Epic getEpicById(Integer id) {
        Epic epicById = new Epic();
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), id)) {
                epicById = epic;
                break;
            }
        }
        inMemoryHistoryManager.add(epicById);
        return epicById;
    }

    public Subtask getSubtaskById(Integer id) {
        Subtask subtaskById = new Subtask();

        for (Epic epic : epics) {
            for (Subtask subtask : epic.getSubtask()) {
                if (Objects.equals(subtask.getId(), id)) {
                    subtaskById = subtask;
                    break;
                }
            }
        }
        inMemoryHistoryManager.add(subtaskById);
        return subtaskById;
    }

    public ArrayList<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

}