package com.nightshadepvp.core.gui.guis;

import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.objects.PlayerTag;
import com.nightshadepvp.core.gui.GuiBuilder;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/3/2018.
 */
public class PlayerTagGUI {

    public PlayerTagGUI(Player player) {

        NSPlayer user = NSPlayer.get(player);
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eTag Selector");
        builder.rows(getRows());

        int i = 0;
        ItemBuilder b;
        for (PlayerTag tag : PlayerTag.values()){
//            builder.item(i, color.getStack());

            b = new ItemBuilder(tag.getStack().getType());
            b.name(tag.getStack().getItemMeta().getDisplayName());
            if(!user.getUnlockedTags().contains(tag)){
                b.lore(ChatUtils.format("&cLocked"));
            }

            if(user.getCurrentTag() == tag){
                b.lore(ChatUtils.format("&aSelected"));
            }

            builder.item(i, b.make());

            i++;
        }
        player.openInventory(builder.make());
    }

    private int getRows(){
        int amt = PlayerTag.values().length;

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
