package com.nightshadepvp.tournament.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 6/12/2018.
 */
public class PlayerUtils {

    public static boolean playerEmptyInventory(Player p) {
        for (ItemStack itemStack : p.getInventory().getContents()) {
            if (itemStack != null || itemStack.getType() != Material.AIR) {
                return false;
            }
        }

        return true;
    }

    public static Integer generateRandomID() {
        int max = 5000;
        Random random = ThreadLocalRandom.current();
        return random.nextInt(max);
    }

    public static void clearPlayer(Player pls, boolean inv) {
        if (inv) {
            pls.getInventory().clear();
            pls.getInventory().setArmorContents(null);
        }
        for (PotionEffect eff : pls.getActivePotionEffects()) {
            pls.removePotionEffect(eff.getType());
        }

        pls.setExp(0.0F);
        pls.setLevel(0);
        pls.setFoodLevel(20);
        pls.setHealth(pls.getMaxHealth());
    }
}
