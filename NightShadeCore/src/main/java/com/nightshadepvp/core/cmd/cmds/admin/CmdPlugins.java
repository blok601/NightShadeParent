package com.nightshadepvp.core.cmd.cmds.admin;

import com.google.common.base.Joiner;
import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Blok on 6/24/2019.
 */
public class CmdPlugins extends NightShadeCoreCommand {

    private static CmdPlugins i = new CmdPlugins();

    public static CmdPlugins get() {
        return i;
    }

    public CmdPlugins() {
        this.addAliases("plugins", "pl");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.setDesc("View the server plugins");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        ArrayList<Plugin> plugins = new ArrayList<>(Arrays.asList(Core.get().getServer().getPluginManager().getPlugins()));
        ArrayList<String> names = new ArrayList<>();
        plugins.forEach(plugin -> names.add(plugin.getName()));
        Collections.sort(names);

        nsPlayer.msg(ChatUtils.message("&ePlugins &7(&a" + plugins.size() + "&7): &a" + Joiner.on("&7, &a").join(names)));

    }
}
