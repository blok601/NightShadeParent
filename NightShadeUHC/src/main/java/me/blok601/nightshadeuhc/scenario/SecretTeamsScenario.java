package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Blok on 12/23/2018.
 */
public class SecretTeamsScenario extends Scenario {
    public SecretTeamsScenario() {
        super("Secret Teams", "All teams are hidden", new ItemBuilder(Material.BOOK).name("&eSecret Teams").make());
    }

    @EventHandler
    public void onPreProcess(PlayerCommandPreprocessEvent event){
        if(!isEnabled()) return;

        if(event.getMessage().startsWith("/team color")){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatUtils.message("&cYou can't color teams in Secret Teams!"));
        }
    }


}
