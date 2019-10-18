package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.entity.objects.PlayerEffect;
import com.nightshadepvp.core.entity.objects.PlayerTag;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 3/25/2019.
 */
public class CmdFixPlayers extends NightShadeCoreCommand {

    private static CmdFixPlayers i = new CmdFixPlayers();

    public static CmdFixPlayers get() {
        return i;
    }

    public CmdFixPlayers() {
        this.addAliases("fixplayer");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.MANAGER));
        this.setVisibility(Visibility.INVISIBLE);
        this.addParameter(TypeString.get(), "player/all");
    }

    @Override
    public void perform() throws MassiveException {
        String arg = this.readArg();
        NSPlayer nsPlayer = NSPlayer.get(sender);

        if (arg.equalsIgnoreCase("*") || arg.equalsIgnoreCase("all")) {


            new BukkitRunnable() {
                int effects = 0;
                int colors = 0;
                int tags = 0;

                @Override
                public void run() {
                    for (NSPlayer target : NSPlayerColl.get().getAll()) {
                        if (!target.getEffects().contains(PlayerEffect.NONE)) {
                            target.getEffects().add(PlayerEffect.NONE);
                            effects++;
                        }

                        if (!target.getColors().contains(PlayerColor.DEFAULT)) {
                            target.getColors().add(PlayerColor.DEFAULT);
                            colors++;
                        }

                        if (!target.getUnlockedTags().contains(PlayerTag.DEFAULT)) {
                            target.getUnlockedTags().add(PlayerTag.DEFAULT);
                            tags++;
                        }
                    }

                    nsPlayer.msg(ChatUtils.message("&bSuccessfully updated &a" + effects + " &bplayers for effects&7, &a" + colors + " &bplayers for colors and &7, &a " + tags + " &bplayers for tags."));

                }
            }.runTaskAsynchronously(Core.get());

            return;
        }

    }
}
