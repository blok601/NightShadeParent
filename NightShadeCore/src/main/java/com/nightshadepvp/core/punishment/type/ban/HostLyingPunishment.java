package com.nightshadepvp.core.punishment.type.ban;

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
public class HostLyingPunishment extends AbstractPunishment {

    public HostLyingPunishment() {
        super("Lying to the Host", Material.SIGN, PunishmentType.BAN);

        this.addChild(new Punishment("Lying to the Host", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Lying to the Host &8(&51st Offense&8)")
                .lore("&eClick to ban the player for 1d for lying to the host").make(),
                this, Collections.singletonList("ban %player% 1d Lying to the Host"), PunishmentType.BAN
        ), 20);
    }
}
