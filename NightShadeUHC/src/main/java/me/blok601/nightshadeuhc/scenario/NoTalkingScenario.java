package me.blok601.nightshadeuhc.scenario;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class NoTalkingScenario extends Scenario {
    public NoTalkingScenario() {
        super("No Talking", "Whenever you Talk, your location is broadcasted to everyone in chat", new ItemBuilder(Material.BARRIER).name(ChatUtils.format("No Talking")).make());
    }

    public void onChat (AsyncPlayerChatEvent e) {
        if (!isEnabled()) return;

        Player p = e.getPlayer();

        if (UHCPlayer.get(p)) {
            Location loc = e.getPlayer().getLocation();
            int x = (int) loc.getX();
            int y = (int) loc.getY();
            int z = (int) loc.getZ();
            Bukkit.broadcastMessage(ChatUtils.format(getPrefix()+ "&e" + e.getPlayer().getName() + " &7is located at &8(&e" +x + "," + y + "," + z + "&8)"));
        }


    }
}
