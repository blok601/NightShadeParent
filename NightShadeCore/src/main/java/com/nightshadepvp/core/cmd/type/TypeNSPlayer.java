package com.nightshadepvp.core.cmd.type;

import com.massivecraft.massivecore.command.type.sender.TypeSenderEntity;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;

public class TypeNSPlayer {


    public static TypeSenderEntity<NSPlayer> get() {
        return NSPlayerColl.get().getTypeEntity();
    }

}
