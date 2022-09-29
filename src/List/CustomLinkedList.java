package List;

import Task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomLinkedList {

    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node last;
    private Node first;

    private void linkLast(Task task) {
        Node oldLast = last;
        Node newNode = new Node(last, task, null);
        last = newNode;
        if (oldLast == null) first = newNode;
        else oldLast.setNext(newNode);
    }

    private void removeNode(Node node) {
        if (node.getPrev() == null) {
            first = node.getNext();
        } else {
            node.getPrev().setNext(node.getNext());
        }
        if (node.getNext() == null) {
            last = node.getPrev();
        } else {
            node.getNext().setPrev(node.getPrev());
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
            for (Node searchingNode = first; searchingNode != null; searchingNode = searchingNode.getNext()) {
                if (node.equals(searchingNode)) {
                    removeNode(searchingNode);
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
            tasks.add(node.getTask());
            node = node.getNext();
        } while (node != null);
        return tasks;
    }

    @Override
    public String toString() {
        return "CustomLinkedList{" +
                "nodeMap=" + nodeMap +
                '}';
    }
}