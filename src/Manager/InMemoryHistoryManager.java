package Manager;

import Tasks.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> historyTasks = new LinkedList<>();

    @Override
    public LinkedList<Task> getHistory() {
        return historyTasks;
    }

    @Override
    public void add(Task task) {
        historyTasks.addLast(task);
        if (historyTasks.size() == 11) {
            historyTasks.removeFirst();
        }
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "historyTasks=" + historyTasks +
                '}';
    }
}
