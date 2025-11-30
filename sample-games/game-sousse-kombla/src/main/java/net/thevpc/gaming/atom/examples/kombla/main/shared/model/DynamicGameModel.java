package net.thevpc.gaming.atom.examples.kombla.main.shared.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vpc on 10/7/16.
 */
public class DynamicGameModel implements Serializable {
    private static final long serialVersionUID = 1L;
    long frame;
    List<SerializableSprite> sprites;
    List<SerializablePlayer> players;

    public DynamicGameModel() {

    }

    public DynamicGameModel(long frame, List<SerializableSprite> sprites, List<SerializablePlayer> players) {
        this.frame = frame;
        this.sprites = sprites;
        this.players = players;
    }

    public DynamicGameModel setFrame(long frame) {
        this.frame = frame;
        return this;
    }

    public DynamicGameModel setSprites(List<SerializableSprite> sprites) {
        this.sprites = sprites;
        return this;
    }

    public DynamicGameModel setPlayers(List<SerializablePlayer> players) {
        this.players = players;
        return this;
    }

    public long getFrame() {
        return frame;
    }

    public List<SerializableSprite> getSprites() {
        return sprites;
    }

    public List<SerializablePlayer> getPlayers() {
        return players;
    }
}
