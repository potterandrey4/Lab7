package org.example.collection.classes;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private double x; //Поле не может быть null
    private float y; //Поле не может быть null

    public Coordinates() {
    }

    public Coordinates(double x, float y) {
        if (Double.isNaN(x)) {
            throw new IllegalArgumentException("Значение x не может быть NaN");
        }
        if (Float.isNaN(y)) {
            throw new IllegalArgumentException("Значение y не может быть NaN");
        }

        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x + "; y=" + y;
    }
}