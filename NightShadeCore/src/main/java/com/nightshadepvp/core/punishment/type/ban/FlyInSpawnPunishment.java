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
public class FlyInSpawnPunishment extends AbstractPunishment {

    public FlyInSpawnPunishment() {
        super("Flying In Spawn", new ItemBuilder(Material.FEATHER).name("&5Fly in spawn").make(), PunishmentType.BAN);

        this.addChild(new Punishment("Flying (1st Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .name("&5Flying &8(1st Offense&&)")
                .lore("&eClick to ban for 1 minute for flying in spawn").make(),
                this, "ban %player% 1min Flying in spawn"
        ), 22);

        this.addChild(new Punishment("Flying (2nd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .amount(2)
                .name("&5Flying &8(2nd Offense&&)")
                .lore("&eClick to ban for 2 minutes for flying in spawn").make(),
                this, "ban %player% 2min Flying in spawn"
        ), 23);

        this.addChild(new Punishment("Flying (3rd Offense)", new ItemBuilder(PunishmentHandler.getInstance().getChildStack())
                .amount(3)
                .name("&5Flying &8(3rd Offense&&)")
                .lore("&eClick to ban permanently for flying in spawn").make(),
                this, "ban %player% Flying in spawn"
        ), 24);
    }

}
