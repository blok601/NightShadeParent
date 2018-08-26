package com.nightshadepvp.core.punishment.type.ban;

import com.nightshadepvp.core.punishment.AbstractPunishment;
import com.nightshadepvp.core.punishment.Punishment;
import com.nightshadepvp.core.punishment.PunishmentHandler;
import com.nightshadepvp.core.punishment.PunishmentType;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Blok on 8/25/2018.
 */
public class IllegalMiningPunishment extends AbstractPunishment {

    public IllegalMiningPunishment() {
        super("Illegal Mining", new ItemBuilder(Material.DIAMOND_PICKAXE).name("&5Illegal Mining").lore("&eClick to view the punishment options for illegal mining").make(), PunishmentType.WARNING);

        this.addChild(new Punishment("Illegal Mining (1st Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Illegal Mining &8(1st Offense&8)")
                .lore("&eClick to warn the player for illegal mining").make(),
                this, "warn %player% Illegal Mining 1/3"
        ), 22);

        this.addChild(new Punishment("Illegal Mining (2nd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Illegal Mining &8(2nd Offense&8)")
                .lore("&eClick to warn the player for illegal mining").make(),
                this, "warn %player% Illegal Mining 1/3"
        ), 22);
    }
}
