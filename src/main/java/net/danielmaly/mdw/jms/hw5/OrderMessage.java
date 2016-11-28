package net.danielmaly.mdw.jms.hw5;

import java.io.Serializable;

public class OrderMessage implements Serializable {
    enum Type {
        BOOKING,
        TRIP
    }

    private Type type;
    private String message;
    private int id;

    public OrderMessage(Type type, String message, int id) {
        this.type = type;
        this.message = message;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{Order " + id + ", type: " + type + ", message: " + message + "}";
    }
}
