package com.nightshadepvp.core.cmd.cmds.admin;

import com.google.gson.JsonObject;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

public class CmdUpateDiscord extends NightShadeCoreCommand {

    private static CmdUpateDiscord i = new CmdUpateDiscord();

    public static CmdUpateDiscord get() {
        return i;
    }

    public CmdUpateDiscord() {
        this.addAliases("updatediscord");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.MANAGER));
        this.addParameter(TypeString.get(), "player/ALL");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        String param = this.readArg();
        if (param == null) {
            param = "ALL";
        }

        if (param.equalsIgnoreCase("all")) {
            if (Bukkit.getOnlinePlayers().size() > 10) {
                nsPlayer.msg(ChatUtils.message("&cYou can't do this command with more than 10 players online!"));
                nsPlayer.msg(ChatUtils.message("&cThis is a heavy command that could cause lag!"));
                return;
            }
            nsPlayer.msg(ChatUtils.message("&eStarting to update ALL users with their Discord linked. This could take a while."));
            long start = System.currentTimeMillis();

            new BukkitRunnable() {
                int updated = 0;

                @Override
                public void run() {

                    JsonObject jsonObject;
                    for (NSPlayer user : NSPlayerColl.get().getAll()) {
                        if (!user.isPlayer()) continue;
                        if (!user.hasDiscordLinked()) continue;

                        jsonObject = new JsonObject();
                        jsonObject.addProperty("discordID", user.getDiscordID());
                        jsonObject.addProperty("rank", user.getRank().getDiscordRankName());
                        Jedis jedis = Core.get().getJedis();
                        if (jedis == null) {
                            Core.get().getLogManager().log(Logger.LogType.SEVERE, "Jedis was null!");
                            return;
                        }

                        jedis.publish("rankUpdates", ChatColor.stripColor(jsonObject.toString()));
                        updated++;
                    }

                    long end = System.currentTimeMillis();

                    long total = end - start;
                    int mins = (int) total % 60;
                    int seconds = (int) total / 60;
                    nsPlayer.msg(ChatUtils.message("&bFinished udpdating &a" + updated + " &bdiscord users &8(&a" + mins + "." + seconds + "s&8)"));


                }
            }.runTaskAsynchronously(Core.get());
        }
    }
}
