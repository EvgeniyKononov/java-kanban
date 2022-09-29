package List;

import Task.Task;

public class Node {
    private Task task;
    private Node prev;
    private Node next;

    public Node(Node prev, Task task, Node next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "task=" + task +
                ", prev=" + (prev != null ? prev.task : null) +
                ", next=" + (next != null ? next.task : null) +
                '}';
    }

    public Task getTask() {
        return task;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
