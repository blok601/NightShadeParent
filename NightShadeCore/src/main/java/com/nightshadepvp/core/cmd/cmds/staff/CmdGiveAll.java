package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 11/27/2018.
 */
public class CmdGiveAll extends NightShadeCoreCommand {

    private static CmdGiveAll i = new CmdGiveAll();

    public static CmdGiveAll get() {
        return i;
    }

    public CmdGiveAll() {
        this.addAliases("giveall");
        this.setDesc("Gives everyone the item in your hand, with the amount specified");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIALHOST));
        this.addRequirements(RequirementIsPlayer.get());
        this.addParameter(TypeInteger.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        ItemStack stack = nsPlayer.getPlayer().getItemInHand();
        if (stack == null || stack.getType() == Material.AIR) {
            nsPlayer.msg(ChatUtils.message("&cThe item must be valid!"));
            return;
        }

        int amount = this.readArg();
        if (amount == 0 || amount > 64) {
            nsPlayer.msg(ChatUtils.message("&cThe amount must be between 1 and 64 (inclusive!)"));
            return;
        }

        ItemStack newStack = new ItemBuilder(stack).amount(amount).make();

        nsPlayer.msg(ChatUtils.message("&eGiving all players &3" + amount + " " + newStack.getType().name()));
        Bukkit.getOnlinePlayers().forEach(o -> {
            o.sendMessage(ChatUtils.message("&eYou have received &3" + amount + " " + newStack.getType().name() + " &efrom the host!"));
            if (o.getInventory().firstEmpty() == -1) { //No empty slots
                o.getWorld().dropItemNaturally(o.getLocation(), newStack);
                o.sendMessage(ChatUtils.message("&4Your inventory was full, dropping items on the ground!"));
            } else {
                o.getInventory().addItem(newStack);
            }
        });

    }
}
