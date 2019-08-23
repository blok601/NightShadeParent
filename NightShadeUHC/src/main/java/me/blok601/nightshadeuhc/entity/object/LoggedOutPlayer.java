package me.blok601.nightshadeuhc.entity.object;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.LoggerManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Blok on 8/23/2019.
 */
public class LoggedOutPlayer {

    private UHC uhc;
    private UUID uuid;
    private String name;
    private int taskID;
    private int timer; //in seconds
    private Location location;
    private float xp;
    private ItemStack[] armor;
    private ItemStack[] items;

    public LoggedOutPlayer(UUID uuid, String name, UHC uhc) {
        this.uuid = uuid;
        this.name = name;
        this.taskID = -1;
        this.timer = 300;
        this.uhc = uhc;
        this.location = null;
        this.xp = 0;
        this.armor = null;
        this.items = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getTimer() {
        return timer;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getXp() {
        return xp;
    }

    public void setXp(float xp) {
        this.xp = xp;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public void start() {
        this.taskID = new BukkitRunnable() {
            @Override
            public void run() {
                if (timer > 0) {
                    timer--;
                    return;
                }

                //Timer got to 0
                if (timer == 0) {
                    timer = -10;
                    kill();
                    this.cancel();
                    return;
                }
            }
        }.runTaskTimer(uhc, 0, 20).getTaskId();
    }

    public void kill() {
        for (ItemStack itemStack : this.getItems()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            this.location.getWorld().dropItem(this.location, itemStack);
        }

        for (ItemStack itemStack : this.getArmor()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            this.location.getWorld().dropItem(this.location, itemStack);
        }

        this.getLocation().getWorld().strikeLightningEffect(this.getLocation().add(0, 10, 0));

        ItemStack skull1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemStack newSkull1 = new ItemBuilder(skull1).skullOwner(this.name).name(this.name).make();
        this.getLocation().getWorld().dropItemNaturally(this.getLocation(), newSkull1);

        ((ExperienceOrb) this.location.getWorld().spawnEntity(this.location,
                EntityType.EXPERIENCE_ORB)).setExperience((int) this.xp); // Might be buggy, test

        UHC.loggedOutPlayers.remove(this.uuid);
        PlayerRespawn object = new PlayerRespawn(this.getArmor(), this.getItems(), this.getLocation());
        GameManager.get().getInvs().put(this.getUuid(), object);
        ChatUtils.sendAll("&7" + this.name + " &7(Logger) &4was logged out for too long.");
        LoggerManager.getInstance().getDeadLoggers().add(this);
        remove(true);
    }

    public void remove(boolean dead) {
        LoggerManager.getInstance().getLoggedOutPlayers().remove(this);
        if (dead) {
            LoggerManager.getInstance().getDeadLoggers().add(this);
        }
    }
}
