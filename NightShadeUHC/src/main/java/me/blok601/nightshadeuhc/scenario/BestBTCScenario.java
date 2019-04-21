package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.event.PvPEnableEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Blok on 4/21/2019.
 */
public class BestBTCScenario extends Scenario {

    private UHC uhc;

    public BestBTCScenario(UHC plugin) {
        super("Best BTC", "For every 10 minutes (starting at PvP time) you are under Y=50, you gain a heart. Going above Y=50 will take you off the list. To get back on, you must mine a diamond.", new ItemBuilder(Material.DIAMOND).name("Best BTC").make());

        this.uhc = plugin;
    }

    public HashSet<UUID> list = new HashSet<>();
    private int timer;
    private BukkitTask task;

    @Override
    public void onToggle(boolean newState, Player toggler) {
        if (newState) return;

        if (task != null) {
            task.cancel();
            sendMessage(toggler, "&bBestBTC tasks have been disabled!");
        }

        task = null;
    }

    @EventHandler
    public void onPvP(PvPEnableEvent e) {
        if (!isEnabled()) return;

        UHCPlayerColl.get().getAllPlaying().forEach(uhcPlayer -> list.add(uhcPlayer.getUuid()));
        Bukkit.getOnlinePlayers().forEach(o -> o.playSound(o.getLocation(), Sound.NOTE_BASS, 5, 5));
        broadcast("&eBestBTC has begun!");
        timer = 600;
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isEnabled()) return;


                timer -= 30;
                if (timer == 0) {
                    timer = 600;
                    UHCPlayerColl.get().getAllPlaying().forEach(uhcPlayer -> {
                        if (list.contains(uhcPlayer.getUuid())) {
                            Player player = uhcPlayer.getPlayer();
                            player.setMaxHealth(player.getMaxHealth() + 2);
                            player.setHealth(player.getHealth() + 2);
                            sendMessage(player, "&eYou have received a BestBTC heart!");
                        }
                    });
                }

            }
        }.runTaskTimer(uhc, 0, 20 * 30); //10 mins = 60 seconds * 20 ticks * 10 mins
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (isEnabled()) return;
        Player player = e.getPlayer();
        if (!PlayerUtils.isPlaying(player)) return;

        if (list.contains(player.getUniqueId()) && e.getTo().getY() > 50) {
            broadcast("&6" + player.getName() + "&4 moved above y:50!");
            list.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!isEnabled()) return;

        Player player = e.getPlayer();
        if (!PlayerUtils.isPlaying(player)) return; // Don't count for spec
        Block block = e.getBlock();
        if (block.getType() != Material.DIAMOND_ORE) return;

        if (!list.contains(player.getUniqueId())) {
            list.add(player.getUniqueId());
            broadcast("&b" + player.getName() + " has mined a diamond and is back on the BestBTC list!");
        }

    }

    public HashSet<UUID> getList() {
        return list;
    }
}
