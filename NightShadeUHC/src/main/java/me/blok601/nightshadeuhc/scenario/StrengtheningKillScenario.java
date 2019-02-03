package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Blok on 2/3/2019.
 */
public class StrengtheningKillScenario extends Scenario {

    public StrengtheningKillScenario() {
        super("Strengthening Kill", "Upon killing a player, you receive Strength I for 10 seconds", new ItemStack(Material.NETHER_WARTS));
    }

    @EventHandler
    public void onKill(CustomDeathEvent e) { //CDE only called during games anyway - but check j incase
        if (!isEnabled()) return;
        if (!GameState.gameHasStarted()) return;


        Player killer = e.getKiller();
        if (killer == null) return;
        killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
        killer.sendMessage(ChatUtils.format(getPrefix() + "&eYou have been awarded Strength I for 10 seconds!"));
    }
}
