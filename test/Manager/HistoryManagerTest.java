package Manager;

import Task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {

    HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void test1_addTest() {
        historyManager.add(new Task("Task 1", "Description 1", 1));
        historyManager.add(new Task("Task 2", "Description 2", 2));
        historyManager.add(new Task("Task 3", "Description 3", 3));
        assertEquals(3, historyManager.getHistory().size());
        historyManager.add(new Task("Task 1", "Description 1", 2));
        assertEquals(3, historyManager.getHistory().size());
    }

    @Test
    void test2_getHistoryTest() {
        assertThrows(NullPointerException.class, () -> historyManager.getHistory().size());
        historyManager.add(new Task("Task 1", "Description 1", 2));
        assertEquals(1, historyManager.getHistory().size());
        historyManager.add(new Task("Task 1", "Description 1", 2));
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void test3_removeTest() {
        historyManager.remove(1);
        assertNull(historyManager.getHistory());
        historyManager.add(new Task("Task 1", "Description 1", 1));
        historyManager.add(new Task("Task 2", "Description 2", 2));
        historyManager.add(new Task("Task 3", "Description 3", 3));
        historyManager.remove(1);
        assertEquals(2, historyManager.getHistory().size());
        historyManager.remove(1);
        assertEquals(2, historyManager.getHistory().size());
        historyManager.remove(3);
        assertEquals(1, historyManager.getHistory().size());
        historyManager.remove(2);
        assertNull(historyManager.getHistory());
    }

    @Test
    void test4_removeList() {
        List<Task> taskList = new ArrayList<>();
        historyManager.removeList(taskList);
        assertNull(historyManager.getHistory());
        historyManager.add(new Task("Task 1", "Description 1", 1));
        historyManager.add(new Task("Task 2", "Description 2", 2));
        historyManager.add(new Task("Task 3", "Description 3", 3));
        taskList.add(new Task("Task 1", "Description 1", 1));
        taskList.add(new Task("Task 2", "Description 2", 2));
        historyManager.removeList(taskList);
        assertEquals(1, historyManager.getHistory().size());
        historyManager.removeList(taskList);
        assertEquals(1, historyManager.getHistory().size());
    }
}
