package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.MathUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Blok on 2/3/2019.
 */
public class RottenApplesScenario extends Scenario {


    public RottenApplesScenario() {
        super("Rotten Apples", "Eating a golden apple has the 7.5% chance of giving nausea and slowness for 10 seconds", new ItemStack(Material.FERMENTED_SPIDER_EYE));
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        if (!isEnabled()) return;

        if (!GameState.gameHasStarted()) {
            return;
        }

        if (e.getItem().getType() == Material.GOLDEN_APPLE) {
            Player p = e.getPlayer();
            if (UHCPlayer.get(p).getPlayerStatus() == PlayerStatus.PLAYING) {
                if (MathUtil.getChance(10)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
                    p.sendMessage(ChatUtils.format(getPrefix() + "&cYou have received slowness and nausea for 10 seconds!"));
                }
            }
        }

    }

}
