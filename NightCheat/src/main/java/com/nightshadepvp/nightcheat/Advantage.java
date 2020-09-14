package com.nightshadepvp.nightcheat;

import org.bukkit.entity.Player;

public interface Advantage {

    void flag(Player player, Advantage advantage, String message);

    void log(Player player, String message);

    void debug(String message);

}
