package me.blok601.nightshadeuhc.gui;

import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 3/7/2018.
 */
public class BorderConfigGUI {

    public BorderConfigGUI(Player player){
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eBorder Config");
        builder.rows(1);

        ItemBuilder item;
        for (int i = 0; i < 9; i++){
            if(GameManager.getShrinks()[i] == 0){
                item = new ItemBuilder(Material.EMERALD_BLOCK).lore(ChatUtils.format("&6Not Set"));
            }else{
                item = new ItemBuilder(Material.EMERALD_BLOCK).lore(ChatUtils.format("&6" + GameManager.getShrinks()[i]));
            }
            builder.item(i, item.make());
        }

        player.openInventory(builder.make());
    }

}
