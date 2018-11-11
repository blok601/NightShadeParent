package com.nightshadepvp.meetup;

import com.massivecraft.massivecore.MassivePlugin;
import com.nightshadepvp.meetup.entity.handler.GameHandler;
import com.nightshadepvp.meetup.scoreboard.PlayerScoreboard;
import com.nightshadepvp.meetup.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;

public final class Meetup extends MassivePlugin {

    private static Meetup i;

    public Meetup() {
        Meetup.i = this;
    }

    private ScoreboardManager scoreboardManager;
    private GameHandler gameHandler;

    @Override
    public void onEnableInner() {
        this.activateAuto();
        getConfig().options().copyDefaults(true);
        saveConfig();
        gameHandler = new GameHandler(this);
        scoreboardManager = new ScoreboardManager(this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            scoreboardManager.getPlayerScoreboards().values().forEach(PlayerScoreboard::update);
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Meetup get() {
        return i;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
