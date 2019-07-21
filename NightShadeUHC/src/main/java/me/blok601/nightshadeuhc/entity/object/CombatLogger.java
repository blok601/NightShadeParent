package me.blok601.nightshadeuhc.entity.object;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.LoggerManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.MathUtils;
import me.blok601.nightshadeuhc.util.TimeUtils;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

    private ArmorStand armorStand;
    private Hologram nameHologram;

    private ItemStack[] armor;
    private ItemStack[] inventory;
    private UUID uuid;
    private Location location;

    private float exp;
    private double health;
    private int food;

    private int taskID;
    private int logOutTimer;

    public CombatLogger(Player logger) {
        this.loggerName = ChatUtils.format("&b&l" + logger.getName() + "'s Logger");
        this.loggerP = logger;
        this.armor = logger.getInventory().getArmorContents();
        this.inventory = logger.getInventory().getContents();
        this.health = logger.getHealth();
        this.food = logger.getFoodLevel();
        this.exp = logger.getExp();
        this.uuid = logger.getUniqueId();
        this.location = logger.getLocation();
        this.logOutTimer = 300;

        //zombie = (Zombie) logger.getLocation().getWorld().spawnEntity(logger.getLocation(), EntityType.ZOMBIE);
        armorStand = (ArmorStand) logger.getLocation().getWorld().spawnEntity(logger.getLocation(), EntityType.ARMOR_STAND);
        //zombie.setMetadata("logger", new FixedMetadataValue(UHC.get(), "logger"));
        armorStand.setMetadata("logger", new FixedMetadataValue(UHC.get(), "logger"));

        //zombie.setCustomNameVisible(true);
        //zombie.setCustomName(logger.getName());
//        armorStand.setCustomNameVisible(true);
//        armorStand.setCustomName(logger.getName());

        armorStand.setMaxHealth(logger.getMaxHealth());
        armorStand.setHealth(logger.getHealth());
        armorStand.getEquipment().setArmorContents(getArmor());
        //this.freeze();

        nameHologram = HologramAPI.createHologram(this.location.add(0, 2.5, 0), ChatUtils.format("&b&l" + loggerName + "'s Logger"));
        nameHologram.spawn();
        nameHologram.addLineBelow(ChatUtils.format("&a&l5:00"));

        taskID = new BukkitRunnable(){

            @Override
            public void run() {

                if (logOutTimer < -10) { //Interrupted
                    this.cancel();
                    return;
                }

                if (logOutTimer == 0) {
                    this.cancel();
                    logOutTimer = -10;
                    ChatUtils.sendAll("&4" + loggerName + "  &4 was logged out for too long.");
                    nameHologram.getLineBelow().despawn();
                    nameHologram.despawn();
                    if (loggerP != null || loggerP.isOnline()) {
                        UHC.loggedOutPlayers.remove(loggerP.getUniqueId());
                    } else {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(loggerName);
                        if (offlinePlayer == null) return;
                        if (offlinePlayer.hasPlayedBefore()) {
                            UHC.loggedOutPlayers.remove(offlinePlayer.getUniqueId());
                        }
                    }

                    for (ItemStack itemStack : getInventory()) {
                        if (itemStack == null || itemStack.getType() == Material.AIR) continue;
                        getArmorStand().getWorld().dropItem(getArmorStand().getLocation(), itemStack);
                    }

                    for (ItemStack itemStack : getArmor()) {
                        if (itemStack == null || itemStack.getType() == Material.AIR) continue;
                        getArmorStand().getWorld().dropItem(getArmorStand().getLocation(), itemStack);
                    }

                    LoggerManager.getInstance().getDeadLoggers().add(getUuid());
                    armorStand.remove();
                    return;
                }

                //Every second
                //Between 300-240 is 5-> 4 mins - Green
                // 240 -> 180 4 mins to 3 mins - Gold
                // 180 - > 120 3 mins to 2 mins - Yellow
                // 120 -> 60 2 mins to 1 min - Red -> Dark Red

                if (MathUtils.isBetween(59, 0, logOutTimer)) {
                    nameHologram.getLineBelow().setText(ChatUtils.format("&4&l" + TimeUtils.formatSecondsToMinutesSeconds(logOutTimer)));
                } else if (MathUtils.isBetween(119, 60, logOutTimer)) {
                    nameHologram.getLineBelow().setText(ChatUtils.format("&c&l" + TimeUtils.formatSecondsToMinutesSeconds(logOutTimer)));
                } else if (MathUtils.isBetween(179, 120, logOutTimer)) {
                    nameHologram.getLineBelow().setText(ChatUtils.format("&e&l" + TimeUtils.formatSecondsToMinutesSeconds(logOutTimer)));
                } else if (MathUtils.isBetween(239, 180, logOutTimer)) {
                    nameHologram.getLineBelow().setText(ChatUtils.format("&6&l" + TimeUtils.formatSecondsToMinutesSeconds(logOutTimer)));
                } else if (MathUtils.isBetween(300, 240, logOutTimer)) {
                    nameHologram.getLineBelow().setText(ChatUtils.format("&a&l" + TimeUtils.formatSecondsToMinutesSeconds(logOutTimer)));
                }
                //Name updated
                logOutTimer--;

            }
        }.runTaskTimer(UHC.get(), 0, Util.TICKS).getTaskId();

        LoggerManager.getInstance().createLogger(this);

    }

    public void remove() {

        this.logOutTimer = -11;
        Bukkit.getServer().getScheduler().cancelTask(getTaskID());
        armorStand.remove();
        nameHologram.removeLineBelow();
        nameHologram.despawn();
        LoggerManager.getInstance().removeLogger(this);

    }

    public String getLoggerName() {
        return loggerName;
    }

    public Player getLogger() {
        return loggerP;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
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
                if (getArmorStand() != null && !getArmorStand().isDead()) {
                    getArmorStand().teleport(location);
                }
            }
        }.runTaskTimer(UHC.get(), 0, 25);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Hologram getNameHologram() {
        return nameHologram;
    }


}
