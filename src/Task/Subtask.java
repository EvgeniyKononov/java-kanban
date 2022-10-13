package Task;

import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;

public class Subtask extends Task {
    private Integer epicId;

    private final Type type = Type.SUBTASK;

    public Subtask() {
    }

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Integer id, Status status) {
        super(name, description, id, status);
    }

    public Subtask(String name, String description, Integer epicId, Integer duration, LocalDateTime startTime) {
        super(name, description, duration, startTime);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,", this.getId(), this.type, this.getName(),
                this.getStatus(), this.getDescription(), this.getEpicId());
    }

    public Subtask fromString(String value) {
        Subtask subtask = new Subtask();
        String[] split = value.split(",");
        subtask.setId(parseInt(split[0]));
        subtask.setName(split[2]);
        subtask.setStatus(Status.valueOf(split[3]));
        subtask.setDescription(split[4]);
        subtask.setEpicId(parseInt(split[5]));
        return subtask;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }
}