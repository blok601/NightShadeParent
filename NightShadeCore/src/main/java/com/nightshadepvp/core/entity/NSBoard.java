package com.nightshadepvp.core.entity;

import com.google.common.collect.Lists;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

/**
 * Created by Blok on 3/15/2019.
 */
public abstract class NSBoard {

    public static int BOARDID = 0;

    private String title;
    private Objective objective;

    private ScoreboardManager bukkitScoreboardManager;
    private Scoreboard bukkitScoreboard;

    private ArrayList<String> lines;

    public NSBoard(String title, DisplaySlot slot) {
        this.title = title;
        this.bukkitScoreboardManager = Bukkit.getServer().getScoreboardManager();
        this.bukkitScoreboard = bukkitScoreboardManager.getNewScoreboard();

        this.objective = this.bukkitScoreboard.registerNewObjective("NSBOARD" + BOARDID, "dummy");
        this.objective.setDisplayName(ChatUtils.format(title));
        this.objective.setDisplaySlot(slot);

        this.lines = Lists.newArrayList();

        BOARDID++;
    }

    public void line(String input, int bukkitScore) {
        this.objective.getScore(ChatUtils.format(input)).setScore(bukkitScore);
    }

    public void clear() {
        this.objective.unregister();
        this.bukkitScoreboard.getTeams().forEach(Team::unregister);
    }

    public int getBukkitScore(String entry) {
        return this.objective.getScore(entry).getScore();
    }

    public void resetScore(String entry) {
        this.bukkitScoreboard.resetScores(entry);
    }

    public void editScore(String entry, int bukkitScore) {
        resetScore(entry);
        line(entry, bukkitScore);
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setup() {
        if (this.lines.size() > 16) {
            throw new IllegalArgumentException("Scoreboard length can only be 16 lines!");
        }


        for (int i = 0; i < this.lines.size(); i++) {
            line(lines.get(i), i);
        }
    }

    public void updateScoreforPlayer(Player player, String entry) {

    }
}
