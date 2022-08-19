package Manager;

import Tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> historyTasks = new ArrayList<>();

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
