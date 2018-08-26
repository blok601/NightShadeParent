package com.nightshadepvp.core.gui.guis;

import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.gui.GuiBuilder;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/15/2017.
 */
public class ChatColorGUI {

    public ChatColorGUI(Player player){

        NSPlayer user = NSPlayer.get(player.getUniqueId());

        GuiBuilder builder = new GuiBuilder();
        builder.name("&eColor Selector");
        builder.rows(getRows());

        int i = 0;
        ItemBuilder b;
        for (PlayerColor color : PlayerColor.values()) {
//            builder.item(i, color.getStack());

            b = new ItemBuilder(color.getStack().getType());
            b.name(color.getStack().getItemMeta().getDisplayName());
            if (!user.getColors().contains(color)) {
                b.lore(ChatUtils.format("&cLocked"));
            }

            if (user.getColor() == color) {
                b.lore(ChatUtils.format("&aSelected"));
            }

            builder.item(i, b.make());

            i++;
        }
        player.openInventory(builder.make());
    }

    private int getRows(){
        int amt = PlayerColor.values().length;

        for (int i = 0; i < 54; i++){
            if(i < amt){
                continue;
            }

            if(i >= amt && i%9==0){
                return i/9;
            }
        }

        return 6;

    }

}
