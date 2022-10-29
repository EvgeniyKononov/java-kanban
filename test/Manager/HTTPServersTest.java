package Manager;

import HttpServer.HttpTaskServer;
import KVClient.KVTaskClient;
import KVServer.KVServer;
import Task.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HTTPServersTest {
    private static KVServer kvServer;
    private static Gson gson = Managers.getGson();
    HttpTaskServer httpTaskServer;

    @BeforeAll
    static void beforeAll() throws IOException {
    }

    @BeforeEach
    void beforeEach() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
    }

    @AfterEach
    void afterEach() {
        kvServer.stop();
        httpTaskServer.stop();

    }

    @Test
    void test1_PostTestAddingTaskViaHttpServerAndGettingViaKVServer() {
        try {
            Task task1 = new Task("Task 1", "Description 1", 1, Status.NEW);
            String task1Json = gson.toJson(task1);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .POST(HttpRequest.BodyPublishers.ofString(task1Json))
                    .build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078/");
            task1.setId(1);
            assertEquals("[" + task1Json + "]", kvTaskClient.load("tasks"));
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    @Test
    void test2_PostTestAddingEpicViaHttpServerAndGettingViaKVServer() {
        try {
            Epic epic1 = new Epic("Epic 1", "Description 1");
            String epic1Json = gson.toJson(epic1);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/"))
                    .POST(HttpRequest.BodyPublishers.ofString(epic1Json))
                    .build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078/");
            Epic[] epic2 = gson.fromJson(kvTaskClient.load("epics"), Epic[].class);
            assertEquals(epic1.getName(), epic2[0].getName());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    @Test
    void test3_PostTestAddingEpicAndSubtaskViaHttpServerAndGettingViaKVServer() {
        try {
            Epic epic1 = new Epic("Epic 1", "Description 1");
            String epic1Json = gson.toJson(epic1);
            Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
            String subtask1Json = gson.toJson(subtask1);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/"))
                    .POST(HttpRequest.BodyPublishers.ofString(epic1Json))
                    .build();
            httpClient.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                    .POST(HttpRequest.BodyPublishers.ofString(subtask1Json))
                    .build();
            httpClient.send(httpRequest2, HttpResponse.BodyHandlers.ofString());
            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078/");
            Subtask[] subtasks2 = gson.fromJson(kvTaskClient.load("subtasks"), Subtask[].class);
            assertEquals(subtask1.getName(), subtasks2[0].getName());
            assertEquals(1, subtasks2[0].getEpicId());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    @Test
    void test4_PostTestAddingTaskViaHttpServerThenAmendAndGettingViaKVServer() {
        try {
            Task task1 = new Task("Task 1", "Description 1", 1, Status.NEW);
            String task1Json = gson.toJson(task1);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .POST(HttpRequest.BodyPublishers.ofString(task1Json))
                    .build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Task amendedTask1 = new Task("Task 1", "Description 1", 1, Status.DONE);
            String amendedTask1Json = gson.toJson(amendedTask1);
            HttpRequest httpRequest2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/?=1"))
                    .POST(HttpRequest.BodyPublishers.ofString(amendedTask1Json))
                    .build();
            httpClient.send(httpRequest2, HttpResponse.BodyHandlers.ofString());
            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078/");
            task1.setId(1);
            assertEquals("[" + amendedTask1Json + "]", kvTaskClient.load("tasks"));
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    @Test
    void test5_PostTestAddingEpicViaHttpServerThenAmendAndGettingViaKVServer() {
        try {
            Epic epic1 = new Epic("Epic 1", "Description 1");
            String epic1Json = gson.toJson(epic1);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/"))
                    .POST(HttpRequest.BodyPublishers.ofString(epic1Json))
                    .build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Epic amendedEpic1 = new Epic("Epic 111", "Description 1");
            String amendedEpic1Json = gson.toJson(amendedEpic1);
            HttpRequest httpRequest2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/?=1"))
                    .POST(HttpRequest.BodyPublishers.ofString(amendedEpic1Json))
                    .build();
            httpClient.send(httpRequest2, HttpResponse.BodyHandlers.ofString());
            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078/");
            Epic[] epic2 = gson.fromJson(kvTaskClient.load("epics"), Epic[].class);
            assertEquals(amendedEpic1.getName(), epic2[0].getName());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    @Test
    void test6_PostTestAddingEpicAndSubtaskViaHttpServerThenAmendSubtaskAndGettingViaKVServer() {
        try {
            Epic epic1 = new Epic("Epic 1", "Description 1");
            String epic1Json = gson.toJson(epic1);
            Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
            String subtask1Json = gson.toJson(subtask1);
            Subtask subtask2 = new Subtask("Subtask 111", "Description 1", 1);
            String subtask2Json = gson.toJson(subtask2);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/"))
                    .POST(HttpRequest.BodyPublishers.ofString(epic1Json))
                    .build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                    .POST(HttpRequest.BodyPublishers.ofString(subtask1Json))
                    .build();
            httpClient.send(httpRequest2, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/?=2"))
                    .POST(HttpRequest.BodyPublishers.ofString(subtask2Json))
                    .build();
            httpClient.send(httpRequest3, HttpResponse.BodyHandlers.ofString());
            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078/");
            Subtask[] subtasks = gson.fromJson(kvTaskClient.load("subtasks"), Subtask[].class);
            assertEquals(subtask2.getName(), subtasks[0].getName());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    @Test
    void test7_GetTestsTaskSubtaskEpicAndPrioritizedTask() {
        try {
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
            String task1Json = gson.toJson(task1);
            String task2Json = gson.toJson(task2);
            String epic3Json = gson.toJson(epic);
            String subtask4Json = gson.toJson(subtask1);
            String subtask5Json = gson.toJson(subtask2);
            String task6Json = gson.toJson(task3);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .POST(HttpRequest.BodyPublishers.ofString(task1Json))
                    .build();
            httpClient.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .POST(HttpRequest.BodyPublishers.ofString(task2Json))
                    .build();
            httpClient.send(httpRequest2, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/"))
                    .POST(HttpRequest.BodyPublishers.ofString(epic3Json))
                    .build();
            httpClient.send(httpRequest3, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest4 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                    .POST(HttpRequest.BodyPublishers.ofString(subtask4Json))
                    .build();
            httpClient.send(httpRequest4, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest5 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                    .POST(HttpRequest.BodyPublishers.ofString(subtask5Json))
                    .build();
            httpClient.send(httpRequest5, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest6 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .POST(HttpRequest.BodyPublishers.ofString(task6Json))
                    .build();
            httpClient.send(httpRequest6, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest01 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .GET()
                    .build();
            HttpResponse<String> response1 = httpClient.send(httpRequest01, HttpResponse.BodyHandlers.ofString());
            assertEquals(3, gson.fromJson(response1.body(), Task[].class).length);
            HttpRequest httpRequest02 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/?id=1"))
                    .GET()
                    .build();
            HttpResponse<String> response2 = httpClient.send(httpRequest02, HttpResponse.BodyHandlers.ofString());
            assertEquals(task1.getName(), gson.fromJson(response2.body(), Task.class).getName());
            HttpRequest httpRequest03 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/"))
                    .GET()
                    .build();
            HttpResponse<String> response3 = httpClient.send(httpRequest03, HttpResponse.BodyHandlers.ofString());
            assertEquals(1, gson.fromJson(response3.body(), Epic[].class).length);
            HttpRequest httpRequest04 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                    .GET()
                    .build();
            HttpResponse<String> response4 = httpClient.send(httpRequest04, HttpResponse.BodyHandlers.ofString());
            assertEquals(2, gson.fromJson(response4.body(), Subtask[].class).length);
            HttpRequest httpRequest05 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/?id=5"))
                    .GET()
                    .build();
            HttpResponse<String> response5 = httpClient.send(httpRequest05, HttpResponse.BodyHandlers.ofString());
            assertEquals(subtask2.getName(), gson.fromJson(response5.body(), Subtask.class).getName());
            HttpRequest httpRequest06 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/"))
                    .GET()
                    .build();
            HttpResponse<String> response6 = httpClient.send(httpRequest06, HttpResponse.BodyHandlers.ofString());
            assertEquals(subtask2.getName(), gson.fromJson(response6.body(), Task[].class)[0].getName());
            assertEquals(task1.getName(), gson.fromJson(response6.body(), Task[].class)[3].getName());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    @Test
    void test8_DeleteTestsTaskSubtaskEpic() {
        try {
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
            String task1Json = gson.toJson(task1);
            String task2Json = gson.toJson(task2);
            String epic3Json = gson.toJson(epic);
            String subtask4Json = gson.toJson(subtask1);
            String subtask5Json = gson.toJson(subtask2);
            String task6Json = gson.toJson(task3);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .POST(HttpRequest.BodyPublishers.ofString(task1Json))
                    .build();
            httpClient.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .POST(HttpRequest.BodyPublishers.ofString(task2Json))
                    .build();
            httpClient.send(httpRequest2, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/"))
                    .POST(HttpRequest.BodyPublishers.ofString(epic3Json))
                    .build();
            httpClient.send(httpRequest3, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest4 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                    .POST(HttpRequest.BodyPublishers.ofString(subtask4Json))
                    .build();
            httpClient.send(httpRequest4, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest5 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                    .POST(HttpRequest.BodyPublishers.ofString(subtask5Json))
                    .build();
            httpClient.send(httpRequest5, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest6 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .POST(HttpRequest.BodyPublishers.ofString(task6Json))
                    .build();
            httpClient.send(httpRequest6, HttpResponse.BodyHandlers.ofString());
            HttpRequest httpRequest01 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/?id=1"))
                    .DELETE()
                    .build();
            httpClient.send(httpRequest01, HttpResponse.BodyHandlers.ofString());
            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078/");
            assertEquals(2, gson.fromJson(kvTaskClient.load("tasks"), Task[].class).length);
            HttpRequest httpRequest02 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/task/"))
                    .DELETE()
                    .build();
            httpClient.send(httpRequest02, HttpResponse.BodyHandlers.ofString());
            assertEquals(0, gson.fromJson(kvTaskClient.load("tasks"), Task[].class).length);
            HttpRequest httpRequest03 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                    .DELETE()
                    .build();
            httpClient.send(httpRequest03, HttpResponse.BodyHandlers.ofString());
            assertEquals(0, gson.fromJson(kvTaskClient.load("subtasks"), Task[].class).length);
            HttpRequest httpRequest04 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/epic/"))
                    .DELETE()
                    .build();
            httpClient.send(httpRequest04, HttpResponse.BodyHandlers.ofString());
            assertEquals(0, gson.fromJson(kvTaskClient.load("epics"), Task[].class).length);
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }
}