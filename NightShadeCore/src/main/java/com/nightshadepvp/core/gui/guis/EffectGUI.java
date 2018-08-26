package com.nightshadepvp.core.gui.guis;


import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.gui.GuiBuilder;
import com.nightshadepvp.core.entity.objects.PlayerEffect;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/15/2017.
 */
public class EffectGUI {

    public EffectGUI(Player player){

        NSPlayer user = NSPlayer.get(player.getUniqueId());

        GuiBuilder builder = new GuiBuilder();
        builder.name("&eEffect Selector");
        builder.rows(getRows());

        int i = 0;
        ItemBuilder b;
        for (PlayerEffect color : PlayerEffect.values()){
//            builder.item(i, color.getStack());

            b = new ItemBuilder(color.getItem().getType());
            b.name(color.getItem().getItemMeta().getDisplayName());
            if(!user.getEffects().contains(color)){
                b.lore(ChatUtils.format("&cLocked"));
            }

            if(user.getEffect() == color){
                b.lore(ChatUtils.format("&aSelected"));
            }

            builder.item(i, b.make());

            i++;
        }


        player.openInventory(builder.make());
    }

    private int getRows(){
        int amt = PlayerEffect.values().length;

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
