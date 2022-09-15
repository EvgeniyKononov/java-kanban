package Manager;

import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryTaskManager implements TaskManager {
    private Integer id = 1;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final ArrayList<Epic> epics = new ArrayList<>();

    private final ArrayList<Subtask> subtasks = new ArrayList<>();
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    private void updateEpicStatus(ArrayList<Epic> epics) {
        for (Epic epic : epics) {
            Integer subtaskAmount = epic.getSubtaskID().size();
            Integer newSubtasksAmount = 0;
            Integer doneSubtaskAmount = 0;
            if (subtaskAmount == 0) {
                epic.setStatus(Status.NEW);
                break;
            } else {
                for (Integer subtaskID : epic.getSubtaskID()) {
                    for (Subtask subtask : subtasks) {
                        if (Objects.equals(subtask.getId(), subtaskID)) {
                            if (subtask.getStatus() == Status.NEW) {
                                newSubtasksAmount++;
                            } else if (subtask.getStatus() == Status.DONE) {
                                doneSubtaskAmount++;
                            }
                        }

                    }
                }
            }
            if (subtaskAmount.equals(newSubtasksAmount)) {
                epic.setStatus(Status.NEW);
            } else if (subtaskAmount.equals(doneSubtaskAmount)) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }


    @Override
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return subtasks;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return epics;
    }

    @Override
    public void removeAllSubtasks() {
        ArrayList<Task> copySubtasks = new ArrayList<>(subtasks);
        inMemoryHistoryManager.removeList(copySubtasks);
        for (Epic epic : epics) {
            epic.setSubtaskID(new ArrayList<>());
        }
        subtasks.clear();
        updateEpicStatus(epics);
    }

    @Override
    public void removeAllEpics() {
        List<Task> subtaskList = new ArrayList<>(subtasks);
        List<Task> epicList = new ArrayList<>(epics);
        inMemoryHistoryManager.removeList(epicList);
        inMemoryHistoryManager.removeList(subtaskList);
        removeAllSubtasks();
        epics.clear();
    }

    @Override
    public void removeAllTasks() {
        inMemoryHistoryManager.removeList(new ArrayList<>(tasks));
        tasks.clear();
    }

    @Override
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
        }
        for (Subtask subtask : subtasks) {
            if (Objects.equals(subtask.getId(), id)) {
                taskById = subtask;
                break;
            }
        }
        inMemoryHistoryManager.add(taskById);
        return taskById;
    }

    @Override
    public void addTask(Task task) {
        task.setId(id);
        tasks.add(task);
        id++;
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(id);
        epics.add(epic);
        id++;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(id);
        id++;
        subtasks.add(subtask);
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), subtask.getEpicId())) {
                epic.getSubtaskID().add(subtask.getId());
            }
        }
        updateEpicStatus(epics);
    }

    @Override
    public void amendTask(Task updatedTask) {
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), updatedTask.getId())) {
                tasks.set(tasks.indexOf(task), updatedTask);
                break;
            }
        }
    }

    @Override
    public void amendEpic(Epic updatedTask) {
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), updatedTask.getId())) {
                updatedTask.setSubtaskID(epic.getSubtaskID());
                updatedTask.setStatus(epic.getStatus());
                epics.set(epics.indexOf(epic), updatedTask);
                break;
            }
        }
    }

    @Override
    public void amendSubtask(Subtask updatedTask) {
        for (Subtask subtask : subtasks) {
            if (Objects.equals(subtask.getId(), updatedTask.getId())) {
                int index = subtasks.indexOf(subtask);
                subtasks.set(index, updatedTask);
            }
        }
        updateEpicStatus(epics);
    }

    @Override
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
        }
        List<Task> subtaskList = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            if (Objects.equals(subtask.getEpicId(), id)) {
                subtaskList.add(subtask);
            }
            if (Objects.equals(subtask.getId(), id)) {
                subtasks.remove(subtask);
                break;

            }
        }
        updateEpicStatus(epics);
        inMemoryHistoryManager.removeList(subtaskList);
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public ArrayList<Subtask> getAllSubtaskByEpicId(Integer id) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            if (Objects.equals(subtask.getEpicId(), id)) {
                subtasksList.add(subtask);
            }
        }
        return subtasksList;
    }

    @Override
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

    @Override
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

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtaskById = new Subtask();
        for (Subtask subtask : subtasks) {
            if (Objects.equals(subtask.getId(), id)) {
                subtaskById = subtask;
                break;
            }
        }
        inMemoryHistoryManager.add(subtaskById);
        return subtaskById;
    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }
}