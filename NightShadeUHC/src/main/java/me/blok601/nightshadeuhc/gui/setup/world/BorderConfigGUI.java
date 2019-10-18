package me.blok601.nightshadeuhc.gui.setup.world;

import me.blok601.nightshadeuhc.gui.GuiBuilder;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 3/7/2018.
 */
public class BorderConfigGUI {

    public BorderConfigGUI(Player player){
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eBorder Config");
        builder.rows(3);

        ItemBuilder item;
        for (int i = 0; i < 9; i++){
            if(GameManager.get().getShrinks()[i] == 0){
                item = new ItemBuilder(Material.EMERALD_BLOCK).lore(ChatUtils.format("&6Not Set"));
            }else{
                item = new ItemBuilder(Material.EMERALD_BLOCK).lore(ChatUtils.format("&6" + GameManager.get().getShrinks()[i]));
            }
            item.lore("&7(&6i&7) &6Right click to increase border by 50, left click to decrease by 50, middle click to set a custom value");
            builder.item(i, item.make());
        }

        builder.item(26, new ItemBuilder(Material.ARROW).name("&cBack").lore("&eClick to go back to the main menu").make());

        player.openInventory(builder.make());
    }

}
