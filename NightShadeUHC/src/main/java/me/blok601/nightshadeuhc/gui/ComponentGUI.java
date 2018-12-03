package me.blok601.nightshadeuhc.gui;

import me.blok601.nightshadeuhc.listeners.modules.Component;
import me.blok601.nightshadeuhc.listeners.modules.ComponentHandler;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/10/2017.
 */
public class ComponentGUI {

    public ComponentGUI(Player player){
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eToggleable Options");
        builder.rows(3);

        int slot = 0;
        for (Component component : ComponentHandler.getInstance().getComponents()){
            builder.item(slot, component.getItemStack());
            slot++;
        }

        builder.item(26, new ItemBuilder(Material.ARROW).name("&cBack").lore("&eClick to go back to the main menu").make());

        player.openInventory(builder.make());
    }

}
