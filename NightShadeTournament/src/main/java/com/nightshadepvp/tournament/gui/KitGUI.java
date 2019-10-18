package com.nightshadepvp.tournament.gui;

import com.nightshadepvp.core.gui.GuiBuilder;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/13/2018.
 */
public class KitGUI {

    public KitGUI(Player player){
        GuiBuilder builder = new GuiBuilder() ;
        builder.name("&eKit Selector");
        builder.rows(6);

        int i = 0;
        for (Kit kit : KitHandler.getInstance().getKits()){
            builder.item(i, kit.getDisplay());
            i++;
        }

        player.openInventory(builder.make());

    }

}
