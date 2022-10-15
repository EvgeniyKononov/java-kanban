package Task;

import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    private static Epic newEpic;
    private static InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    public void beforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager();
        newEpic = new Epic("Epic 1", "Epic Description 1");
        inMemoryTaskManager.addEpic(newEpic);
    }

    @DisplayName("GIVEN a new instance InMemoryTaskManager " +
            "WHEN a Epic creates " +
            "THEN the Epic status is NEW")
    @Test
    public void test1_EpicStatusIsNewWhenSubtaskListIsEmpty() {
        assertEquals(0, newEpic.getSubtaskID().size());
        assertEquals(Status.NEW, inMemoryTaskManager.getAnyTaskById(1).getStatus());
    }

    @DisplayName("GIVEN a new instance InMemoryTaskManager " +
            "WHEN a Epic creates and 2 Subtask with Status NEW " +
            "THEN the Epic status is NEW")
    @Test
    public void test2_EpicStatusIsNewWhenAllSubtasksHaveStatusNew() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", 1);
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask Description 2", 1);
        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        assertEquals(Status.NEW, inMemoryTaskManager.getAnyTaskById(2).getStatus());
        assertEquals(Status.NEW, inMemoryTaskManager.getAnyTaskById(3).getStatus());
        assertEquals(Status.NEW, inMemoryTaskManager.getAnyTaskById(1).getStatus());
    }

    @DisplayName("GIVEN a new instance InMemoryTaskManager " +
            "WHEN a Epic creates and 2 Subtask with Status DONE " +
            "THEN the Epic status is DONE")
    @Test
    public void test3_EpicStatusIsDoneWhenAllSubtasksHaveStatusDone() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", 1);
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask Description 2", 1);
        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        inMemoryTaskManager.amendSubtask(new Subtask("Subtask 1", "Subtask Description 1", 2, Status.DONE));
        inMemoryTaskManager.amendSubtask(new Subtask("Subtask 2", "Subtask Description 2", 3, Status.DONE));
        assertEquals(Status.DONE, inMemoryTaskManager.getAnyTaskById(2).getStatus());
        assertEquals(Status.DONE, inMemoryTaskManager.getAnyTaskById(3).getStatus());
        assertEquals(Status.DONE, inMemoryTaskManager.getAnyTaskById(1).getStatus());
    }

    @DisplayName("GIVEN a new instance InMemoryTaskManager " +
            "WHEN a Epic creates, one Subtask with Status DONE and another one Subtask with Status NEW " +
            "THEN the Epic status is IN_PROGRESS")
    @Test
    public void test4_EpicStatusIsInProgressWhenOneSubtasksHaveStatusDoneAndAnotherOneNew() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", 1);
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask Description 2", 1);
        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        inMemoryTaskManager.amendSubtask(new Subtask("Subtask 1", "Subtask Description 1", 2, Status.DONE));
        assertEquals(Status.DONE, inMemoryTaskManager.getAnyTaskById(2).getStatus());
        assertEquals(Status.NEW, inMemoryTaskManager.getAnyTaskById(3).getStatus());
        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getAnyTaskById(1).getStatus());
    }

    @DisplayName("GIVEN a new instance InMemoryTaskManager " +
            "WHEN a Epic creates and 2 Subtask with Status IN_PROGRESS " +
            "THEN the Epic status is IN_PROGRESS")
    @Test
    public void test5_EpicStatusIsInProgressWhenAllSubtasksHaveStatusInProgress() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", 1);
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask Description 2", 1);
        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        inMemoryTaskManager.amendSubtask(new Subtask("Subtask 1", "Subtask Description 1", 2, Status.IN_PROGRESS));
        inMemoryTaskManager.amendSubtask(new Subtask("Subtask 2", "Subtask Description 2", 3, Status.IN_PROGRESS));
        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getAnyTaskById(2).getStatus());
        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getAnyTaskById(3).getStatus());
        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getAnyTaskById(1).getStatus());
    }
}