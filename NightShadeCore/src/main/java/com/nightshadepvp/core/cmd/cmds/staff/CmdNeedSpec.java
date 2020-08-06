package com.nightshadepvp.core.cmd.cmds.staff;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.ServerType;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.UUID;

public class CmdNeedSpec extends NightShadeCoreCommand {

    private static CmdNeedSpec i = new CmdNeedSpec();
    public static CmdNeedSpec get() {return i;}

    private HashSet<UUID> cooldown = Sets.newHashSet();

    public CmdNeedSpec() {
        this.addAliases("needspec");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL), RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);

        if(cooldown.contains(nsPlayer.getUuid())){
            nsPlayer.msg(ChatUtils.message("&cYou recently requested spectators! Please wait before doing this again!"));
            return;
        }


        int players = Bukkit.getOnlinePlayers().size();
        String server = ServerType.getType().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("player", nsPlayer.getName());
        jsonObject.addProperty("amount", players);
        jsonObject.addProperty("server", server);

        Core.get().getServer().getScheduler().runTaskAsynchronously(Core.get(), () -> {
            Jedis jedis = Core.get().getJedis();
            if (jedis == null) {
                Core.get().getLogManager().log(Logger.LogType.SEVERE, "Jedis was null!");
                return;
            }

            jedis.publish("needspec", ChatColor.stripColor(jsonObject.toString()));
        });
        this.cooldown.add(nsPlayer.getUuid());
        Core.get().getServer().getScheduler().scheduleSyncDelayedTask(Core.get(), () -> {
            this.cooldown.remove(nsPlayer.getUuid());
        }, 12000L);

    }
}
