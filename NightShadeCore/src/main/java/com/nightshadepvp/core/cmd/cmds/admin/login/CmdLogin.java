package com.nightshadepvp.core.cmd.cmds.admin.login;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Encrypter;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/20/2019.
 */
public class CmdLogin extends NightShadeCoreCommand implements Encrypter {

    private static CmdLogin i = new CmdLogin();

    public static CmdLogin get() {
        return i;
    }

    public CmdLogin() {
        this.setAliases("login");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN), RequirementIsPlayer.get());
        this.addParameter(TypeString.get(), "password");
    }

    @Override
    public void perform() throws MassiveException {
        String password = this.readArg();
        NSPlayer nsPlayer = NSPlayer.get(sender); //Is a player
        Player player = nsPlayer.getPlayer();

        if(nsPlayer.isLoggedIn()){
            nsPlayer.msg(ChatUtils.message("&cYou are already logged in!"));
            return;
        }

        if (nsPlayer.getAdminPassword() == null || nsPlayer.getAdminPassword().length == 0) {
            nsPlayer.msg(ChatUtils.message("&cYou don't have a password! Do /setpassword to create one!"));
            return;
        }

        if (password.length() < 5 || password.length() > 16) {
            nsPlayer.msg(ChatUtils.message("&cYour password must be 5-16 characters!"));
            return;
        }

        //Log them in
        if (!password.equals(new String(decrypt(nsPlayer.getAdminPassword())))) {
            nsPlayer.msg(ChatUtils.message("&cIncorrect Password!"));
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 0.5F, 0.5F);
            return;
        }

        //It was good
        nsPlayer.setLoggedIn(true);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.5F, 0.5f);
        nsPlayer.msg(ChatUtils.format("&5&m---------------------------------"));
        nsPlayer.msg(ChatUtils.message("&bYou are logged in, &r" + nsPlayer.getName()));
        nsPlayer.msg(ChatUtils.format("&5&m---------------------------------"));
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return new byte[0];
    }

    @Override
    public byte[] decrypt(byte[] data) {
        byte[] enc = new byte[data.length];

        for (int i = 0; i < data.length; i++) {
            enc[i] = (byte) ((i % 2 == 0) ? data[i] - 1 : data[i] + 1);
        }

        return enc;
    }
}
