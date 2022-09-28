package Tasks;

import java.util.Objects;

import static java.lang.Integer.parseInt;

public class Task {
    private Integer id;
    private String name;
    private String description;
    private Status status;
    private final Type type = Type.TASK;

    public Task() {
    }

    public Task(String name, String description, Integer id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.id = 0;
    }

    public Task(String name, String description, Integer id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return name.equals(task.name)
                && description.equals(task.description)
                && id.equals(task.id)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,", this.id, this.type, this.name, this.status, this.description);
    }

    public Task fromString(String value) {
        Task task = new Task();
        String[] split = value.split(",");
        task.id = parseInt(split[0]);
        task.name = split[2];
        task.status = Status.valueOf(split[3]);
        task.description = split[4];
        return task;
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}