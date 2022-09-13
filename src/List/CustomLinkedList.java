package List;

import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomLinkedList {

    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node last;
    private Node first;

    private void linkLast(Task task) {
        Node l = last;
        Node newNode = new Node(last, task, null);
        last = newNode;
        if (l == null) first = newNode;
        else l.next = newNode;
    }

    private void removeNode(Node node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
            node.prev = null;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
            node.next = null;
        }
        node = null;
    }

    public void add(Task task) {
        remove(task.getId());
        linkLast(task);
        nodeMap.put(task.getId(), last);
    }

    public void remove(int taskId) {
        Node node = nodeMap.get(taskId);
        if (node == null) {
            return;
        } else {
            for (Node x = first; x != null; x = x.next) {
                if (node.equals(x)) {
                    removeNode(x);
                }
            }
        }
        nodeMap.remove(taskId);
    }

    public ArrayList<Task> getHistory() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node node = first;
        if (node == null) {
            return null;
        }
        do {
            tasks.add(node.task);
            node = node.next;
        } while (node != null);
        return tasks;
    }
}
