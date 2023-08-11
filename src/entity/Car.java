package entity;

import java.util.Objects;

public class Car {
    private Long id;
    private String name;
    private String type;

    private int countExamples;

    public Car() {
    }

    public Car(Long id, String name, String type, int countExamples) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.countExamples = countExamples;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCountExamples() {
        return countExamples;
    }

    public void setCountExamples(int countExamples) {
        this.countExamples = countExamples;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return getId() == car.getId() && getCountExamples() == car.getCountExamples() && Objects.equals(getName(), car.getName()) && Objects.equals(getType(), car.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getCountExamples());
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", countExamples=" + countExamples +
                '}';
    }
}
