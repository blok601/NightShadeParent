package com.nightshadepvp.meetup.scoreboard.provider.type;

import com.nightshadepvp.meetup.entity.MPlayerColl;
import com.nightshadepvp.meetup.entity.handler.GameHandler;
import com.nightshadepvp.meetup.scoreboard.ScoreboardProvider;
import com.nightshadepvp.meetup.scoreboard.ScoreboardText;
import com.nightshadepvp.meetup.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blok on 10/15/2018.
 */
public class LobbyScoreboard extends ScoreboardProvider {

    private GameHandler gameHandler;
    public LobbyScoreboard(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    /**
     * Gets the current Title of the Scoreboard
     *
     * @param p
     * @return the Scoreboard Title
     */
    @Override
    public String getTitle(Player p) {
        return ChatUtils.format("&5Meetup");
    }

    /**
     * Gets the current Sidebar lines of the Scoreboard
     *
     * @param p
     * @return the Scoreboard Sidebar lines
     */
    @Override
    public List<ScoreboardText> getLines(Player p) {
        List<ScoreboardText> lines = new ArrayList<>();
        lines.add(new ScoreboardText(ChatUtils.format("&5&m--------------------")));
        lines.add(new ScoreboardText(ChatUtils.format("&6Status: &e" + gameHandler.getGameState().getFormatted())));
        lines.add(new ScoreboardText(ChatUtils.format("&6Players: &e" + MPlayerColl.get().getAllIngamePlayers().size() + "/" + gameHandler.getMaxPlayers())));
        lines.add(new ScoreboardText(ChatUtils.format("&5&m--------------------&r")));
        lines.add(new ScoreboardText(ChatUtils.format("&ediscord.me/NightShadeMC")));
        return lines;
    }
}
