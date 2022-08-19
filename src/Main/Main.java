package Main;

import Manager.InMemoryTaskManager;
import Manager.Managers;
import Manager.TaskManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Отобразить список всех задач по типу");
        System.out.println("2 - Удалить все задачи по типу");
        System.out.println("3 - Создать задачу");
        System.out.println("4 - Получение любой задачи по ID");
        System.out.println("5 - Изменить задачу");
        System.out.println("6 - Удаление по ID");
        System.out.println("7 - Получение списка всех подзадач определённого эпика");
        System.out.println("8 - Получение задачи по ID по типу");
        System.out.println("9 - Просмотреть историю просмотров");
        System.out.println("10 - Выход");
    }

    public static void main(String[] args) {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 10) {
            printMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println("Тип задачи: 1 - задача; 2 - эпик; 3 - подзадача");
                    int type = scanner.nextInt();
                    if (type == 1) {
                        System.out.println(manager.getAllTasks().toString());
                    } else if (type == 2) {
                        System.out.println(manager.getAllEpics().toString());
                    } else if (type == 3) {
                        System.out.println(manager.getAllSubtasks().toString());
                    }
                    break;
                }
                case 2: {
                    System.out.println("Тип задачи: 1 - задача; 2 - эпик; 3 - подзадача");
                    int type = scanner.nextInt();
                    if (type == 1) {
                        manager.removeAllTasks();
                    } else if (type == 2) {
                        manager.removeAllEpics();
                    } else if (type == 3) {
                        manager.removeAllSubtasks();
                    }
                    break;
                }
                case 3: {
                    System.out.println("Тип задачи: 1 - задача; 2 - эпик; 3 - подзадача");
                    int type = scanner.nextInt();
                    System.out.println("Имя задачи");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Описание");
                    String description = scanner.nextLine();
                    if (type == 1) {
                        manager.addTask(new Task(name, description));
                    } else if (type == 2) {
                        manager.addEpic(new Epic(name, description));
                    } else if (type == 3) {
                        System.out.println("Номер эпика");
                        int epicId = scanner.nextInt();
                        manager.addSubtask(new Subtask(name, description, epicId));
                    }
                    break;
                }
                case 4: {
                    System.out.println("Номер ID");
                    int id = scanner.nextInt();
                    System.out.println(manager.getAnyTaskById(id));
                    break;
                }
                case 5: {
                    System.out.println("Тип задачи: 1 - задача; 2 - эпик; 3 - подзадача");
                    int type = scanner.nextInt();
                    System.out.println("ID");
                    int id = scanner.nextInt();
                    System.out.println("Имя задачи");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Описание");
                    String description = scanner.nextLine();
                    System.out.println("Сатус (NEW, IN_PROGRESS, DONE)");
                    Status status = Status.valueOf(scanner.nextLine());
                    if (type == 1) {
                        manager.amendTask(new Task(name, description, id, status));
                    } else if (type == 2) {
                        manager.amendEpic(new Epic(name, description, id, status));
                    } else if (type == 3) {
                        manager.amendSubtask(new Subtask(name, description, id, status));
                    }
                    break;
                }
                case 6: {
                    System.out.println("номер ID");
                    int id = scanner.nextInt();
                    manager.removeAnyTaskById(id);
                    break;
                }
                case 7: {
                    System.out.println("номер ID");
                    int id = scanner.nextInt();
                    System.out.println(manager.getAllSubtaskByEpicId(id));
                    break;
                }
                case 8: {
                    System.out.println("Тип задачи: 1 - задача; 2 - эпик; 3 - подзадача");
                    int type = scanner.nextInt();
                    System.out.println("Номер ID");
                    int id = scanner.nextInt();
                    if (type == 1) {
                        System.out.println(manager.getTaskById(id).toString());
                    } else if (type == 2) {
                        System.out.println(manager.getEpicById(id).toString());
                    } else if (type == 3) {
                        System.out.println(manager.getSubtaskById(id).toString());
                    }
                    break;
                }
                case 9: {
                    System.out.println(Managers.getDefaultHistory().toString());
                    break;
                }
                case 10: {
                    break;
                }
                default: {
                    System.out.println("Введите цифру от 1 до 10");
                    break;
                }
            }
        }
    }
}