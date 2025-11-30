package net.thevpc.gaming.atom.examples.kombla.main.shared.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Serializable DTO for transferring Player data via RMI
 */
public class SerializablePlayer implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private Map<String, String> properties;

    public SerializablePlayer() {
        this.properties = new HashMap<>();
    }

    public SerializablePlayer(int id, String name) {
        this.id = id;
        this.name = name;
        this.properties = new HashMap<>();
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
        this.name = name;
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
