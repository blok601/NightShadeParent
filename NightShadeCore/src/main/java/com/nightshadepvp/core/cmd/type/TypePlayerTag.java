package com.nightshadepvp.core.cmd.type;

import com.massivecraft.massivecore.command.type.enumeration.TypeEnum;
import com.nightshadepvp.core.entity.objects.PlayerTag;

public class TypePlayerTag extends TypeEnum<PlayerTag> {

    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static TypePlayerTag i = new TypePlayerTag();
    public static TypePlayerTag get() { return i; }
    public TypePlayerTag() { super(PlayerTag.class); }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public String getName()
    {
        return "tag";
    }

}
