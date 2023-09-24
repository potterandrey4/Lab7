package org.example.collection.classes;

import java.io.Serializable;

public class Organization implements Serializable {
    private String fullName; //Значение этого поля должно быть уникальным, Поле может быть null
    private float annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null

    public Organization() {
    }

    public Organization(String fullName, float annualTurnover, OrganizationType type) {
        if (annualTurnover <= 0) {
            throw new IllegalArgumentException("Значение поля annualTurnover не может быть меньше 0");
        }

        if (type == null) {
            throw new IllegalArgumentException("Значение поля type не может быть null");
        }
        this.fullName = fullName;
        this.annualTurnover = annualTurnover;
        this.type = type;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAnnualTurnover(float annualTurnover) {
        if (annualTurnover <= 0) {
            throw new IllegalArgumentException("Значение поля annualTurnover не может быть меньше 0");
        } else {
            this.annualTurnover = annualTurnover;
        }
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public float getAnnualTurnover() {
        return annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "полное название: " + fullName +
                ", годовой оборот: " + annualTurnover +
                ", тип: " + type;
    }
}