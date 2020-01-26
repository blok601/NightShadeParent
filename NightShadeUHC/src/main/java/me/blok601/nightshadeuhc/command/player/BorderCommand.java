package me.blok601.nightshadeuhc.command.player;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Blok on 7/18/2019.
 */
public class BorderCommand implements UHCCommand {

    private GameManager gameManager;

    public BorderCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "border"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        UHCPlayer uhcPlayer = UHCPlayer.get(s);

        if (!GameState.gameHasStarted()) {
            uhcPlayer.msg(ChatUtils.message("&cThe game hasn't started yet!"));
            return;
        }

        int currentBorder = gameManager.getShrinks()[gameManager.getBorderID() - 1];
        int nextBorder = (gameManager.getBorderID() + 1 < gameManager.getShrinks().length ? gameManager.getShrinks()[gameManager.getBorderID()] : -1);

        uhcPlayer.msg(ChatUtils.format("&f&m----------------------------"));
        uhcPlayer.msg(ChatUtils.format("&fCurrent Border: &b" + currentBorder));
        uhcPlayer.msg(ChatUtils.format("&fNext Shrink: &b" + (nextBorder > 0 ? nextBorder : "N/A")));
        uhcPlayer.msg(ChatUtils.format("&f&m----------------------------"));

    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}
