package me.blok601.nightshadeuhc.util;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Master on 7/24/2017.
 */
public class PlayerUtils {

    private static ArrayList<UUID> frozen = new ArrayList<>();

    @Getter
    private static HashMap<UUID, Runnable> toConfirm = new HashMap<>();

    public static HashMap<String /* Country*/, Integer /* People tally*/> locations = new HashMap<>();

    public static void freezePlayer(Player punisher, Player target){
        frozen.add(target.getUniqueId());
        punisher.sendMessage(ChatUtils.message("&6You have frozen &9" + target.getName() + "!"));
        target.sendMessage(ChatUtils.message("&4&lYou have been frozen. Logging out will result in a ban!"));
    }

    public static void unFreeze(Player target) {
        if(!frozen.contains(target.getUniqueId())) {
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


}
