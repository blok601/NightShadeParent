package com.nightshadepvp.core.gui.guis;


import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.gui.GuiBuilder;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/15/2017.
 */
public class PrefrencesGUI {

    public PrefrencesGUI(Player player){

        NSPlayer user = NSPlayer.get(player.getUniqueId());

        GuiBuilder gui = new GuiBuilder();
        gui.name("&ePreferences");
        gui.rows(3);



        ItemBuilder messages = new ItemBuilder(Material.PAPER).name("&3Allow Messages").lore(ChatUtils.format("&5Click to toggle messages"));

        if(user.isReceivingPMs()){
            messages.name(ChatUtils.format("&aPrivate Messages"));
        }else{
            messages.name(ChatUtils.format("&cPrivate Messages"));
        }

        ItemBuilder nametagSelector = new ItemBuilder(Material.NAME_TAG).name("&3Nametag Selector").lore(ChatUtils.format("&5Click to view the nametag selector"));


        ItemBuilder selectParticles = new ItemBuilder(Material.MAGMA_CREAM).name("&3Particle Effects").lore(ChatUtils.format("&5Click to view the particle selector")).lore(ChatUtils.format("&4&lCOMING SOON!"));;

        ItemBuilder seeAllParticles = new ItemBuilder(Material.GOLD_NUGGET).name("&3See All Particles").lore(ChatUtils.format("&5See all player's particles or only yours")).lore(ChatUtils.format("&4&lCOMING SOON!"));;

        ItemBuilder color = new ItemBuilder(Material.WOOL).name("&3Chat Color Selector").lore(ChatUtils.format("&5Click to view the chat color menu"));

        gui.item(1, messages.make());
        gui.item(4, nametagSelector.make());
        gui.item(7, selectParticles.make());
        gui.item(21, seeAllParticles.make());
        gui.item(23, color.make());

        player.openInventory(gui.make());
    }

}
