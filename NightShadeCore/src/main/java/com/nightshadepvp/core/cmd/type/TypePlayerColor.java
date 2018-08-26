package com.nightshadepvp.core.cmd.type;

import com.massivecraft.massivecore.command.type.TypeColor;
import com.massivecraft.massivecore.command.type.enumeration.TypeEnum;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.entity.objects.PlayerEffect;

public class TypePlayerColor extends TypeEnum<PlayerColor> {

    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static TypePlayerColor i = new TypePlayerColor();
    public static TypePlayerColor get() { return i; }
    public TypePlayerColor() { super(PlayerColor.class); }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public String getName()
    {
        return "color";
    }

}
