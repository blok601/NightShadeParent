package me.blok601.nightshadeuhc.gui.setup;

import me.blok601.nightshadeuhc.component.Component;
import me.blok601.nightshadeuhc.component.ComponentHandler;
import me.blok601.nightshadeuhc.gui.GuiBuilder;
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
        ItemBuilder itemBuilder;
        for (Component component : ComponentHandler.getInstance().getComponents()){
            itemBuilder = new ItemBuilder(component.getItemStack());
            itemBuilder.lore(component.isEnabled() ? "&aEnabled" : "&cDisabled");
            builder.item(slot, itemBuilder.make());
            slot++;
        }

        builder.item(26, new ItemBuilder(Material.ARROW).name("&cBack").lore("&eClick to go back to the main menu").make());

        player.openInventory(builder.make());
    }

}
