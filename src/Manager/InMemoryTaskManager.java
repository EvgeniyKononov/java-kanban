package Manager;

import Task.Epic;
import Task.Status;
import Task.Subtask;
import Task.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected Integer id = 1;
    protected ArrayList<Task> tasks = new ArrayList<>();
    protected ArrayList<Epic> epics = new ArrayList<>();
    protected ArrayList<Subtask> subtasks = new ArrayList<>();
    protected HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
    }

    public InMemoryTaskManager(Integer id, ArrayList<Task> tasks, ArrayList<Epic> epics, ArrayList<Subtask> subtasks) {
        this.id = id;
        this.tasks = tasks;
        this.epics = epics;
        this.subtasks = subtasks;
    }

    public InMemoryTaskManager(Integer id, ArrayList<Task> tasks, ArrayList<Epic> epics,
                               ArrayList<Subtask> subtasks, HistoryManager inMemoryHistoryManager) {
        this.id = id;
        this.tasks = tasks;
        this.epics = epics;
        this.subtasks = subtasks;
        this.inMemoryHistoryManager = inMemoryHistoryManager;
    }

    private boolean checkPossibilityToCreateTaskAtThisTime(Task task) {
        LocalDateTime taskStartTime = task.getStartTime();
        boolean isPossible = true;
        if (Objects.equals(taskStartTime, null)) {
            return isPossible;
        }
        for (Task checkedTask : tasks) {
            if (Objects.equals(checkedTask.getStartTime(), null)) {
                break;
            }
            if (taskStartTime.isAfter(checkedTask.getStartTime())
                    && taskStartTime.isBefore(checkedTask.getEndTime())
                    || taskStartTime.equals(checkedTask.getStartTime())
                    || taskStartTime.equals(checkedTask.getEndTime())
            ) {
                isPossible = false;
                return isPossible;
            }
        }
        for (Subtask checkedSubtask : subtasks) {
            if (Objects.equals(checkedSubtask.getStartTime(), null)) {
                break;
            }
            if (taskStartTime.isAfter(checkedSubtask.getStartTime())
                    && taskStartTime.isBefore(checkedSubtask.getEndTime())
                    && taskStartTime.equals(checkedSubtask.getStartTime())
                    && taskStartTime.equals(checkedSubtask.getEndTime())) {
                isPossible = false;
                return isPossible;
            }
        }
        return isPossible;
    }

    private void updateEpicTime(ArrayList<Epic> epics) {
        for (Epic epic : epics) {
            Integer duration = 0;
            LocalDateTime startTime = LocalDateTime.MAX;
            LocalDateTime endTime = LocalDateTime.MIN;
            ;
            for (Integer subtaskId : epic.getSubtaskID()) {
                Subtask subtask = getSubtaskById(subtaskId);
                if (Objects.equals(subtask.getDuration(), null)) {
                    break;
                }
                duration += subtask.getDuration();
                if (startTime.isAfter(subtask.getStartTime())) {
                    startTime = subtask.getStartTime();
                }
                if (endTime.isBefore(subtask.getEndTime())) {
                    endTime = subtask.getEndTime();
                }
                epic.setDuration(duration);
                epic.setStartTime(startTime);
                epic.setEndTime(endTime);
            }
        }
    }


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
    public TreeSet<Task> getPrioritizedTasks() {
        Comparator<Task> tasksComparator = Comparator.comparing(Task::getStartTime,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Task::getId);
        TreeSet<Task> tasksTreeSet = new TreeSet<>(tasksComparator);
        tasksTreeSet.addAll(tasks);
        tasksTreeSet.addAll(subtasks);
        return tasksTreeSet;
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
        updateEpicTime(epics);
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
        if (tasks.isEmpty() && epics.isEmpty() && subtasks.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        boolean isTaskExist = false;
        Task taskById = new Task();
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), id)) {
                taskById = task;
                isTaskExist = true;
                break;
            }
        }
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), id)) {
                taskById = epic;
                isTaskExist = true;
                break;
            }
        }
        for (Subtask subtask : subtasks) {
            if (Objects.equals(subtask.getId(), id)) {
                taskById = subtask;
                isTaskExist = true;
                break;
            }
        }
        if (!isTaskExist) {
            throw new NoSuchElementException("Нет задачи с таким номером");
        }
        inMemoryHistoryManager.add(taskById);
        return taskById;
    }

    @Override
    public void addTask(Task task) {
        if (checkPossibilityToCreateTaskAtThisTime(task)) {
            task.setId(id);
            tasks.add(task);
            id++;
        }
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(id);
        epics.add(epic);
        id++;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (checkPossibilityToCreateTaskAtThisTime(subtask)) {
            subtask.setId(id);
            id++;
            int subtaskEpicId = subtask.getEpicId();
            boolean isEpicExist = false;
            for (Epic epic : epics) {
                if (Objects.equals(epic.getId(), subtaskEpicId)) {
                    epic.getSubtaskID().add(subtask.getId());
                    isEpicExist = true;
                }
            }
            if (isEpicExist) {
                subtasks.add(subtask);
                updateEpicStatus(epics);
                updateEpicTime(epics);
            } else {
                id--;
                throw new RuntimeException("Нет Эпика с таким ID");
            }
        }
    }

    @Override
    public void amendTask(Task updatedTask) {
        if (tasks.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        if (checkPossibilityToCreateTaskAtThisTime(updatedTask)) {
            boolean isTaskExist = false;
            for (Task task : tasks) {
                if (Objects.equals(task.getId(), updatedTask.getId())) {
                    tasks.set(tasks.indexOf(task), updatedTask);
                    isTaskExist = true;
                    break;
                }
            }
            if (!isTaskExist) {
                throw new NoSuchElementException("Нет задачи с таким номером");
            }
        }
    }

    @Override
    public void amendEpic(Epic updatedTask) {
        if (epics.isEmpty()) {
            throw new NullPointerException("Список эпиков пуст");
        }
        boolean isEpicExist = false;
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), updatedTask.getId())) {
                updatedTask.setSubtaskID(epic.getSubtaskID());
                updatedTask.setStatus(epic.getStatus());
                epics.set(epics.indexOf(epic), updatedTask);
                isEpicExist = true;
                break;
            }
        }
        if (!isEpicExist) {
            throw new NoSuchElementException("Нет эпиков с таким номером");
        }
    }

    @Override
    public void amendSubtask(Subtask updatedTask) {
        if (subtasks.isEmpty()) {
            throw new NullPointerException("Список подзадач пуст");
        }
        if (checkPossibilityToCreateTaskAtThisTime(updatedTask)) {
            boolean isSubtaskExist = false;
            for (Subtask subtask : subtasks) {
                if (Objects.equals(subtask.getId(), updatedTask.getId())) {
                    int index = subtasks.indexOf(subtask);
                    subtasks.set(index, updatedTask);
                    isSubtaskExist = true;
                }
            }
            updateEpicStatus(epics);
            updateEpicTime(epics);
            if (!isSubtaskExist) {
                throw new NoSuchElementException("Нет подзадачи с таким номером");
            }
        }
    }

    @Override
    public void removeAnyTaskById(Integer id) {
        if (tasks.isEmpty() && epics.isEmpty() && subtasks.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        boolean isTaskExist = false;
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), id)) {
                tasks.remove(task);
                isTaskExist = true;
                break;
            }
        }
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), id)) {
                epics.remove(epic);
                isTaskExist = true;
                break;
            }
        }
        List<Task> subtaskList = new ArrayList<>();
        Iterator<Subtask> subtaskIterator = subtasks.iterator();
        while (subtaskIterator.hasNext()) {
            Subtask subtask = subtaskIterator.next();
            if (Objects.equals(subtask.getEpicId(), id)) {
                subtaskList.add(subtask);
                subtaskIterator.remove();
            }
            if (Objects.equals(subtask.getId(), id)) {
                subtasks.remove(subtask);
                for (Epic epic : epics) {
                    if (Objects.equals(subtask.getEpicId(), epic.getId())) {
                        epic.getSubtaskID().remove(subtask.getId());
                        break;
                    }
                }
                isTaskExist = true;
            }
        }
        updateEpicStatus(epics);
        updateEpicTime(epics);
        inMemoryHistoryManager.removeList(subtaskList);
        inMemoryHistoryManager.remove(id);
        if (!isTaskExist) {
            throw new NoSuchElementException("Нет задачи с таким номером");
        }
    }

    @Override
    public ArrayList<Subtask> getAllSubtaskByEpicId(Integer id) {
        if (epics.isEmpty()) {
            throw new NullPointerException("Список эпиков пуст");
        }
        boolean isSubtaskExist = false;
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            if (Objects.equals(subtask.getEpicId(), id)) {
                subtasksList.add(subtask);
                isSubtaskExist = true;
            }
        }
        if (!isSubtaskExist) {
            throw new NoSuchElementException("Нет эпиков с таким номером");
        }
        return subtasksList;
    }

    @Override
    public Task getTaskById(Integer id) {
        if (tasks.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        boolean isTaskExist = false;
        Task taskById = new Task();
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), id)) {
                taskById = task;
                isTaskExist = true;
                break;
            }
        }
        if (!isTaskExist) {
            throw new NoSuchElementException("Нет задачи с таким номером");
        }
        inMemoryHistoryManager.add(taskById);
        return taskById;
    }

    @Override
    public Epic getEpicById(Integer id) {
        if (epics.isEmpty()) {
            throw new NullPointerException("Список эпиков пуст");
        }
        boolean isEpicExist = false;
        Epic epicById = new Epic();
        for (Epic epic : epics) {
            if (Objects.equals(epic.getId(), id)) {
                epicById = epic;
                isEpicExist = true;
                break;
            }
        }
        if (!isEpicExist) {
            throw new NoSuchElementException("Нет эпика с таким номером");
        }
        inMemoryHistoryManager.add(epicById);
        return epicById;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        if (subtasks.isEmpty()) {
            throw new NullPointerException("Список подзадач пуст");
        }
        boolean isSubtaskExist = false;
        Subtask subtaskById = new Subtask();
        for (Subtask subtask : subtasks) {
            if (Objects.equals(subtask.getId(), id)) {
                subtaskById = subtask;
                isSubtaskExist = true;
                break;
            }
        }
        if (!isSubtaskExist) {
            throw new NoSuchElementException("Нет подзадачи с таким номером");
        }
        inMemoryHistoryManager.add(subtaskById);
        return subtaskById;
    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Epic> getEpics() {
        return epics;
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public Integer getId() {
        return id;
    }
}