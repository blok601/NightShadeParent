package me.blok601.nightshadeuhc.entity.object;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/10/2018.
 */
public abstract class Achievement {

    private String name;
    private String description;
    private Reward reward;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public void announce(Player player){
        ChatUtils.sendAll("&a" + player.getName() + " has achieved &3" + getName());
    }

    public void giveReward(Player player){
        reward.announce(player);
        reward.give(player);
    }
}
