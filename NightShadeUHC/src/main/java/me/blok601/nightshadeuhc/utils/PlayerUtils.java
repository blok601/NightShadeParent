package me.blok601.nightshadeuhc.utils;

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
        ItemStack[] armor = player.getInventory().getArmorContents();
        return armor[0] != null && armor[1] != null && armor[2] != null && armor[3] != null; //This will return true if any of these fail -> if they fail they are wearing some armor -> returns true
    }


}
