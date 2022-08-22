package Tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private Integer subtaskAmount;
    private Integer doneSubtaskAmount;
    private Integer newSubtasksAmount;
    private ArrayList<Subtask> subtask;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskAmount = 0;
        this.newSubtasksAmount = 0;
        this.doneSubtaskAmount = 0;
        this.subtask = new ArrayList<>();
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
                ", subtask=" + getSubtask() +
                '}';
    }

    public Integer getSubtaskAmount() {
        return subtaskAmount;
    }

    public void setSubtaskAmount(Integer subtaskAmount) {
        this.subtaskAmount = subtaskAmount;
    }

    public Integer getDoneSubtaskAmount() {
        return doneSubtaskAmount;
    }

    public void setDoneSubtaskAmount(Integer doneSubtaskAmount) {
        this.doneSubtaskAmount = doneSubtaskAmount;
    }

    public Integer getNewSubtasksAmount() {
        return newSubtasksAmount;
    }

    public void setNewSubtasksAmount(Integer newSubtasksAmount) {
        this.newSubtasksAmount = newSubtasksAmount;
    }

    public ArrayList<Subtask> getSubtask() {
        return subtask;
    }

    public void setSubtask(ArrayList<Subtask> subtask) {
        this.subtask = subtask;
    }

}





