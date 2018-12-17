package me.blok601.nightshadeuhc.entity.object;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.LoggerManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Blok on 9/23/2017.
 */
public class CombatLogger {

    private Player loggerP;
    private String loggerName;

    private Zombie zombie;

    private ItemStack[] armor;
    private ItemStack[] inventory;
    private UUID uuid;
    private Location location;

    private float exp;
    private double health;
    private int food;

    private int taskID;

    public CombatLogger(Player logger) {

        this.loggerName = logger.getName() ;
        this.loggerP = logger;
        this.armor = logger.getInventory().getArmorContents();
        this.inventory = logger.getInventory().getContents();
        this.health = logger.getHealth();
        this.food = logger.getFoodLevel();
        this.exp = logger.getExp();
        this.uuid = logger.getUniqueId();
        this.location = logger.getLocation();

        zombie = (Zombie) logger.getLocation().getWorld().spawnEntity(logger.getLocation(), EntityType.ZOMBIE);
        zombie.setMetadata("logger", new FixedMetadataValue(UHC.get(), "logger"));

        zombie.setCustomNameVisible(true);
        zombie.setCustomName(logger.getName());

        zombie.setMaxHealth(logger.getMaxHealth());
        zombie.setHealth(logger.getHealth());
        zombie.getEquipment().setArmorContents(getArmor());
        this.freeze();


        taskID = new BukkitRunnable(){
            @Override
            public void run() {

                // TODO: remove player from game and change broadcast
                Bukkit.broadcastMessage(ChatUtils.format("&5" + loggerName + " (Logger) &9 was killed."));
                if(loggerP != null || loggerP.isOnline()) {
                    UHC.players.remove(loggerP.getUniqueId());
                }else{
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(loggerName);
                    if(offlinePlayer.hasPlayedBefore()) {
                        UHC.players.remove(offlinePlayer.getUniqueId());
                    }
                }

                for (ItemStack itemStack : getInventory()) {
                    if(itemStack == null || itemStack.getType() == Material.AIR) continue;
                    getZombie().getWorld().dropItem(getZombie().getLocation(), itemStack);
                }

                for (ItemStack itemStack : getArmor()) {
                    if(itemStack == null || itemStack.getType() == Material.AIR) continue;
                    getZombie().getWorld().dropItem(getZombie().getLocation(), itemStack);
                }

                LoggerManager.getInstance().getDeadLoggers().add(getUuid());
                zombie.remove();

            }
        }.runTaskLater(UHC.get(), Util.TICKS*60*5).getTaskId();

        LoggerManager.getInstance().createLogger(this);

    }

    public void remove() {

        Bukkit.getServer().getScheduler().cancelTask(getTaskID());

        zombie.remove();

        LoggerManager.getInstance().removeLogger(this);

    }

    public String getLoggerName() {
        return loggerName;
    }

    public Player getLogger() {
        return loggerP;
    }

    public Zombie getZombie() {
        return zombie;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public double getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public float getExp() {
        return exp;
    }

    public int getTaskID() {
        return this.taskID;
    }

    private void freeze(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(getZombie() != null && !getZombie().isDead()){
                    getZombie().teleport(location);
                }
            }
        }.runTaskTimer(UHC.get(), 0, 25);
    }

    public UUID getUuid() {
        return uuid;
    }
}