package com.nightshadepvp.meetup.entity;

import com.massivecraft.massivecore.store.SenderColl;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.store.NSStore;
import com.nightshadepvp.meetup.Meetup;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Blok on 10/15/2018.
 */
public class MPlayerColl extends SenderColl<MPlayer> {
    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static MPlayerColl i = new MPlayerColl();

    public static MPlayerColl get() {
        return i;
    }

    public MPlayerColl() {
        super("meetup_mplayers", MPlayer.class, NSStore.getDb(), Meetup.get());
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

    public ArrayList<MPlayer> getAllIngamePlayers(){
        return MPlayerColl.get().getAllOnline().stream().filter(mPlayer -> !mPlayer.isSpectator()).collect(Collectors.toCollection(ArrayList::new));
    }
}
