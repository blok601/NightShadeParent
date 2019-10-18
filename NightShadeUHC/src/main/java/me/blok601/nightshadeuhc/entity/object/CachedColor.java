package me.blok601.nightshadeuhc.entity.object;

/**
 * Created by Blok on 8/19/2018.
 */
public class CachedColor {

    private String id;
    private String player;
    private String color;

    public CachedColor(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
