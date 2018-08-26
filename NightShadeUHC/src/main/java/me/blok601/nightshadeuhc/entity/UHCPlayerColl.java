package me.blok601.nightshadeuhc.entity;

import com.massivecraft.massivecore.store.SenderColl;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.store.NSStore;
import me.blok601.nightshadeuhc.UHC;

public class UHCPlayerColl extends SenderColl<UHCPlayer> {
    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static UHCPlayerColl i = new UHCPlayerColl();

    public static UHCPlayerColl get() {
        return i;
    }

    public UHCPlayerColl() {
        super("uhc_uhcplayers", UHCPlayer.class, NSStore.getDb(), UHC.get());
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


}
