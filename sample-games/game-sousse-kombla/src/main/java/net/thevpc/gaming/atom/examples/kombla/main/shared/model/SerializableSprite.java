package net.thevpc.gaming.atom.examples.kombla.main.shared.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Serializable DTO for transferring Sprite data via RMI
 * Contains only primitive/serializable fields
 */
public class SerializableSprite implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String kind;
    private String name;
    private double x;
    private double y;
    private double direction;
    private int playerId;
    private int movementStyle;
    private int life;
    private Map<String, String> properties;

    public SerializableSprite() {
        this.properties = new HashMap<>();
    }

    public SerializableSprite(int id, String kind, String name, double x, double y,
            double direction, int playerId, int movementStyle, int life) {
        this.id = id;
        this.kind = kind;
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.playerId = playerId;
        this.movementStyle = movementStyle;
        this.life = life;
        this.properties = new HashMap<>();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getMovementStyle() {
        return movementStyle;
    }

    public void setMovementStyle(int movementStyle) {
        this.movementStyle = movementStyle;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public String getProperty(String key) {
        return this.properties.get(key);
    }
}
