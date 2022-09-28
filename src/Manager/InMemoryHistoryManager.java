package Manager;

import Tasks.Task;
import List.CustomLinkedList;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList historyTasks = new CustomLinkedList();

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

    public void setHistoryTasks(CustomLinkedList historyTasks) {
        this.historyTasks = historyTasks;
    }
}