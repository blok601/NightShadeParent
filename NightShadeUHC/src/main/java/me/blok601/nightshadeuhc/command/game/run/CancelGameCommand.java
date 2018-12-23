package me.blok601.nightshadeuhc.command.game.run;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/20/2017.
 */
public class CancelGameCommand implements UHCCommand {

    private GameManager gameManager;

    public CancelGameCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "cancelgame"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (GameState.getState() != GameState.PRE_SCATTER) {
            p.sendMessage(ChatUtils.message("&cYou can only do this during the scatter countdown!"));
            return;
        }

        gameManager.getGameCountdownTask().cancel();
        gameManager.setGameCountdownTask(null);
        p.sendMessage(ChatUtils.message("&bThe game countdown has been cancelled!"));


    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIAL;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
