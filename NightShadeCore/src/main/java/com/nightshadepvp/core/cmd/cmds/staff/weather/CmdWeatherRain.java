package com.nightshadepvp.core.cmd.cmds.staff.weather;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/12/2019.
 */
public class CmdWeatherRain extends NightShadeCoreCommand {

    public CmdWeatherRain() {
        this.addAliases("rain");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL), RequirementIsPlayer.get());
        this.addParameter(TypeString.get(), "world", "current world");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = NSPlayer.get(sender).getPlayer();
        String worldName = this.readArg(player.getWorld().getName());
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            player.sendMessage(ChatUtils.message("&cInvalid world!"));
            return;
        }

        world.setStorm(true);
        player.sendMessage(ChatUtils.message("&bSet the weather to &frain &bin &f" + world.getName()));
    }
}
