package com.nightshadepvp.meetup.engine;

import com.massivecraft.massivecore.Engine;
import com.nightshadepvp.meetup.Meetup;
import com.nightshadepvp.meetup.entity.MPlayer;
import com.nightshadepvp.meetup.entity.handler.GameHandler;
import com.nightshadepvp.meetup.scoreboard.ScoreboardManager;
import com.nightshadepvp.meetup.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Blok on 10/15/2018.
 */
public class EnginePlayer extends Engine {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        MPlayer mPlayer = MPlayer.get(p);

        ScoreboardManager scoreboardManager = Meetup.get().getScoreboardManager();
        scoreboardManager.addToPlayerCache(p);
        mPlayer.msg(ChatUtils.message("&eWelcome to NightShadePvP UHC Meetup v" + Meetup.get().getDescription().getVersion()));
        mPlayer.msg(ChatUtils.message("&eNightShade Meetup is currently in &cclosed beta! &eEverything is currently being tested, modified and balanced to make sure the user has the best experience possible."));
        mPlayer.msg(ChatUtils.message("&ePlease make sure to report all bugs in the discord @ discord.me/NightShadeMC"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        MPlayer mPlayer = MPlayer.get(p);
        Meetup.get().getScoreboardManager().removeFromPlayerCache(p);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        GameHandler gameHandler = Meetup.get().getGameHandler();
        if(gameHandler.inGame()){
            //TODO: Check if they were already in the game
            //TODO: Make them spectator
        }
    }
}
