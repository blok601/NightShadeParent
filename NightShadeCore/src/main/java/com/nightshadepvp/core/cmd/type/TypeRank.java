package com.nightshadepvp.core.cmd.type;

import com.massivecraft.massivecore.command.type.enumeration.TypeEnum;
import com.nightshadepvp.core.Rank;

public class TypeRank extends TypeEnum<Rank> {

    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static TypeRank i = new TypeRank();
    public static TypeRank get() { return i; }
    public TypeRank() { super(Rank.class); }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public String getName()
    {
        return "rank";
    }

}
