package Manager;

import Task.Task;
import List.CustomLinkedList;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList historyTasks = new CustomLinkedList();

    @Override
    public ArrayList<Task> getHistory() {
        return historyTasks.getHistory();
    }

    @Override
    public void add(Task task) {
        historyTasks.add(task);
    }

    @Override
    public void remove(int id) {
        historyTasks.remove(id);
    }

    @Override
    public void removeList(List<Task> taskList) {
        if (taskList != null) {
            for (Task task : taskList) {
                historyTasks.remove(task.getId());
            }
        }
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "historyTasks=" + historyTasks +
                '}';
    }

    public CustomLinkedList getHistoryTasks() {
        return historyTasks;
    }
}