package Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Epic extends Task {
    private ArrayList<Integer> subtaskID;
    private final Type type = Type.EPIC;

    private LocalDateTime endTime;

    public Epic() {
        this.subtaskID = new ArrayList<>();
    }

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskID = new ArrayList<>();
    }

    public Epic(String name, String description, Integer id) {
        super(name, description, id, Status.NEW);
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Epic fromString(String value) {
        Epic epic = new Epic();
        String[] split = value.split(",");
        epic.setId(parseInt(split[0]));
        epic.setName(split[2]);
        epic.setStatus(Status.valueOf(split[3]));
        epic.setDescription(split[4]);
        return epic;
    }

    public ArrayList<Integer> getSubtaskID() {
        return subtaskID;
    }

    public void setSubtaskID(ArrayList<Integer> subtaskID) {
        this.subtaskID = subtaskID;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,", this.getId(), this.type, this.getName(), this.getStatus(),
                this.getDescription());
    }
}
