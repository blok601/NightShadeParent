package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        this.addParameter(TypeNSPlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer target = this.readArg();
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if (!target.isOnline()) {
            nsPlayer.msg(ChatUtils.message("&cThat player is not online!"));
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

    }
}
