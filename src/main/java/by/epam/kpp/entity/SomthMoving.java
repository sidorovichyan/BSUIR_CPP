package by.epam.kpp.entity;

import java.util.Objects;

public class SomthMoving {

    private Double path;

    private Double speed;


    public SomthMoving() {
        speed = (double) 0;
        path = (double) 0;
    }

    public SomthMoving(double path, double speed) {
        this.speed = speed;
        this.path = path;
    }

    public SomthMoving(Double path, Double speed) {
        this.speed = speed;
        this.path = path;

    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPath() {
        return path;
    }

    public void setPath(double path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "SomthMoving{" +
                "path=" + path +
                ", speed=" + speed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SomthMoving)) return false;
        SomthMoving that = (SomthMoving) o;
        return Objects.equals(getPath(), that.getPath()) && Objects.equals(getSpeed(), that.getSpeed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getSpeed());
    }
}
