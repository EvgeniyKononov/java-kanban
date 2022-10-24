package Manager;

import Task.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    FileBackedTasksManager taskManager;
    File path = new File("Resource/tasks.csv");

    @BeforeEach
    void beforeEach() {
        taskManager = new FileBackedTasksManager(path);
        super.taskManager = taskManager;
    }

   /* @AfterEach
    void afterEach() {
        path.delete();
    }*/

    @Test
    void test1_shouldReturnEqualTasksWhenContainsInManager() {
        super.test1_shouldReturnEqualTasksWhenContainsInManager();
    }

    @Test
    void test2_shouldReturnEqualTasksWhenSeveralTasksContainInManager() {
        super.test2_shouldReturnEqualTasksWhenSeveralTasksContainInManager();
    }

    @Test
    void test3_shouldReturnEqualEpicWhenContainsInManager() {
        super.test3_shouldReturnEqualEpicWhenContainsInManager();
    }

    @Test
    void test4_shouldReturnEqualEpicWhenSeveralEpicsContainInManager() {
        super.test4_shouldReturnEqualEpicWhenSeveralEpicsContainInManager();
    }

    @Test
    void test5_shouldReturnEqualSubtaskWhenContainsInManager() {
        super.test5_shouldReturnEqualSubtaskWhenContainsInManager();
    }

    @Test
    void test6_shouldReturnEqualSubtaskWhenSeveralSubtasksContainInManager() {
        super.test6_shouldReturnEqualSubtaskWhenSeveralSubtasksContainInManager();
    }

    @Test
    void test7_shouldThrowExceptionWhenEpicIsNotExist() {
        super.test7_shouldThrowExceptionWhenEpicIsNotExist();
    }

    @Test
    void test8_shouldReturnEqualTasksWhenMangerIsEmpty() {
        super.test8_shouldReturnEqualTasksWhenMangerIsEmpty();
    }

    @Test
    void test9_shouldThrowExceptionWhenTaskListIsEmpty() {
        super.test9_shouldThrowExceptionWhenTaskListIsEmpty();
    }

    @Test
    void test10_shouldThrowExceptionWhenIncorrectIdTask() {
        super.test10_shouldThrowExceptionWhenIncorrectIdTask();
    }

    @Test
    void test11_shouldReturnEqualEpicWhenMangerIsEmpty() {
        super.test11_shouldReturnEqualEpicWhenMangerIsEmpty();
    }

    @Test
    void test12_shouldThrowExceptionWhenEpicListIsEmpty() {
        super.test12_shouldThrowExceptionWhenEpicListIsEmpty();
    }

    @Test
    void test13_shouldThrowExceptionWhenIncorrectIdEpic() {
        super.test13_shouldThrowExceptionWhenIncorrectIdEpic();
    }

    @Test
    void test14_shouldReturnEqualSubtaskWhenMangerIsEmpty() {
        super.test14_shouldReturnEqualSubtaskWhenMangerIsEmpty();
    }

    @Test
    void test15_shouldThrowExceptionWhenSubtaskListIsEmpty() {
        super.test15_shouldThrowExceptionWhenSubtaskListIsEmpty();
    }

    @Test
    void test16_shouldThrowExceptionWhenIncorrectIdSubtask() {
        super.test16_shouldThrowExceptionWhenIncorrectIdSubtask();
    }

    @Test
    void test17_shouldReturnTrueWhenMangerHasTasks() {
        super.test17_shouldReturnTrueWhenMangerHasTasks();
    }

    @Test
    void test18_shouldReturnTrueWhenMangerHaveNotTasks() {
        super.test18_shouldReturnTrueWhenMangerHaveNotTasks();
    }

    @Test
    void test19_shouldReturnTrueWhenMangerHasEpics() {
        super.test19_shouldReturnTrueWhenMangerHasEpics();
    }

    @Test
    void test20_shouldReturnTrueWhenMangerHaveNotEpics() {
        super.test20_shouldReturnTrueWhenMangerHaveNotEpics();
    }

    @Test
    void test21_shouldReturnTrueWhenMangerHasEpicsAndSubtasks() {
        super.test21_shouldReturnTrueWhenMangerHasEpicsAndSubtasks();
    }

    @Test
    void test22_shouldReturnTrueWhenMangerHasSubtask() {
        super.test22_shouldReturnTrueWhenMangerHasSubtask();
    }

    @Test
    void test23_shouldReturnTrueWhenMangerHaveNotTasks() {
        super.test23_shouldReturnTrueWhenMangerHaveNotTasks();
    }

    @Test
    void test24_shouldReturnEpicStatusNewWhenSubtasksRemoved() {
        super.test24_shouldReturnEpicStatusNewWhenSubtasksRemoved();
    }

    @Test
    void test25_shouldReturnNullWhenDeleteTask() {
        super.test25_shouldReturnNullWhenDeleteTask();
    }

    @Test
    void test26_shouldThrowExceptionWhenDeleteEpic() {
        super.test26_shouldThrowExceptionWhenDeleteEpic();
    }

    @Test
    void test27_shouldReturnTrueWhenDeleteEpicWhichHaveSubtasks() {
        super.test27_shouldReturnTrueWhenDeleteEpicWhichHaveSubtasks();
    }

    @Test
    void test28_shouldReturnNullWhenDeleteSubtask() {
        super.test28_shouldReturnNullWhenDeleteSubtask();
    }

    @Test
    void test29_shouldThrowExceptionWhenTaskListIsEmpty() {
        super.test29_shouldThrowExceptionWhenTaskListIsEmpty();
    }

    @Test
    void test30_shouldThrowExceptionWhenIncorrectIdNumber() {
        super.test30_shouldThrowExceptionWhenIncorrectIdNumber();
    }

    @Test
    void test31_shouldReturnTaskListWhenExist() {
        super.test31_shouldReturnTaskListWhenExist();
    }

    @Test
    void test32_shouldReturnZeroWhenTaskLisIsEmpty() {
        super.test32_shouldReturnZeroWhenTaskLisIsEmpty();
    }

    @Test
    void test33_shouldReturnEpicListWhenExist() {
        super.test33_shouldReturnEpicListWhenExist();
    }

    @Test
    void test34_shouldReturnZeroWhenEpicLisIsEmpty() {
        super.test34_shouldReturnZeroWhenEpicLisIsEmpty();
    }

    @Test
    void test35_shouldReturnSubtaskListWhenExist() {
        super.test35_shouldReturnSubtaskListWhenExist();
    }

    @Test
    void test36_shouldReturnZeroWhenSubtaskLisIsEmpty() {
        super.test36_shouldReturnZeroWhenSubtaskLisIsEmpty();
    }

    @Test
    void test37_shouldReturnEqualTaskWhenContainsInTaskManager() {
        super.test37_shouldReturnEqualTaskWhenContainsInTaskManager();
    }

    @Test
    void test38_shouldThrowExceptionWhenTaskListIsEmpty() {
        super.test38_shouldThrowExceptionWhenTaskListIsEmpty();
    }

    @Test
    void test39_shouldThrowExceptionWhenIncorrectIdTask() {
        super.test39_shouldThrowExceptionWhenIncorrectIdTask();
    }

    @Test
    void test40_shouldReturnEqualEpicWhenMangerContainsInManager() {
        super.test40_shouldReturnEqualEpicWhenMangerContainsInManager();
    }

    @Test
    void test41_shouldThrowExceptionWhenEpicListIsEmpty() {
        super.test41_shouldThrowExceptionWhenEpicListIsEmpty();
    }

    @Test
    void test42_shouldThrowExceptionWhenIncorrectIdEpic() {
        super.test42_shouldThrowExceptionWhenIncorrectIdEpic();
    }

    @Test
    void test43_shouldReturnEqualSubtaskWhenContainsInManager() {
        super.test43_shouldReturnEqualSubtaskWhenContainsInManager();
    }

    @Test
    void test44_shouldThrowExceptionWhenSubtaskListIsEmpty() {
        super.test44_shouldThrowExceptionWhenSubtaskListIsEmpty();
    }

    @Test
    void test45_shouldThrowExceptionWhenIncorrectIdSubtask() {
        super.test45_shouldThrowExceptionWhenIncorrectIdSubtask();
    }

    @Test
    void test46_shouldReturnSubtaskListWhenEpicExist() {
        super.test46_shouldReturnSubtaskListWhenEpicExist();
    }

    @Test
    void test47_shouldThrowExceptionWhenEpicListIsEmpty() {
        super.test47_shouldThrowExceptionWhenEpicListIsEmpty();
    }

    @Test
    void test48_shouldThrowExceptionWhenIncorrectIdEpic() {
        super.test48_shouldThrowExceptionWhenIncorrectIdEpic();
    }

    @Test
    void test49_shouldReturnEqualTaskWhenContainsInTaskManager() {
        super.test49_shouldReturnEqualTaskWhenContainsInTaskManager();
    }

    @Test
    void test50_shouldThrowExceptionWhenTaskListIsEmpty() {
        super.test50_shouldThrowExceptionWhenTaskListIsEmpty();
    }

    @Test
    void test51_shouldThrowExceptionWhenIncorrectIdTask() {
        super.test51_shouldThrowExceptionWhenIncorrectIdTask();
    }

    @Test
    void test52_shouldReturnEqualInMemoryHistoryManagerWhenExist() {
        super.test52_shouldReturnEqualInMemoryHistoryManagerWhenExist();
    }

    @Test
    void test53_shouldReturnTrueWhenHistoryIsEmpty() {
        super.test53_shouldReturnTrueWhenHistoryIsEmpty();
    }

    @Test
    void test54_getEndTimeForTaskMethodTest() {
        super.test54_getEndTimeForTaskMethodTest();
    }

    @Test
    void test55_getTimeForEpicTest() {
        super.test55_getTimeForEpicTest();
    }

    @Test
    void test56_getPrioritizedTasksTest() {
        super.test56_getPrioritizedTasksTest();
    }

    @Test
    void test57_checkPossibilityToCreateTaskAtThisTimeTest() {
        super.test57_checkPossibilityToCreateTaskAtThisTimeTest();
    }

    @Test
    void test58_writeReadFinalTest() {
        Task task1 = new Task("задача 1", "описание 1");
        taskManager.addTask(task1);
        Task task2 = new Task("задача 2", "описание 2");
        taskManager.addTask(task2);
        Epic epic1 = new Epic("эпик 1", "описание 3");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("подзадача 1", "описание 4", epic1.getId());
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("подзадача 2", "описание 5", epic1.getId());
        taskManager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("подзадача 3", "описание 6", epic1.getId());
        taskManager.addSubtask(subtask3);
        Epic epic2 = new Epic("эпик 2", "описание 7");
        taskManager.addEpic(epic2);
        taskManager.getAnyTaskById(task1.getId());
        taskManager.getAnyTaskById(task2.getId());
        taskManager.getAnyTaskById(epic1.getId());
        taskManager.getAnyTaskById(epic2.getId());
        taskManager.getAnyTaskById(subtask1.getId());
        taskManager.getAnyTaskById(subtask3.getId());
        taskManager.getAnyTaskById(subtask2.getId());
        taskManager.getAnyTaskById(task1.getId());
        taskManager.getAnyTaskById(epic2.getId());
        taskManager.removeAnyTaskById(task1.getId());
        taskManager.removeAnyTaskById(epic1.getId());
        assertEquals(1, taskManager.getTasks().size());
        assertEquals(1, taskManager.getEpics().size());
        assertEquals(0, taskManager.getSubtasks().size());
        assertEquals(2, taskManager.getInMemoryHistoryManager().getHistory().size());
        FileBackedTasksManager manager1 = Managers.loadFromFile(new File("Resource/tasks.csv"));
        assertEquals(1, manager1.getTasks().size());
        assertEquals(1, manager1.getEpics().size());
        assertEquals(2, manager1.getInMemoryHistoryManager().getHistory().size());
        Task task3 = new Task("задача 3", "описание 3");
        manager1.addTask(task3);
        manager1.getAnyTaskById(task3.getId());
        assertEquals(2, manager1.getTasks().size());
        assertEquals(1, manager1.getEpics().size());
        assertEquals(3, manager1.getInMemoryHistoryManager().getHistory().size());
    }

    @Test
    void test59_writeReadFinalTest() {
        Task task1 = new Task("задача 1", "описание 1");
        taskManager.addTask(task1);
        taskManager.getAnyTaskById(task1.getId());
        FileBackedTasksManager manager1 =
                Managers.loadFromFile(new File("Z:\\Users\\tolst\\IdeaProjects\\java-kanban\\Resource"));
        Task task3 = new Task("задача 3", "описание 3");
      //  manager1.addTask(task3);
        assertThrows(ManagerSaveException.class, () -> manager1.addTask(task3));

    }

    @Test
    void test999_writeReadFinalTest() {
        Task task1 = new Task("задача 1", "описание 1", 30,
                LocalDateTime.of(2026, 10, 15, 10, 0));
        taskManager.addTask(task1);
        Task task2 = new Task("задача 2", "описание 2");
        taskManager.addTask(task2);
        Epic epic1 = new Epic("эпик 1", "описание 3");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("подзадача 1", "описание 4", epic1.getId(), 30,
                LocalDateTime.of(2025, 10, 15, 10, 0));
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("подзадача 2", "описание 5", epic1.getId(),50,
                LocalDateTime.of(2025, 10, 15, 16, 0));
        taskManager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("подзадача 3", "описание 6", epic1.getId(),10,
                LocalDateTime.of(2025, 10, 16, 16, 0));
        taskManager.addSubtask(subtask3);
        //Epic epic2 = new Epic("эпик 2", "описание 7");
       // taskManager.addEpic(epic2);
        taskManager.getAnyTaskById(task1.getId());
        taskManager.getAnyTaskById(task2.getId());
        taskManager.getAnyTaskById(epic1.getId());
      //  taskManager.getAnyTaskById(epic2.getId());
        taskManager.getAnyTaskById(subtask1.getId());
        taskManager.getAnyTaskById(subtask3.getId());
        taskManager.getAnyTaskById(subtask2.getId());
        taskManager.getAnyTaskById(task1.getId());
     //   taskManager.getAnyTaskById(epic2.getId());
        //taskManager.removeAnyTaskById(task1.getId());
       // taskManager.removeAnyTaskById(epic1.getId());
        assertEquals(2, taskManager.getTasks().size());

    }


}


