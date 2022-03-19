package by.epam.kpp.entity;

import java.util.Objects;

public class SomthMoving {
    private Double path;

    private Double speed;

    private Double averangeSpeed;

    public SomthMoving() {
        speed = (double) 0;
        path = (double) 0;
        averangeSpeed = (double) 0;
    }

    public SomthMoving(Double path, Double speed, Double averangeSpeed) {
        this.path = path;
        this.speed = speed;
        this.averangeSpeed = averangeSpeed;
    }

    public SomthMoving(double path, double speed) {
        this.speed = speed;
        this.path = path;
        averangeSpeed = (double) 0;
    }

    public SomthMoving(Double path, Double speed) {
        this.speed = speed;
        this.path = path;
        averangeSpeed = (double) 0;
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

    public double getAverangeSpeed() {
        return averangeSpeed;
    }

    public void setAverangeSpeed(double rezult) {
        this.averangeSpeed = rezult;
    }

    @Override
    public String toString() {
        return "SomthMoving{" +
                "path=" + path +
                ", speed=" + speed +
                ", averangeSpeed=" + averangeSpeed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SomthMoving)) return false;
        SomthMoving that = (SomthMoving) o;
        return Objects.equals(getPath(), that.getPath()) && Objects.equals(getSpeed(), that.getSpeed()) && Objects.equals(getAverangeSpeed(), that.getAverangeSpeed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getSpeed(), getAverangeSpeed());
    }

}
