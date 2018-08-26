package com.nightshadepvp.core.cmd.type;

import com.massivecraft.massivecore.command.type.enumeration.TypeEnum;
import com.nightshadepvp.core.entity.objects.PlayerEffect;

public class TypePlayerEffect extends TypeEnum<PlayerEffect> {

    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static TypePlayerEffect i = new TypePlayerEffect();
    public static TypePlayerEffect get() { return i; }
    public TypePlayerEffect() { super(PlayerEffect.class); }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public String getName()
    {
        return "effect";
    }

}
