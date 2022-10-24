package Task;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class Subtask extends Task {
    private Integer epicId;
    public Subtask() {
        this.setType(Type.SUBTASK);
    }

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
        this.setType(Type.SUBTASK);
    }

    public Subtask(String name, String description, Integer id, Status status) {
        super(name, description, id, status);
        this.setType(Type.SUBTASK);
    }

    public Subtask(String name, String description, Integer epicId, Integer duration, LocalDateTime startTime) {
        super(name, description, duration, startTime);
        this.epicId = epicId;
        this.setStatus(Status.NEW);
        this.setType(Type.SUBTASK);
    }

    public Subtask fromString(String value) {
        Subtask subtask = new Subtask();
        String[] split = value.split(",");
        subtask.setId(parseInt(split[0]));
        subtask.setName(split[2]);
        subtask.setStatus(Status.valueOf(split[3]));
        subtask.setDescription(split[4]);
        subtask.setEpicId(parseInt(split[5]));
        if(!Objects.equals(split[6], "null")) {
            subtask.setDuration(parseInt(split[6]));
        }
        if(!Objects.equals(split[7], "null")) {
            subtask.setStartTime(LocalDateTime.parse(split[7]));
        }
        return subtask;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,", this.getId(), this.getType(), this.getName(),
                this.getStatus(), this.getDescription(), this.getEpicId(),  this.getDuration(), this.getStartTime());
    }
}