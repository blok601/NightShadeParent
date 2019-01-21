package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Blok on 1/21/2019.
 */
public class SportsmanshipComponent extends Component {

    public SportsmanshipComponent() {
        super("Sportsmanship Filter", Material.PAPER, false, "Toggle whether default players should only be allowed to say \'gg\' or \'gf\' and thank the host after death?");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        if (!isEnabled()) return;

        Player p = event.getPlayer();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        String message = event.getMessage();
        if (uhcPlayer.getPlayerStatus() == PlayerStatus.DEAD) {
            if (!message.equalsIgnoreCase("gf") || !message.equalsIgnoreCase("gg")) {
                event.setCancelled(true);
                p.sendMessage(ChatUtils.message("&cThe sportsmanship filter is on! You can only say gg or gf. If you would like to say anything else, use /helpop"));
                return;
            }
        }
    }
}
