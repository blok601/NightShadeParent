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
public class XrayPunishment extends AbstractPunishment {

    public XrayPunishment() {
        super("Xray", new ItemBuilder(Material.DIAMOND_ORE).name("&5Xray").lore("&eClick to view punishment options for Xray").make(), PunishmentType.BAN);

        this.addChild(new Punishment("Xray (1st Offense)",
                new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                        .name("&5Hacked Client &8(&51st Offense&8)")
                        .lore("&eBan the player for 2 months for Xray").make(), this, "ban %playet% 2mo Xray"), 22
        );

        this.addChild(new Punishment("Xray (2nd Offense)",
                new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                        .name("&5Hacked Client &8(&52nd Offense&8)")
                        .amount(2)
                        .lore("&eBan the player permanently for Xray").make(), this, "ban %playet% Xray"), 23
        );

    }
}
