package me.blok601.nightshadeuhc.util;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.MConf;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Master on 7/24/2017.
 */
public class PlayerUtils {

    private static ArrayList<UUID> frozen = new ArrayList<>();

    public static HashMap<String /* Country*/, Integer /* People tally*/> locations = new HashMap<>();

    public static void freezePlayer(Player punisher, Player target) {
        frozen.add(target.getUniqueId());
        punisher.sendMessage(ChatUtils.message("&6You have frozen &9" + target.getName() + "!"));
        target.sendMessage(ChatUtils.message("&4&lYou have been frozen. Logging out will result in a ban!"));
    }

    public static void unFreeze(Player target) {
        if (!frozen.contains(target.getUniqueId())) {
            return;
        }

        frozen.remove(target.getUniqueId());

        target.sendMessage(ChatUtils.message("&aYou have been unfrozen!"));
    }

    public static boolean wearingArmor(Player player) {
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                return true;
            }
        }
        return false;
    }

    public static void giveBulkItems(Player player, Collection<ItemStack> items) {
        for (ItemStack itemStack : items) {

        }
    }

    public static boolean inventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

    public static void giveItem(ItemStack itemStack, Player player) {
        if (inventoryFull(player)) {
            player.getWorld().dropItemNaturally(player.getLocation().add(0.5, 0.5, 0.5), itemStack);
            return;
        }

        player.getInventory().addItem(itemStack);
    }

    public static boolean inGameWorld(Player player) {
        World world = player.getWorld();
        if (world.getName().equalsIgnoreCase(MConf.get().getSpawnLocation().asBukkitLocation().getWorld().getName()) ||
                world.getName().equalsIgnoreCase(MConf.get().getArenaLocation().asBukkitLocation().getWorld().getName())) {
            return false;
        }

        return true;
    }

    public static boolean isPlaying(Player player) {
        return UHCPlayer.get(player).getPlayerStatus() == PlayerStatus.PLAYING;
    }

    public static boolean isPlaying(UUID uuid) {
        if (UHC.loggedOutPlayers.contains(uuid)) return true;

        UHCPlayer uhcPlayer = UHCPlayer.get(uuid);
        return uhcPlayer.getPlayerStatus() == PlayerStatus.PLAYING;
    }



}
