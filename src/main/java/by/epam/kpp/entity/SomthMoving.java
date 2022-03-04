package by.epam.kpp.entity;

public class SomthMoving {

    private Double speed;

    private Double path;

    private Double averangeSpeed;

    public SomthMoving() {
        speed = (double) 0;
        path = (double) 0;
        averangeSpeed = (double) 0;
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
}
