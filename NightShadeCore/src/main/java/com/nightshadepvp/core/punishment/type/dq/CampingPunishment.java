package com.nightshadepvp.core.punishment.type.dq;

import com.nightshadepvp.core.punishment.AbstractPunishment;
import com.nightshadepvp.core.punishment.Punishment;
import com.nightshadepvp.core.punishment.PunishmentHandler;
import com.nightshadepvp.core.punishment.PunishmentType;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.Collections;

/**
 * Created by Blok on 8/26/2018.
 */
public class CampingPunishment extends AbstractPunishment {

    public CampingPunishment() {
        super("Camping", Material.COBBLESTONE, PunishmentType.BAN);

        this.addChild(new Punishment("Camping (1st Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Camping &8(&51st Offense&8)")
                .lore("&eClick to warn the player for camping 1st offense").make(),
                this, Collections.singletonList("warn %player% Camping 1/1"), PunishmentType.WARNING
        ), 22);

        this.addChild(new Punishment("Camping (2nd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Camping &8(&52nd Offense&8)")
                .amount(2)
                .lore("&eClick to DQ the player for camping 2nd offense").make(),
                this, Collections.singletonList(""), PunishmentType.DQ
        ), 23);
    }
}
