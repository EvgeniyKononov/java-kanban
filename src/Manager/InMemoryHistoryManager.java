package Manager;

import Tasks.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> historyTasks = new LinkedList<>();

    @Override
    public LinkedList<Task> getHistory() {
        LinkedList<Task> returnTasks = new LinkedList<>();
        LinkedList<Task> copyOfHistoryTasks = new LinkedList<>(historyTasks);
        for (int i = 0; i < 10 && copyOfHistoryTasks.size() != 0; i++) {
            returnTasks.addFirst(copyOfHistoryTasks.pollLast());
        }
        return returnTasks;
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
