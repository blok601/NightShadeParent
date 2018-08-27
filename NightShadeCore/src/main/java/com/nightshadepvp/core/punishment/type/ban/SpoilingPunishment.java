package com.nightshadepvp.core.punishment.type.ban;

import com.nightshadepvp.core.punishment.AbstractPunishment;
import com.nightshadepvp.core.punishment.PunishmentType;
import org.bukkit.Material;

/**
 * Created by Blok on 8/27/2018.
 */
public class SpoilingPunishment extends AbstractPunishment {

    public SpoilingPunishment(String name, Material material, PunishmentType punishmentType) {
        super(name, material, punishmentType);
    }
}
