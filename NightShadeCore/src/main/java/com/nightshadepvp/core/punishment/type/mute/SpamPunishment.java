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
public class SpamPunishment extends AbstractPunishment {

    public SpamPunishment() {
        super("Spam", Material.BOOKSHELF, PunishmentType.MUTE);

        this.addChild(new Punishment("Spam (1st Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Spam &8(1st Offense&8)")
                .lore("&eClick to mute the player for 15m for spam").make(),
                this, Collections.singletonList("mute %player% 15m Spam"), PunishmentType.MUTE
        ), 20);

        this.addChild(new Punishment("Spam (2nd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Spam &8(2nd Offense&8)")
                .lore("&eClick to mute the player for 30m for spam").make(),
                this, Collections.singletonList("mute %player% 30m Spam"), PunishmentType.MUTE
        ), 21);

        this.addChild(new Punishment("Spam (3rd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Spam &8(3rd Offense&8)")
                .lore("&eClick to mute the player for 45m for spam").make(),
                this, Collections.singletonList("mute %player% 45m Spam"), PunishmentType.MUTE
        ), 22);

        this.addChild(new Punishment("Spam (4th Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Spam &8(4th Offense&8)")
                .lore("&eClick to mute the player for 1h for spam").make(),
                this, Collections.singletonList("mute %player% 1h Spam"), PunishmentType.MUTE
        ), 23);
    }
}
