package com.nightshadepvp.core.entity;

import com.massivecraft.massivecore.store.SenderColl;
import com.massivecraft.massivecore.store.SenderEntity;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.store.NSStore;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NSPlayerColl extends SenderColl<NSPlayer> {
    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static NSPlayerColl i = new NSPlayerColl();

    public static NSPlayerColl get() {
        return i;
    }

    public NSPlayerColl() {
        super("core_nsplayers", NSPlayer.class, NSStore.getDb(), Core.get());
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

    public ArrayList<NSPlayer> getAllPlayerStaffOnline(){
        return getAllOnline().stream().filter(nsPlayer -> nsPlayer.hasRank(Rank.TRIAL)).filter(SenderEntity::isPlayer).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<NSPlayer> getAllPlayersOnline(){
        return getAllOnline().stream().filter(SenderEntity::isPlayer).collect(Collectors.toCollection(ArrayList::new));
    }
}
