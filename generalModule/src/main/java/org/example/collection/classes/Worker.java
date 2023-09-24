package org.example.collection.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Worker implements Serializable, Comparable<Worker>  {
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyyг HHч.mmмин.sс");

    private int userId;
    private int id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long salary; //Значение поля должно быть больше 0
    private Position position; //Поле не может быть null
    private Status status; //Поле не может быть null
    private Organization organization; //Поле не может быть null

    public Worker() {
    }

    public Worker(int id, String name, Coordinates coordinates, long salary, Position position, Status status, Organization organization) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.salary = salary;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }

    public Worker(String id, String name, Coordinates coordinates, String salary, Position position, Status status, Organization organization) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.salary = Long.parseLong(salary);
        this.position = position;
        this.status = status;
        this.organization = organization;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Значение поля name не может быть null");
        }
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Значение поля creationDate не может быть null");
        }
        this.creationDate = creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = LocalDateTime.parse(creationDate);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Значение поля status не может быть null");
        } else {
            this.status = status;
        }
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        if (organization == null) {
            throw new IllegalArgumentException("Значение поля organization не может быть null");
        } else {
            this.organization = organization;
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Значение поля coordinates не может быть null");
        }
        this.coordinates = coordinates;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Значение поля salary не может быть меньше 0");
        } else {
            this.salary = salary;
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Значение поля position не может быть null");
        } else {
            this.position = position;
        }
    }

    public String time() {
        return creationDate.format(dateTimeFormatter);
    }

    public void setTime(String time) {
        this.creationDate = LocalDateTime.parse(time, dateTimeFormatter);
    }

    @Override
    public String toString() {
        return "Id:" + id +
                " | Имя: " + name + " | Координаты: " + coordinates +
                " | Время создания: " + time() + " | ЗП: " + salary +
                " | Позиция: " + position.toString() + " | Статус: " + status.toString() +
                " | Организация: " + organization;
    }

    public int compareTo(Worker other) {
        return this.name.compareTo(other.name);
    }
}