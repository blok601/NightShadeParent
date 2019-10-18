package com.nightshadepvp.core.punishment.type.dq;

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
public class StalkingPunishment extends AbstractPunishment {

    public StalkingPunishment() {
        super("Stalking", Material.SAND, PunishmentType.DQ); //Stalking media/ ranks is a 1d ban

        this.addChild(new Punishment("Stalking (1st Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Stalking &8(&51st Offense&8)")
                .lore("&eStalking first offense is a verbal warning.").make(),
                this, Collections.singletonList(""), PunishmentType.VERBBAL
        ), 20);

        this.addChild(new Punishment("Stalking (2nd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Stalking &8(&52nd Offense&8)")
                .lore("&eClick to DQ the player for stalking").make(),
                this, Collections.singletonList(""), PunishmentType.DQ
        ), 21);

        this.addChild(new Punishment("Stalking (2nd Offense - Media)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Stalking &8(&52nd Offense&8 - Media)")
                .lore("&eClick to ban the player for stalking a Media rank player").make(),
                this, Collections.singletonList("ban %player% 1d Stalking Media Rank"), PunishmentType.BAN
        ), 22);
    }
}
