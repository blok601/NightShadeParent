package me.blok601.nightshadeuhc.gui;


import com.nightshadepvp.core.utils.ItemBuilder;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 11/11/2017.
 */
public class PresetGUI {

    public PresetGUI(Player player){
        GuiBuilder gui = new GuiBuilder();
        gui.name("&6Preset Selector");
        gui.rows(3);

        ItemStack classic = new ItemBuilder(Material.PAINTING).name(ChatUtils.format("&5Classic Options")).make();

        ItemStack rush = new ItemBuilder(Material.IRON_SWORD).name(me.blok601.nightshadeuhc.utils.ChatUtils.format("&5Rush Options")).make();

        ItemStack preset = new ItemBuilder(Material.PAPER).name(ChatUtils.format("&5Custom Preset")).make();


        gui.item(10, classic);
        gui.item(13, rush);
        gui.item(16, preset);

        player.openInventory(gui.make());
    }

}
