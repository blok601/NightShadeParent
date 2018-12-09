package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 9/3/2018.
 */
public class CmdArmor extends NightShadeCoreCommand {

    private static CmdArmor i = new CmdArmor();

    public static CmdArmor get() {
        return i;
    }

    public CmdArmor() {
        this.addAliases("armor");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addParameter(TypeString.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        String targetName = this.readArg();
        NSPlayer nsPlayer = NSPlayer.get(sender);


        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            nsPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }


        int slot = 0;
        Inventory inventory = Bukkit.createInventory(null, 9, target.getName() + "'s Armor");
        for (ItemStack stack : target.getPlayer().getInventory().getArmorContents()) {
            if (stack == null || stack.getType() == Material.AIR) {
                continue;
            }
            inventory.setItem(slot, stack);
            slot++;
        }
        nsPlayer.msg(ChatUtils.message("&eOpening &a" + target.getName() + "&e's armor..."));
        nsPlayer.getPlayer().openInventory(inventory);

//        ItemStack helm = target.getInventory().getArmorContents()[0];
//        ItemStack chest = target.getInventory().getArmorContents()[1];
//        ItemStack leggs = target.getInventory().getArmorContents()[2];
//        ItemStack boots = target.getInventory().getArmorContents()[3];
//
//        if (helm != null && helm.getType() != Material.AIR) {
//            inventory.setItem(0, helm);
//        }
//
//        if (chest != null && chest.getType() != Material.AIR) {
//            inventory.setItem(1, chest);
//        }
//        if (leggs != null && leggs.getType() != Material.AIR) {
//            inventory.setItem(2, leggs);
//        }
//        if (boots != null && boots.getType() != Material.AIR) {
//            inventory.setItem(3, boots);
//        }





    }
}
