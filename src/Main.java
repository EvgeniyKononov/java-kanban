import Manager.Managers;
import Manager.TaskManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Task task1 = new Task("задача 1", "описание 1");
        manager.addTask(task1);
        Task task2 = new Task("задача 2", "описание 2");
        manager.addTask(task2);
        Epic epic1 = new Epic("эпик 1", "описание 3");
        manager.addEpic(epic1);
        Subtask subtask1 = new Subtask("подзадача 1", "описание 4", epic1.getId());
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("подзадача 2", "описание 5", epic1.getId());
        manager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("подзадача 3", "описание 6", epic1.getId());
        manager.addSubtask(subtask3);
        Epic epic2 = new Epic("эпик 2", "описание 7");
        manager.addEpic(epic2);
        manager.getAnyTaskById(task1.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.getAnyTaskById(task2.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.getAnyTaskById(epic1.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.getAnyTaskById(epic2.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.getAnyTaskById(subtask1.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.getAnyTaskById(subtask3.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.getAnyTaskById(subtask2.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.getAnyTaskById(task1.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.getAnyTaskById(epic2.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.removeAnyTaskById(task1.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
        manager.removeAnyTaskById(epic1.getId());
        System.out.println(manager.getInMemoryHistoryManager().getHistory());
    }
}