package List;

import Tasks.Task;

public class Node {
    Task task;
    Node prev;
    Node next;

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
}
