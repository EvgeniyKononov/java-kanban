package Task;

import java.lang.annotation.Native;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class Epic extends Task {
    private ArrayList<Integer> subtaskID;
    private LocalDateTime endTime;

    public Epic() {
        this.subtaskID = new ArrayList<>();
        this.setType(Type.EPIC);
    }

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskID = new ArrayList<>();
        this.setType(Type.EPIC);
    }

    public Epic(String name, String description, Integer id) {
        super(name, description, id, Status.NEW);
        this.setType(Type.EPIC);
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
        if(!Objects.equals(split[5], "null")) {
            epic.setDuration(parseInt(split[5]));
        }
        if(!Objects.equals(split[6], "null")) {
            epic.setStartTime(LocalDateTime.parse(split[6]));
        }
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
        return String.format("%s,%s,%s,%s,%s,%s,%s,", this.getId(), this.getType(), this.getName(), this.getStatus(),
                this.getDescription(), this.getDuration(), this.getStartTime());
    }
}
