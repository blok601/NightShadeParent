package com.nightshadepvp.core.punishment.type.ban;

import com.nightshadepvp.core.punishment.AbstractPunishment;
import com.nightshadepvp.core.punishment.Punishment;
import com.nightshadepvp.core.punishment.PunishmentHandler;
import com.nightshadepvp.core.punishment.PunishmentType;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.Collections;

/**
 * Created by Blok on 8/25/2018.
 */
public class XrayPunishment extends AbstractPunishment {

    public XrayPunishment() {
        super("Xray",Material.DIAMOND_ORE, PunishmentType.BAN);

        this.addChild(new Punishment("Xray (1st Offense)",
                new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                        .name("&5Hacked Client &8(&51st Offense&8)")
                        .lore("&eBan the player for 2 months for Xray").make(), this, Collections.singletonList("ban %player% 2mo Xray"), PunishmentType.BAN), 20
        );

        this.addChild(new Punishment("Xray (2nd Offense)",
                new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                        .name("&5Hacked Client &8(&52nd Offense&8)")
                        .amount(2)
                        .lore("&eBan the player permanently for Xray").make(), this, Collections.singletonList("ban %player% Xray"), PunishmentType.BAN), 21
        );

    }
}
