package Manager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void remove(int id);

    ArrayList<Task> getHistory();

    void removeList(List<Task> taskList);
}
