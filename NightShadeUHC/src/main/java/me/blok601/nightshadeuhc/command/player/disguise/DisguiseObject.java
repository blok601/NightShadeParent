package me.blok601.nightshadeuhc.command.player.disguise;

import com.nightshadepvp.core.Rank;

/**
 * Created by Blok on 12/9/2017.
 */
public class DisguiseObject {

    private String name;
    private boolean op;
    private Rank preRank; //For rank and prefix storage

    private String disguisedName;

    public DisguiseObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOp() {
        return op;
    }

    public void setOp(boolean op) {
        this.op = op;
    }

    public Rank getPreRank() {
        return preRank;
    }

    public void setPreRank(Rank preRank) {
        this.preRank = preRank;
    }

    public String getDisguisedName() {
        return disguisedName;
    }

    public void setDisguisedName(String disguisedName) {
        this.disguisedName = disguisedName;
    }
}
