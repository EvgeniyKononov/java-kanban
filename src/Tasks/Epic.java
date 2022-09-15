package Tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskID;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskID = new ArrayList<>();
    }

    public Epic() {
    }

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", subtask=" + getSubtaskID() +
                '}';
    }

    public ArrayList<Integer> getSubtaskID() {
        return subtaskID;
    }

    public void setSubtaskID(ArrayList<Integer> subtaskID) {
        this.subtaskID = subtaskID;
    }
}
