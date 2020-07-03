package com.nightshadepvp.nightcheat;

import org.bukkit.entity.Player;

public interface Advantage {

    void flag(Player player, Advantage advantage);

    void log(Player player, boolean notify, String message);

}
