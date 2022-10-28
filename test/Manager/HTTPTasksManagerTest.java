package Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HTTPTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    HTTPTaskManager taskManager;


    @BeforeEach
    void beforeEach() {
        taskManager = Managers.getDefault();
        super.taskManager = taskManager;
    }


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
}