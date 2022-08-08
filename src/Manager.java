import java.util.ArrayList;
import java.util.Objects;

public class Manager {
    Integer id = 1;
    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<Epic> epics = new ArrayList<>();

    void removeAllTasks() {
        tasks.clear();
        epics.clear();
    }

    Task getTaskById(Integer id) {
        Task taskById = new Task();
        for (Task task : tasks) {
            if (Objects.equals(task.id, id)) {
                taskById = task;
                break;
            }
        }
        for (Epic epic : epics) {
            if (Objects.equals(epic.id, id)) {
                taskById = epic;
                break;
            }
            for (Subtask subtask : epic.subtask) {
                if (Objects.equals(subtask.id, id)) {
                    taskById = subtask;
                    break;
                }
            }
        }
        return taskById;
    }

    void addTask(Task task) {
        task.setId(id);
        tasks.add(task);
        id++;
    }

    void addEpic(Epic epic) {
        epic.setId(id);
        epics.add(epic);
        id++;
    }

    void addSubtask(Subtask subtask) {
        subtask.setId(id);
        for (Epic epic : epics) {
            if (Objects.equals(epic.id, subtask.epicId)) {
                epic.subtask.add(subtask);
                epic.subtaskAmount++;
                epic.newSubtasksAmount++;
                id++;
            }
        }
    }

    void amendTask(Task updatedTask) {
        for (Task task : tasks) {
            if (Objects.equals(task.id, updatedTask.id)) {
                tasks.set(tasks.indexOf(task), updatedTask);
                break;
            }
        }
    }

    void amendEpic(Epic updatedTask) {
        for (Epic epic : epics) {
            if (Objects.equals(epic.id, updatedTask.id)) {
                updatedTask.subtask = epic.subtask;
                updatedTask.subtaskAmount = epic.subtaskAmount;
                updatedTask.doneSubtaskAmount = epic.doneSubtaskAmount;
                epics.set(epics.indexOf(epic), updatedTask);
                break;
            }
        }
    }

    void amendSubtask(Subtask updatedTask) {
        for (Epic epic : epics) {
            for (Subtask subtask : epic.subtask) {
                if (Objects.equals(subtask.id, updatedTask.id)) {
                    int index = epic.subtask.indexOf(subtask);
                    if (updatedTask.status == Status.NEW && subtask.status != Status.NEW) {
                        epic.newSubtasksAmount++;
                    } else if (updatedTask.status != Status.NEW && subtask.status == Status.NEW) {
                        epic.newSubtasksAmount--;
                    }
                    if (updatedTask.status == Status.DONE && subtask.status != Status.DONE) {
                        epic.doneSubtaskAmount++;
                    } else if (updatedTask.status != Status.DONE && subtask.status == Status.DONE) {
                        epic.doneSubtaskAmount--;
                    }
                    if (Objects.equals(epic.subtaskAmount, epic.doneSubtaskAmount)) {
                        epic.status = Status.DONE;
                    } else if (Objects.equals(epic.subtaskAmount, epic.newSubtasksAmount)) {
                        epic.status = Status.NEW;
                    } else {
                        epic.status = Status.IN_PROGRESS;
                    }
                    epic.subtask.set(index, updatedTask);
                }
            }
        }
    }

    void removeTaskById(Integer id) {
        for (Task task : tasks) {
            if (Objects.equals(task.id, id)) {
                tasks.remove(task);
                break;
            }
        }
        for (Epic epic : epics) {
            if (Objects.equals(epic.id, id)) {
                epics.remove(epic);
                break;
            }
            for (Subtask subtask : epic.subtask) {
                if (Objects.equals(subtask.id, id)) {
                    epic.subtask.remove(subtask);
                    break;
                }
            }
        }
    }

    Epic getAllSubtaskByEpicId(Integer id) {
        Epic taskById = new Epic();
        for (Epic epic : epics) {
            if (Objects.equals(epic.id, id)) {
                taskById = epic;
                break;
            }
        }
        return taskById;
    }
}