package com.nightshadepvp.meetup.engine;

import com.massivecraft.massivecore.Engine;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.meetup.Meetup;
import com.nightshadepvp.meetup.entity.MPlayer;
import com.nightshadepvp.meetup.entity.handler.GameHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Blok on 7/31/2019.
 */
public class EnginePlayer extends Engine {

    protected Meetup meetup = Meetup.get();
    private GameHandler gameHandler = meetup.getGameHandler();

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        //Check whitelist and stuff
        if (meetup.getGameHandler().gameHasStarted()) {
            //Whitelist essentially enabled -> check rank
            if (!NSPlayer.get(p).hasRank(Rank.YOUTUBE)) { //Allowed to spec
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "The game has already begun!");
                return;
            }

            e.allow();
            return;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        MPlayer mPlayer = MPlayer.get(p);

        if (gameHandler.gameHasStarted()){
            if(gameHandler.getPlaying().contains(p.getUniqueId())){
                //TODO: Remove logger and spawn back in
            }else{
                //Spectator
                //TODO: Set spectator mode
            }
        }else{
            //Waiting
            gameHandler.getPlaying().add(p.getUniqueId());
        }

    }

}
