package com.nightshadepvp.nightcheat;

import org.bukkit.entity.Player;

public interface Advantage {

    void flag(Player player);

    void log(Player player, boolean silent);

}
