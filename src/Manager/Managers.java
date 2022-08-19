package Manager;

public class Managers {

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return InMemoryTaskManager.inMemoryHistoryManager;
    }
}
