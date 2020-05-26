package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CatsEyesScenario extends Scenario{

    public CatsEyesScenario() {
        super("Cats Eyes", "All players receive night vision at the start of the game", new ItemBuilder(Material.NETHER_STAR).name("Cats Eyes").make());
    }

    @EventHandler
    public void onStart(GameStartEvent event){
        if(!isEnabled()) return;
        for (Player player : Bukkit.getOnlinePlayers()){
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false));
            sendMessage(player, "&bYou have received night vision...use it wisely");
        }
    }
}
