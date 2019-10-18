package com.nightshadepvp.core.punishment.type.mute;

import com.nightshadepvp.core.punishment.AbstractPunishment;
import com.nightshadepvp.core.punishment.Punishment;
import com.nightshadepvp.core.punishment.PunishmentHandler;
import com.nightshadepvp.core.punishment.PunishmentType;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.Collections;

/**
 * Created by Blok on 8/27/2018.
 */
public class HackusationPunishment extends AbstractPunishment {

    public HackusationPunishment() {
        super("Hackusation", Material.CACTUS, PunishmentType.MUTE);

        this.addChild(new Punishment("Hackusation (1st Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Hackusation &8(&51st Offense&8)")
                .lore("&eClick to warn the player for hackusations in public chat").make(),
                this, Collections.singletonList("warn %player% Hackusation"), PunishmentType.WARNING
        ), 20);

        this.addChild(new Punishment("Hackusation (2nd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Hackusation &8(&52nd Offense&8)")
                .lore("&eClick to mute the player for 15 minutes for hackusations in public chat").make(),
                this, Collections.singletonList("mute %player% 15m Hackusations"), PunishmentType.MUTE
        ), 21);
    }
}
