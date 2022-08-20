package Manager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> historyTasks = new LinkedList<>();

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> tasks = new ArrayList<>();
        int j = 0;
        if (historyTasks.size() > 10) {
            for (int i = historyTasks.size() - 10; i < historyTasks.size(); i++) {
                tasks.add(j, historyTasks.get(i));
                j++;
            }
        } else {
            for (Task task : historyTasks) {
                tasks.add(j, task);
                j++;
            }
        }
        return tasks;
    }

    @Override
    public void add(Task task) {
        historyTasks.add(task);
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "historyTasks=" + historyTasks +
                '}';
    }
}
