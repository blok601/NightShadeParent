package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.gui.GuiBuilder;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdPlayerInfo extends NightShadeCoreCommand{

    private static CmdPlayerInfo i = new CmdPlayerInfo();

    public static CmdPlayerInfo get() {
        return i;
    }

    public CmdPlayerInfo() {
        this.setAliases("playerinfo");

        this.addParameter(TypeNSPlayer.get(), "player", "you");

        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer user = this.readArg(NSPlayer.get(sender));


        GuiBuilder gui = new GuiBuilder().name(user.getName() + "'s Network Stats").rows(3);


        //So we dont directly interface with the mojang api using the offline player object
        // this creates a new user object given the uuid and grabs the data from the db

        ItemStack info = new ItemBuilder(Material.PAPER).name(ChatUtils.format("&6" + user.getName() + "'s Player Information"))
                .lore(ChatUtils.format("&5Name: &e" + user.getName()))
                .lore(ChatUtils.format("&5Rank: &e" + user.getRank().getNameColor() + user.getRank().toString())).make();

        gui.item(12, info);

        ItemStack stats = new ItemBuilder(Material.PAPER).name(ChatUtils.format("&6Stats")).lore(ChatUtils.format("&eClick to view &a" + user.getName() + "'s &enetwork stats"))
                .lore(ChatUtils.format("&4&lCOMING SOON!"))
                .make();

        gui.item(14, stats);

        ((Player) sender).openInventory(gui.make());
    }
}
