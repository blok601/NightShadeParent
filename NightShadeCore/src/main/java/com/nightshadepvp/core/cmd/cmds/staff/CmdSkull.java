package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.InventoryUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import com.nightshadepvp.core.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CmdSkull extends NightShadeCoreCommand {

    private static CmdSkull i = new CmdSkull();

    public static CmdSkull get() {
        return i;
    }

    public CmdSkull() {
        this.addAliases("skull");
        this.addParameter(TypeString.get(), "player");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        String owner = this.readArg();
        Player player = (Player) sender;
        Core.get().getServer().getScheduler().runTaskAsynchronously(Core.get(), () -> { //Set owner has an http request
            ItemStack skull1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemStack newSkull1 = new ItemBuilder(skull1).skullOwner(owner).name(owner).make();
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.getInventory().addItem(newSkull1);
                    player.sendMessage(ChatUtils.message("&bGiven the skull of &f" + owner));
                }
            }.runTask(Core.get());
        });
    }
}
