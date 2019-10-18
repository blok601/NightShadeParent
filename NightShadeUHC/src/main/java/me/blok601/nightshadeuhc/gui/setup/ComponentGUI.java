package me.blok601.nightshadeuhc.gui.setup;

import me.blok601.nightshadeuhc.component.Component;
import me.blok601.nightshadeuhc.component.ComponentHandler;
import me.blok601.nightshadeuhc.gui.GuiBuilder;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/10/2017.
 */
public class ComponentGUI {

    public ComponentGUI(Player player, ComponentHandler componentHandler){
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eToggleable Options");
        builder.rows(3);

        int slot = 0;
        for (Component component : componentHandler.getComponents()){
            ItemBuilder itemBuilder = new ItemBuilder(component.getMaterial());
            itemBuilder.name("&e" + component.getName());
            itemBuilder.lore(component.isEnabled() ? "&aEnabled" : "&cDisabled");
            itemBuilder.lore("&7&o(&6&oi&7&o) &6&o" + component.getDescription());
            builder.item(slot, itemBuilder.make());
            slot++;
        }

        builder.item(26, new ItemBuilder(Material.ARROW).name("&cBack").lore("&eClick to go back to the main menu").make());

        player.openInventory(builder.make());
    }

}
