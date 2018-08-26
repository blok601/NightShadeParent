package com.nightshadepvp.core.entity;

import com.massivecraft.massivecore.store.SenderColl;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.store.NSStore;

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
}
