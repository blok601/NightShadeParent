package com.nightshadepvp.tournament.entity;

import com.massivecraft.massivecore.store.SenderColl;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.store.NSStore;
import com.nightshadepvp.tournament.Tournament;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Blok on 7/18/2018.
 */
public class TPlayerColl extends SenderColl<TPlayer> {
    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static TPlayerColl i = new TPlayerColl();

    public static TPlayerColl get() {
        return i;
    }

    public TPlayerColl() {
        super("tourney_tplayers", TPlayer.class, NSStore.getDb(), Tournament.get());
    }

    // -------------------------------------------- //
    // STACK TRACEABILITY
    // -------------------------------------------- //

    @Override
    public void onTick() {
        super.onTick();
    }

    // -------------------------------------------- //
    // EXTRAS
    // -------------------------------------------- //

    @Override
    public long getCleanInactivityToleranceMillis() {
        return MConf.get().cleanInactivityToleranceMillis;
    }


    public Collection<TPlayer> getAllPlayers(){
        return getAllOnline().stream().filter(TPlayer::isSeeded).filter(tPlayer -> !tPlayer.isSpectator()).collect(Collectors.toList());
    }
}