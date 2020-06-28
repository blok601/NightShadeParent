package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.entity.Player;

public class CmdFly extends NightShadeCoreCommand {

    private static CmdFly i = new CmdFly();
    public static CmdFly get() {return i;}

    public CmdFly() {
        this.addAliases("fly");
        this.addParameter(TypeBooleanOn.get(), "state");
        this.addParameter(TypePlayer.get(), "player", "Player to change (default = you)");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL), RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        Player player = (Player) sender;
        boolean state = this.readArg();
        Player target = this.readArg(player);
        if(target == null){
            player.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        target.setAllowFlight(state);
        target.setFlying(true);
        player.sendMessage(ChatUtils.message("&bSet fly mode " + (state ? "&aenabled" : "&cdisabled") + " &bfor &f" + target.getName()));
    }
}
