package com.nightshadepvp.core.punishment.type.mute;

import com.nightshadepvp.core.punishment.AbstractPunishment;
import com.nightshadepvp.core.punishment.Punishment;
import com.nightshadepvp.core.punishment.PunishmentHandler;
import com.nightshadepvp.core.punishment.PunishmentType;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.Collections;

public class DisrespectPunishment extends AbstractPunishment {
    public DisrespectPunishment() {
        super("Disrespect", Material.SPIDER_EYE, PunishmentType.MUTE );
        this.addChild(new Punishment("Disrespect (1st Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&Disrespect &8(&51st Offense&8)")
                .lore("&eClick to mute the player for 15m for Disrespect").make(),
                this, Collections.singletonList("mute %player% 15m Disrespect"), PunishmentType.MUTE
        ), 20);

        this.addChild(new Punishment("Disrespect (2nd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&Disrespect &8(&52nd Offense&8)")
                .lore("&eClick to mute the player for 45 for Disrespect").make(),
                this, Collections.singletonList("mute %player% 45m Disrespect"), PunishmentType.MUTE
        ), 21);

        this.addChild(new Punishment("Disrespect (3rd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&Disrespect &8(&53rd Offense&8)")
                .lore("&eClick to mute the player for 2h for Disrespect").make(),
                this, Collections.singletonList("mute %player% 2h Disrespect"), PunishmentType.MUTE
        ), 22);
    }
}
