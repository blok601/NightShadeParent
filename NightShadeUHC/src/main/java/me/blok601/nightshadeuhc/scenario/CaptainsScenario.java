package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CaptainsScenario extends Scenario{

    private UUID turnToPick;
    private int positionIndex;
    private boolean hasPicked;
    private boolean goingBackwards;

    public CaptainsScenario() {
        super("Captains", "Teams are picked in a snake draft with set captains", new ItemBuilder(Material.PAINTING).name("Captains").make());
        this.turnToPick = UUID.randomUUID();
        this.positionIndex = -1;
        this.goingBackwards = false;
    }

    public UUID getTurnToPick() {
        return turnToPick;
    }

    public void setTurnToPick(UUID turnToPick) {
        this.turnToPick = turnToPick;
    }

    public boolean isTurnToPick(UUID uuid){
        return this.turnToPick.equals(uuid);
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public boolean hasPicked() {
        return hasPicked;
    }

    public void setHasPicked(boolean hasPicked) {
        this.hasPicked = hasPicked;
    }

    public boolean isGoingBackwards() {
        return goingBackwards;
    }

    public void setGoingBackwards(boolean goingBackwards) {
        this.goingBackwards = goingBackwards;
    }
}
