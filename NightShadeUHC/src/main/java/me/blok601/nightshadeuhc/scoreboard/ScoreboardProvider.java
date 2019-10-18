package me.blok601.nightshadeuhc.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class ScoreboardProvider {
	
	/**
	 * Gets the current Title of the Scoreboard
	 * 
	 * @return the Scoreboard Title
	 */
	public abstract String getTitle(Player p);
	
	/**
	 * Gets the current Sidebar lines of the Scoreboard
	 * 
	 * @return the Scoreboard Sidebar lines
	 */
	public abstract List<ScoreboardText> getLines(Player p);
	
}