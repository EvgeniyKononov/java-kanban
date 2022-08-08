import java.util.ArrayList;

public class Epic extends Task {
    Integer subtaskAmount;
    Integer doneSubtaskAmount;
    Integer newSubtasksAmount;
    ArrayList<Subtask> subtask;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskAmount = 0;
        this.newSubtasksAmount = 0;
        this.doneSubtaskAmount = 0;
        this.subtask = new ArrayList<>();
    }

    public Epic(String name, String description, Integer id, Status status) {
        super(name, description, id, status);
    }

    public Epic() {
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", subtask=" + subtask +
                '}';
    }
}





