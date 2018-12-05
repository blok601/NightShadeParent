package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 11/27/2018.
 */
public class CmdGiveall extends NightShadeCoreCommand {

    private static CmdGiveall i = new CmdGiveall();

    public static CmdGiveall get() {
        return i;
    }

    public CmdGiveall() {
        this.addAliases("giveall");
        this.setDesc("Gives everyone the item in your hand, with the amount specified");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIALHOST));
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        ItemStack stack = nsPlayer.getPlayer().getItemInHand();
        if (stack == null || stack.getType() == Material.AIR) {
            nsPlayer.msg(ChatUtils.message("&cThe item must be valid!"));
            return;
        }
        ItemStack newStack = nsPlayer.getPlayer().getItemInHand();

        nsPlayer.msg(ChatUtils.message("&eGiving all players &3" + newStack.getAmount() + " " + newStack.getType().name()));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (nsPlayer.getName().equalsIgnoreCase(player.getName())) {
                continue;
            }

            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), newStack);
                player.sendMessage(ChatUtils.message("&eYou have received &3" + newStack.getAmount() + " " + newStack.getType().name() + " &efrom the host!"));
                player.sendMessage(ChatUtils.message("&4Your inventory was full, dropping items on the ground!"));
            } else {
                player.sendMessage(ChatUtils.message("&eYou have received &3" + newStack.getAmount() + " " + newStack.getType().name() + " &efrom the host!"));
                player.getInventory().setItem(player.getInventory().firstEmpty(), newStack);
            }
        }
    }
}
