package com.nightshadepvp.core.cmd.cmds.player;

import com.google.gson.JsonObject;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;


public class CmdLinkDiscord extends NightShadeCoreCommand {

    private static CmdLinkDiscord i = new CmdLinkDiscord();

    public static CmdLinkDiscord get() {
        return i;
    }

    public CmdLinkDiscord() {
        this.addAliases("linkdiscord");
        this.addParameter(TypeString.get(), "code");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        String code = this.readArg();
        Jedis jedis = Core.get().getJedis();
        String string = jedis.get(code);
        if (string == null) {
            nsPlayer.msg(ChatUtils.message("&cThat code couldn't be found! It may have expired."));
            return;
        }

        jedis.expire(code, 10);
        Long id = Long.parseLong(string);

        nsPlayer.setDiscordID(id);
        nsPlayer.msg(ChatUtils.message("&eSuccessfully synced your Discord!"));
        nsPlayer.msg(ChatUtils.message("&eYour Discord rank will now be synced with your in game rank..."));

        new BukkitRunnable() {
            @Override
            public void run() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("discordID", id);
                jsonObject.addProperty("rank", nsPlayer.getRank().getDiscordRankName());
                jsonObject.addProperty("username", nsPlayer.getName());

                jedis.publish("playerLink", jsonObject.toString()); //To remove special chars
            }
        }.runTaskAsynchronously(Core.get());
    }
}
