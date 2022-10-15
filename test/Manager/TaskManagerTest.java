package Manager;

import Task.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static Task.Status.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    @Test
    void test1_shouldReturnEqualTasksWhenContainsInManager() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        assertEquals(task, taskManager.getAnyTaskById(1));
    }

    @Test
    void test2_shouldReturnEqualTasksWhenSeveralTasksContainInManager() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        assertEquals(task2, taskManager.getAnyTaskById(2));
    }

    @Test
    void test3_shouldReturnEqualEpicWhenContainsInManager() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        assertEquals(epic, taskManager.getAnyTaskById(1));
    }

    @Test
    void test4_shouldReturnEqualEpicWhenSeveralEpicsContainInManager() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        Epic epic2 = new Epic("Epic 2", "Description 2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        assertEquals(epic2, taskManager.getAnyTaskById(2));
    }

    @Test
    void test5_shouldReturnEqualSubtaskWhenContainsInManager() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description 2", 1);
        taskManager.addSubtask(subtask);
        assertEquals(subtask, taskManager.getAnyTaskById(2));
    }

    @Test
    void test6_shouldReturnEqualSubtaskWhenSeveralSubtasksContainInManager() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 2", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 3", 1);
        taskManager.addSubtask(subtask2);
        assertEquals(subtask2, taskManager.getAnyTaskById(3));
    }

    @Test
    void test7_shouldThrowExceptionWhenEpicIsNotExist() {
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
        assertThrows(RuntimeException.class, () -> taskManager.addSubtask(subtask1));
    }

    @Test
    void test8_shouldReturnEqualTasksWhenMangerIsEmpty() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        Task amendedTask = new Task("Task 11", "Description 11", 1, Status.IN_PROGRESS);
        taskManager.amendTask(amendedTask);
        assertEquals(amendedTask, taskManager.getAnyTaskById(1));
    }

    @Test
    void test9_shouldThrowExceptionWhenTaskListIsEmpty() {
        Task amendedTask = new Task("Task 11", "Description 11", 1, Status.IN_PROGRESS);
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.amendTask(amendedTask)
        );
        assertEquals("Список задач пуст", exception.getMessage());
    }

    @Test
    void test10_shouldThrowExceptionWhenIncorrectIdTask() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        Task amendedTask = new Task("Task 11", "Description 11", 2, Status.IN_PROGRESS);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.amendTask(amendedTask)
        );
        assertEquals("Нет задачи с таким номером", exception.getMessage());
    }

    @Test
    void test11_shouldReturnEqualEpicWhenMangerIsEmpty() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Epic amendedEpic = new Epic("Epic 11", "Description 11", 1);
        taskManager.amendEpic(amendedEpic);
        assertEquals(amendedEpic, taskManager.getAnyTaskById(1));
    }

    @Test
    void test12_shouldThrowExceptionWhenEpicListIsEmpty() {
        Epic amendedEpic = new Epic("Epic 11", "Description 11", 1);
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.amendEpic(amendedEpic)
        );
        assertEquals("Список эпиков пуст", exception.getMessage());
    }

    @Test
    void test13_shouldThrowExceptionWhenIncorrectIdEpic() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Epic amendedEpic = new Epic("Epic 11", "Description 11", 2);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.amendEpic(amendedEpic)
        );
        assertEquals("Нет эпиков с таким номером", exception.getMessage());
    }

    @Test
    void test14_shouldReturnEqualSubtaskWhenMangerIsEmpty() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask);
        Subtask amendedSubtask = new Subtask("Epic 11", "Description 11", 2, Status.IN_PROGRESS);
        taskManager.amendSubtask(amendedSubtask);
        assertEquals(amendedSubtask, taskManager.getAnyTaskById(2));
    }

    @Test
    void test15_shouldThrowExceptionWhenSubtaskListIsEmpty() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask amendedSubtask = new Subtask("Epic 11", "Description 11", 2, Status.IN_PROGRESS);
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.amendSubtask(amendedSubtask)
        );
        assertEquals("Список подзадач пуст", exception.getMessage());
    }

    @Test
    void test16_shouldThrowExceptionWhenIncorrectIdSubtask() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask);
        Subtask amendedSubtask = new Subtask("Epic 11", "Description 11", 3, Status.IN_PROGRESS);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.amendSubtask(amendedSubtask)
        );
        assertEquals("Нет подзадачи с таким номером", exception.getMessage());
    }

    @Test
    void test17_shouldReturnTrueWhenMangerHasTasks() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        taskManager.removeAllTasks();
        assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    void test18_shouldReturnTrueWhenMangerHaveNotTasks() {
        taskManager.removeAllTasks();
        assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    void test19_shouldReturnTrueWhenMangerHasEpics() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        taskManager.removeAllEpics();
        assertTrue(taskManager.getAllEpics().isEmpty());
    }

    @Test
    void test20_shouldReturnTrueWhenMangerHaveNotEpics() {
        taskManager.removeAllEpics();
        assertTrue(taskManager.getAllEpics().isEmpty());
    }

    @Test
    void test21_shouldReturnTrueWhenMangerHasEpicsAndSubtasks() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", 1);
        taskManager.addSubtask(subtask2);
        taskManager.removeAllEpics();
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void test22_shouldReturnTrueWhenMangerHasSubtask() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", 1);
        taskManager.addSubtask(subtask2);
        taskManager.removeAllSubtasks();
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void test23_shouldReturnTrueWhenMangerHaveNotTasks() {
        taskManager.removeAllSubtasks();
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void test24_shouldReturnEpicStatusNewWhenSubtasksRemoved() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", 1);
        taskManager.addSubtask(subtask2);
        taskManager.removeAllSubtasks();
        assertEquals(NEW, taskManager.getAnyTaskById(1).getStatus());
    }

    @Test
    void test25_shouldReturnNullWhenDeleteTask() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        Epic epic = new Epic("Epic 1", "Description 2");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 3", 2);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 4", 2);
        taskManager.addSubtask(subtask2);
        taskManager.removeAnyTaskById(1);
        assertThrows(RuntimeException.class, () -> taskManager.getAnyTaskById(1));
    }

    @Test
    void test26_shouldThrowExceptionWhenDeleteEpic() {
        Epic epic = new Epic("Epic 1", "Description 2");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 3", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 4", 1);
        taskManager.addSubtask(subtask2);
        taskManager.removeAnyTaskById(1);
        assertThrows(NullPointerException.class, () -> taskManager.getAnyTaskById(2));
    }

    @Test
    void test27_shouldReturnTrueWhenDeleteEpicWhichHaveSubtasks() {
        Epic epic = new Epic("Epic 1", "Description 2");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 3", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 4", 1);
        taskManager.addSubtask(subtask2);
        taskManager.removeAnyTaskById(1);
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void test28_shouldReturnNullWhenDeleteSubtask() {
        Epic epic = new Epic("Epic 1", "Description 2");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 3", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 4", 1);
        taskManager.addSubtask(subtask2);
        taskManager.removeAnyTaskById(2);
        assertThrows(NoSuchElementException.class, () -> taskManager.getAnyTaskById(2));
    }

    @Test
    void test29_shouldThrowExceptionWhenTaskListIsEmpty() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.removeAnyTaskById(1)
        );
        assertEquals("Список задач пуст", exception.getMessage());
    }

    @Test
    void test30_shouldThrowExceptionWhenIncorrectIdNumber() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.removeAnyTaskById(2)
        );
        assertEquals("Нет задачи с таким номером", exception.getMessage());
    }

    @Test
    void test31_shouldReturnTaskListWhenExist() {
        Task task1 = new Task("Task 1", "Description 1");
        taskManager.addTask(task1);
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.addTask(task2);
        assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    void test32_shouldReturnZeroWhenTaskLisIsEmpty() {
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    void test33_shouldReturnEpicListWhenExist() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic1);
        Epic epic2 = new Epic("Epic 2", "Description 2");
        taskManager.addEpic(epic2);
        assertEquals(2, taskManager.getAllEpics().size());
    }

    @Test
    void test34_shouldReturnZeroWhenEpicLisIsEmpty() {
        assertEquals(0, taskManager.getAllEpics().size());
    }

    @Test
    void test35_shouldReturnSubtaskListWhenExist() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", 1);
        taskManager.addSubtask(subtask2);
        assertEquals(2, taskManager.getAllSubtasks().size());
    }

    @Test
    void test36_shouldReturnZeroWhenSubtaskLisIsEmpty() {
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    void test37_shouldReturnEqualTaskWhenContainsInTaskManager() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        Task expectedTask = new Task("Task 1", "Description 1", 1, NEW);
        assertEquals(expectedTask, taskManager.getTaskById(1));
    }

    @Test
    void test38_shouldThrowExceptionWhenTaskListIsEmpty() {
        Task task = new Task("Task 1", "Description 1");
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getTaskById(1)
        );
        assertEquals("Список задач пуст", exception.getMessage());
    }

    @Test
    void test39_shouldThrowExceptionWhenIncorrectIdTask() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.getTaskById(2)
        );
        assertEquals("Нет задачи с таким номером", exception.getMessage());
    }

    @Test
    void test40_shouldReturnEqualEpicWhenMangerContainsInManager() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Epic expectedEpic = new Epic("Epic 1", "Description 1", 1);
        assertEquals(expectedEpic, taskManager.getEpicById(1));
    }

    @Test
    void test41_shouldThrowExceptionWhenEpicListIsEmpty() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getEpicById(1)
        );
        assertEquals("Список эпиков пуст", exception.getMessage());
    }

    @Test
    void test42_shouldThrowExceptionWhenIncorrectIdEpic() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.getEpicById(2)
        );
        assertEquals("Нет эпика с таким номером", exception.getMessage());
    }

    @Test
    void test43_shouldReturnEqualSubtaskWhenContainsInManager() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask);
        Subtask expectedSubtask = new Subtask("Subtask 1", "Description 1", 2, NEW);
        assertEquals(expectedSubtask, taskManager.getSubtaskById(2));
    }

    @Test
    void test44_shouldThrowExceptionWhenSubtaskListIsEmpty() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getSubtaskById(1)
        );
        assertEquals("Список подзадач пуст", exception.getMessage());
    }

    @Test
    void test45_shouldThrowExceptionWhenIncorrectIdSubtask() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.getSubtaskById(3)
        );
        assertEquals("Нет подзадачи с таким номером", exception.getMessage());
    }

    @Test
    void test46_shouldReturnSubtaskListWhenEpicExist() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", 1);
        taskManager.addSubtask(subtask2);
        assertEquals(2, taskManager.getAllSubtaskByEpicId(1).size());
    }

    @Test
    void test47_shouldThrowExceptionWhenEpicListIsEmpty() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getAllSubtaskByEpicId(1)
        );
        assertEquals("Список эпиков пуст", exception.getMessage());
    }

    @Test
    void test48_shouldThrowExceptionWhenIncorrectIdEpic() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
        taskManager.addTask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", 1);
        taskManager.addTask(subtask2);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.getEpicById(2)
        );
        assertEquals("Нет эпика с таким номером", exception.getMessage());
    }

    @Test
    void test49_shouldReturnEqualTaskWhenContainsInTaskManager() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        Task expectedTask = new Task("Task 1", "Description 1", 1, NEW);
        assertEquals(expectedTask, taskManager.getAnyTaskById(1));
    }

    @Test
    void test50_shouldThrowExceptionWhenTaskListIsEmpty() {
        Task task = new Task("Task 1", "Description 1");
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getAnyTaskById(1)
        );
        assertEquals("Список задач пуст", exception.getMessage());
    }

    @Test
    void test51_shouldThrowExceptionWhenIncorrectIdTask() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskManager.getAnyTaskById(2)
        );
        assertEquals("Нет задачи с таким номером", exception.getMessage());
    }

    @Test
    void test52_shouldReturnEqualInMemoryHistoryManagerWhenExist() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.addTask(task);
        taskManager.getAnyTaskById(1);
        assertEquals(1, taskManager.getInMemoryHistoryManager().getHistory().size());
    }

    @Test
    void test53_shouldReturnTrueWhenHistoryIsEmpty() {
        assertThrows(NullPointerException.class, () -> taskManager.getInMemoryHistoryManager().getHistory().isEmpty());
    }

    @Test
    void test54_getEndTimeForTaskMethodTest() {
        Task task = new Task("Task 1",
                "Description 1",
                10,
                LocalDateTime.of(2023, 10, 15, 10, 0));
        assertEquals(LocalDateTime.of(2023, 10, 15, 10, 10), task.getEndTime());
    }

    @Test
    void test55_getTimeForEpicTest() {
        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1",
                "Description 2",
                1,
                20,
                LocalDateTime.of(2023, 10, 15, 10, 0));
        taskManager.addSubtask(subtask1);
        assertAll(
                () -> assertEquals(LocalDateTime.of(2023, 10, 15, 10, 20),
                        epic.getEndTime()),
                () -> assertEquals(LocalDateTime.of(2023, 10, 15, 10, 0),
                        epic.getStartTime()),
                () -> assertEquals(20, epic.getDuration())
        );

        Subtask subtask2 = new Subtask("Subtask 2",
                "Description 3",
                1,
                5,
                LocalDateTime.of(2023, 10, 16, 10, 0));
        taskManager.addSubtask(subtask2);
        assertAll(
                () -> assertEquals(LocalDateTime.of(2023, 10, 16, 10, 5),
                        epic.getEndTime()),
                () -> assertEquals(LocalDateTime.of(2023, 10, 15, 10, 0),
                        epic.getStartTime()),
                () -> assertEquals(25, epic.getDuration())
        );
    }

    @Test
    void test56_getPrioritizedTasksTest() {
        Task task1 = new Task("Task 1",
                "Description 1",
                10,
                LocalDateTime.of(2025, 10, 15, 10, 0));
        Task task2 = new Task("Task 1",
                "Description 1",
                10,
                LocalDateTime.of(2023, 11, 15, 10, 0));
        Epic epic = new Epic("Epic 1", "Description 1");
        Subtask subtask1 = new Subtask("Subtask 1",
                "Description 2",
                3,
                20,
                LocalDateTime.of(2023, 11, 16, 15, 15));
        Subtask subtask2 = new Subtask("Subtask 2",
                "Description 3",
                3,
                5,
                LocalDateTime.of(2023, 1, 16, 10, 0));
        Task task3 = new Task("Task 3",
                "Description 1");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addTask(task3);
        LinkedList<Integer> expectedList = new LinkedList<>();
        expectedList.add(5);
        expectedList.add(2);
        expectedList.add(4);
        expectedList.add(1);
        expectedList.add(6);
        assertEquals(expectedList, taskManager.getPrioritizedTasks());
    }

    @Test
    void test57_checkPossibilityToCreateTaskAtThisTimeTest() {
        Task task1 = new Task("Task 1",
                "Description 1",
                30,
                LocalDateTime.of(2025, 10, 15, 10, 0));
        taskManager.addTask(task1);
        Task task2 = new Task("Task 2",
                "Description 2",
                30,
                LocalDateTime.of(2025, 10, 15, 10, 15));
        taskManager.addTask(task2);
        assertEquals(1, taskManager.getAllTasks().size());
    }
}
